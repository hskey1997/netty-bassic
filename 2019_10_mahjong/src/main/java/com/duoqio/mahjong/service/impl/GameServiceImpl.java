package com.duoqio.mahjong.service.impl;

import com.duoqio.mahjong.enums.MahJong;
import com.duoqio.mahjong.pojo.Room;
import com.duoqio.mahjong.service.GameService;
import com.duoqio.mahjong.util.MahjongUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author hsk
 * @title GameServiceImpl
 * @package com.duoqio.mahjong.service.impl
 * @describe
 * @date 2019/11/7 9:43
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
@Service
public class GameServiceImpl implements GameService {

    private final RedisTemplate<Object,Object> template;

    @Autowired
    public GameServiceImpl(RedisTemplate<Object, Object> template) {
        this.template = template;
    }

    @Override
    public Room startGame(Integer roomId, String type) throws Exception {

        //获取房间信息
        Room room = (Room) template.opsForValue().get("room:" + type + ":" + roomId);
        if (Objects.isNull(room)) {
            throw new Exception("找不到房间");
        }

        room.getPlayerList().forEach(f ->{
            if  (1 !=f.getStatus()){
                System.out.println("玩家未准备");
            }
        });

        Map<Integer, String> mahJong = (Map<Integer, String>) template.opsForValue().get("MahJong");

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < mahJong.size(); i++) {
            String value = mahJong.get(String.valueOf(i));
            list.add(MahJong.valueOf(value).ordinal());
        }
        room.setTableMajiangStack(list);
        MahjongUtil.initCards(room);
        return null;
    }
}
