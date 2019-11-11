package com.duoqio.mahjong.util;


import com.duoqio.mahjong.pojo.Player;
import com.duoqio.mahjong.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hsk
 * @title RoomUtil
 * @package com.duoqio.mahjong.util
 * @describe 房间
 * @date 2019/11/5 22:37
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
public class RoomUtil {

    private final static RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
//    @Autowired
//    private final RedisTemplate<Object,Object> template;
//
//    public RoomUtil(RedisTemplate<Object, Object> template) {
//        this.template = template;
//    }

    public static Room addRoom(String type, Integer userId) {
        Room room = new Room();
        List<Player> playerList = new ArrayList<>();

        //获取房间列表、设置房间号
        List<Room> roomList = (List<Room>) template.opsForValue().get("room:" + type + ":list");
        if (roomList.isEmpty()) {
            //根据创建类型给予默认房间号
            if ("other".equals(type)) {
                room.setRoomId(Integer.valueOf("110000"));
            } else {
                room.setRoomId(Integer.valueOf("120000"));
            }
        } else {
            //房间号累加动态生成房间号
            Integer maxRoomId = roomList.stream().map(e -> e.getRoomId()).max(Integer::compare).get();
            room.setRoomId(maxRoomId++);
        }

        room.setStatus(0);
        room.setCompleteNum(0);
        room.setPlayerList(playerList);

        template.opsForValue().set("room:" + type + ":" + room.getRoomId(), room);
        //更新房间列表
        template.opsForValue().set("room:" + type + ":list", roomList.add(room));
        return room;
    }

    public static Boolean joinRoom(String type, Integer userId, Integer roomId) {

        Room room = (Room) template.opsForValue().get("room:" + type + ":" + roomId);
        if (Objects.isNull(room)) {
            return false;
        }
        //断线重连
        if (1 == room.getStatus()) {
            //TODO
        }
        //channel连接数判断房间内人数
        if (room.getConNum() >= 4) {
            return false;
        }

        List<Player> playerList = room.getPlayerList();
        playerList.add(new Player());
        room.setPlayerList(playerList);
        room.setConNum(room.getConNum() + 1);
        room.setCompleteNum(0);

        //更新缓存
        template.opsForValue().set("room:" + type + ":" + room.getRoomId(), room);
        return true;
    }

    public static Boolean leaveRoom(String type, Integer userId, Integer roomId) {
        Room room = (Room) template.opsForValue().get("room:" + type + ":" + roomId);
        if (Objects.isNull(room)) {
            return false;
        }

        //房间人数为1删除房间,更新缓存
        if (room.getConNum() <= 1) {
            template.delete("room:" + type + ":" + roomId);
            List<Room> roomList = (List<Room>) template.opsForValue().get("room:" + type + ":list");
            template.opsForValue().set("room:" + type + ":list", roomList.stream().filter(e -> e.getRoomId() != roomId).collect(Collectors.toList()));
            return true;
        }

        room.setConNum(room.getConNum() - 1);
        List<Player> playerList = room.getPlayerList();
        //从缓存中移除玩家
        room.setPlayerList(playerList.stream().filter(e -> e.getPlayerId() != userId).collect(Collectors.toList()));
        //更新缓存
        template.opsForValue().set("room:" + type + ":" + roomId, room);
        return true;
    }


}
