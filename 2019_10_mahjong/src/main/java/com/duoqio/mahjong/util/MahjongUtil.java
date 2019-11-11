package com.duoqio.mahjong.util;

import com.duoqio.mahjong.enums.MahJong;
import com.duoqio.mahjong.pojo.Player;
import com.duoqio.mahjong.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hsk
 * @title MahjongUtil
 * @package com.duoqio.mahjong.util
 * @describe 麻将工具类
 * @date 2019/11/5 11:51
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
public class MahjongUtil {

    /**
     * @param
     * @describe 生成麻将、洗牌
     * @title initCards
     * @author hsk
     * @date 2019/11/7 11:20
     * @return java.util.List<java.lang.Integer>
     */
    public static List<Integer> initCards(Room room) {
        //发牌
        dealCards(room);

        return null;
    }

    /**
     * @param
     * @describe 发牌
     * @title dealCards
     * @author hsk
     * @date 2019/11/7 11:22
     * @return com.duoqio.mahjong.pojo.Room
     */
    public static void dealCards(Room room) {
        List<Player> playerList = room.getPlayerList();
        List<Integer> cards = room.getTableMajiangStack();
        //洗牌
        Collections.shuffle(cards);
        //按玩家顺序对玩家排序
        List<Player> sortPlayers = playerList.stream().sorted(Comparator.comparing(Player::getTurn)).collect(Collectors.toList());
        //发牌
        for (int i = 0; i < 3; i++) {
            sortPlayers.forEach(f -> {
                List<Integer> collect = IntStream.range(0, 4).boxed().map(e -> {
                    return cards.remove(0);
                }).collect(Collectors.toList());
                if (f.getPlayerMajiangStack().size()<1){
                    f.setPlayerMajiangStack(collect);
                }else {
                    f.getPlayerMajiangStack().addAll(collect);
                }
            });
        }
        for (int i = 0; i < 4; i++) {
            //庄家 多拿一张
            if (i == 0) {
                sortPlayers.get(i).getPlayerMajiangStack().add(cards.remove(4));
            }
            //玩家各拿一张
            sortPlayers.get(i).getPlayerMajiangStack().add(cards.remove(0));
            //初始化玩家打出的牌
            sortPlayers.get(i).setPlayerThrowMajiangStack(new ArrayList<>());
        }
        room.setPlayerList(playerList);
        room.setTableMajiangStack(cards);

//        return null;
    }


    /**
     * @param cards   麻将编号
     * @param majiang
     * @describe 转换成枚举坐标
     * @title switchToEnumOrdinal
     * @author hsk
     * @date 2019/11/5 14:10
     * @return java.util.List<java.lang.Integer>
     */
    public static List<Integer> switchToEnumOrdinal(Map<Integer, String> majiang, List<Integer> cards) {
        List<Integer> list = new ArrayList<>();
        if (cards.size() > 0) {
            for (Integer key : cards) {
                String value = majiang.get(String.valueOf(key));
                list.add(MahJong.valueOf(value).ordinal());
            }
        } else {
            for (int i = 0; i < majiang.size(); i++) {
                String value = majiang.get(String.valueOf(i));
                list.add(MahJong.valueOf(value).ordinal());
            }
        }
        Collections.sort(list);
        return list;
    }

    //转换成枚举的值
    public static List<String> switchToEnumValues(List<Integer> player, Map<Integer, String> majiang) {
        List<String> list = new ArrayList<>();
        String cardName = "";
        for (Integer key : player) {
            String value = majiang.get(key);
            cardName = MahJong.values()[MahJong.valueOf(value).ordinal()].getValue();
            list.add(cardName);
        }
        return list;
    }

    public static void main(String[] args) {
    }

}
