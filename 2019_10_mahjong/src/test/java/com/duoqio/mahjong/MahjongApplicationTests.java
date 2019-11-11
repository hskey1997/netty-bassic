package com.duoqio.mahjong;

import com.duoqio.mahjong.pojo.Player;
import com.duoqio.mahjong.pojo.Room;
//import com.duoqio.mahjong.util.RoomUtil;
import com.duoqio.mahjong.service.GameService;
import com.duoqio.mahjong.util.MahjongUtil;
import com.duoqio.mahjong.util.RoomUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class MahjongApplicationTests {

    @Autowired
    private GameService gameService;

//    @Autowired
//    private RedisTemplate<Object,Object> template;
    @Test
    void mahjong() throws Exception {
        gameService.startGame(123456,"other");

        //从缓存获取麻将
//        Map<Integer, String> mahJong = (Map<Integer, String>) template.opsForValue().get("MahJong");
//        Room room = (Room) template.opsForValue().get("room:other:110110");

//        List<Player> playerList = room.getPlayerList();
//        playerList.forEach(f -> {
//            System.out.println(f.getPlayerMajiangStack());
//        });
//        System.out.println(room);
//        List<Player> playerList = room.getPlayerList();
//        playerList.stream().forEach(e-> e.setPlayerMajiangStack(new ArrayList<>()));
//        room.setPlayerList(playerList);
//        System.out.println(room);
//        template.opsForValue().set("room:other:110110",room);
//        List<Integer> list = MahjongUtil.switchToEnumOrdinal(mahJong, new ArrayList<>());
//
//        room.setMajiangStack(list);
//        List<Integer> list = MahjongUtil.initCards();
//        System.out.println(list);
    }

    private void createTestRoom() {
        Room room = new Room();
        List<Player> list = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            Player player = new Player();
            player.setPlayerId(i);
            player.setStatus(1);
            player.setTurn(i);
            player.setNickName("玩家"+i);
            player.setMoney(BigDecimal.valueOf(100));
            player.setPlayerMajiangStack(new ArrayList<>());
            player.setPlayerThrowMajiangStack(new ArrayList<>());
            player.setTableMajiangStack(new ArrayList<>());
            player.setMajiang(new ArrayList<>());
            list.add(player);
        }
        room.setPlayerList(list);
        room.setConNum(4);
        room.setStatus(0);
        room.setCompleteNum(0);
        room.setTableMajiangStack(new ArrayList<>());
        room.setRoomId(123456);
//        template.opsForValue().set("room:other:"+room.getRoomId(),room);
    }

    void mahJongInitTest(){
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] types = {"TIAO_ZI_", "TONG_ZI_", "WAN_ZI_"};
        // 东、西、南、北风,和红中、白皮、发财
        String[] others = {"FENG_EAST", "FENG_SOUTH", "FENG_WEST", "FENG_NORTH", "FENG_ZHONG", "FENG_FA", "FENG_BAI"};
        //春夏秋冬、梅兰竹菊
        String[] follows = {"FOLLOW_SPRING", "FOLLOW_SUMMER", "FOLLOW_AUTUMN", "FOLLOW_WINTER", "FOLLOW_MEI", "FOLLOW_LAN", "FOLLOW_ZHU", "FOLLOW_JV"};
        Map<Integer, String> majiang = new HashMap<Integer, String>();
        //创建List集合,存储编号
        List<Integer> majiangNumber = new ArrayList<Integer>();
        //定义整数变量,作为键出现
        int index = 0;
        //遍历数组,类型+数字的组合,存储到Map集合
        for (String type : types) {
            for (String number : numbers) {
                for (int i = 0; i < 4; i++) {
                    // 存麻将
                    majiang.put(index, type + number);
                    // 存麻将编号
                    majiangNumber.add(index);
                    index++;
                }
            }
        }
        //存储东、西、南、北风,和红中、白皮、发财
        for (String str : others) {
            for (int i = 0; i < 4; i++) {
                // 存麻将
                majiang.put(index, str);
                // 存麻将编号
                majiangNumber.add(index);
                index++;
            }
        }
        //存储花牌
        for (String follow : follows) {
            majiang.put(index, follow);
            majiangNumber.add(index);
            index++;
        }
    }
}
