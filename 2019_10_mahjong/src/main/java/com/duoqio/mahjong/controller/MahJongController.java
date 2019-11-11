package com.duoqio.mahjong.controller;

import com.duoqio.mahjong.enums.MahJong;
import com.duoqio.mahjong.pojo.Player;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hsk
 * @title MahJongController
 * @package com.duoqio.boot.business.app.controller
 * @describe 麻将
 * @date 2019/10/15 10:18
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
public class MahJongController {

    public static List<Map<String, List<Integer>>> getCard() {
        // 1、组装136张麻将
        // 定义数字
        String[] numbers =
                {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        //定义3个类型数组
        String[] types =
                {"TIAO_ZI_", "TONG_ZI_", "WAN_ZI_"};
        // 东、西、南、北风,和红中、白皮、发财
        String[] others =
                {"FENG_EAST", "FENG_SOUTH", "FENG_WEST", "FENG_NORTH", "FENG_ZHONG", "FENG_FA", "FENG_BAI"};
        //春夏秋冬、梅兰竹菊
        String[] follows =
                {"FOLLOW_SPRING", "FOLLOW_SUMMER", "FOLLOW_AUTUMN", "FOLLOW_WINTER", "FOLLOW_MEI", "FOLLOW_LAN", "FOLLOW_ZHU", "FOLLOW_JV"};
        //1. 组合麻将
        //创建Map集合,键是编号,值是牌
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
        // 遍历麻将
        Set<Entry<Integer, String>> entrySet = majiang.entrySet();
        System.out.print("洗牌前：");
        for (Entry<Integer, String> entry : entrySet) {
            System.out.print("[" + entry.getKey() + ":" + entry.getValue() + "]   ");
        }
        // 2、洗牌,将牌的编号打乱

        Collections.shuffle(majiangNumber);
        System.out.println();
        System.out.print("洗牌后：");

        for (Integer mInteger : majiangNumber) {
            System.out.print("[" + mInteger + ":" + majiang.get(mInteger) + "]   ");
        }
        System.out.println();

        // 3、发牌
        // 四个玩家参与游戏，四人交替摸牌，每次4张，3次，之后庄家摸2张，两张之间个索引相差4，其他人再摸一张，即庄家14张，其他每人13张，剩下的留作底牌。

        List<Integer> player1 = new ArrayList<Integer>();
        List<Integer> player2 = new ArrayList<Integer>();
        List<Integer> player3 = new ArrayList<Integer>();
        List<Integer> player4 = new ArrayList<Integer>();

        List<Integer> bottom = new ArrayList<Integer>();

        Integer n = 0;

        // 给玩家发牌
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                player1.add(majiangNumber.get(n++));
            }

            for (int j = 0; j < 4; j++) {
                player2.add(majiangNumber.get(n++));
            }

            for (int j = 0; j < 4; j++) {
                player3.add(majiangNumber.get(n++));
            }

            for (int j = 0; j < 4; j++) {
                player4.add(majiangNumber.get(n++));
            }

        }

        // 庄家 多拿一张
        player1.add(majiangNumber.get(n));
        n += 4;
        player1.add(majiangNumber.get(n));
        n -= 3;
        player2.add(majiangNumber.get(n));
        n++;

        player3.add(majiangNumber.get(n));
        n++;

        player4.add(majiangNumber.get(n));
        n += 2;
        System.out.println("发了" + n + "张牌");

        // 剩下的底牌
        for (int i = n; i < majiangNumber.size(); i++) {
            bottom.add(majiangNumber.get(i));
        }

        Collections.sort(player1);
        Collections.sort(player2);
        Collections.sort(player3);
        Collections.sort(player4);

        List<Integer> cardList1 = switchToEnumOrdinal(player1, majiang);
        List<Integer> cardList2 = switchToEnumOrdinal(player2,majiang);
        List<Integer> cardList3 = switchToEnumOrdinal(player3,majiang);
        List<Integer> cardList4 = switchToEnumOrdinal(player4,majiang);
        List<String> cardNameList1 = switchToEnumValues(player1, majiang);
        List<String> cardNameList2 = switchToEnumValues(player2,majiang);
        List<String> cardNameList3 = switchToEnumValues(player3,majiang);
        List<String> cardNameList4 = switchToEnumValues(player4,majiang);

        HashMap<String, List<Integer>> map = new HashMap<>();

        map.put("玩家1", cardList1);
        map.put("玩家2", cardList2);
        map.put("玩家3", cardList3);
        map.put("玩家4", cardList4);
        List<Map<String, List<Integer>>> list = new ArrayList<>();
        list.add(map);

        return list;
    }

    //转换成枚举坐标
    public static List<Integer> switchToEnumOrdinal(List<Integer> player,Map<Integer, String> majiang){
        List<Integer> list = new ArrayList<>();
        for (Integer key : player) {
            String value = majiang.get(key);
            list.add(MahJong.valueOf(value).ordinal());
        }
        Collections.sort(list);
        return list;
    }

    //转换成枚举的值
    public static List<String> switchToEnumValues(List<Integer> player,Map<Integer, String> majiang) {
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
        List<Map<String, List<Integer>>> card = getCard();
        System.out.println(card);
    }

}
