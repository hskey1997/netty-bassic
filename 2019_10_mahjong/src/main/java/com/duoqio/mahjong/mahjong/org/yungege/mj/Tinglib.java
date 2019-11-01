package com.duoqio.mahjong.mahjong.org.yungege.mj;

import static com.duoqio.mahjong.mahjong.org.yungege.mj.Program.print_cards;

//胡牌类
public class Tinglib {
    static Tinglib tinglib =  new Tinglib();


    /**
     * @describe //是否胡牌
     * @title get_hu_info
     * @author hsk
     * @date 2019/10/23 14:22
     * @param hand_cards   玩家手牌
     * @param curCard   只关注下标为前33的牌
     * @param gui_index  癞子牌下标
     * @return	boolean  是否胡牌
     */
    public boolean get_hu_info(int[] hand_cards, int curCard, int gui_index) {

        int[] hand_cards_tmp = new int[34];
        //玩家手牌数
        int handCardsNum = 0;

        for (int i = 0; i < 34; ++i) {
            hand_cards_tmp[i] = hand_cards[i];
            handCardsNum += hand_cards[i];
        }
        //手牌不足14张不满足胡牌条件
//        if (handCardsNum < 14) {
//            return false;
//        }
        //鬼牌数量
        int gui_num = 0;
        if (gui_index < 34) {//将癞子从手牌中提出来
            gui_num = hand_cards_tmp[gui_index];
            hand_cards_tmp[gui_index] = 0;
        }
        gui_num += 1;//用鬼牌代替模拟打出的那一张牌

        //
        ProbabilityItemTable ptbl = new ProbabilityItemTable();
        if (!split(hand_cards_tmp, gui_num, ptbl)) {
            return false;
        }

        return check_probability(ptbl, gui_num);
    }
    //听牌
    public Boolean getTing(int[] hand_cards, int curCard, int gui_index) {

        int[] hand_cards_tmp = new int[34];
        //玩家手牌数
        int handCardsNum = 0;

        for (int i = 0; i < 34; ++i) {
            hand_cards_tmp[i] = hand_cards[i];
            handCardsNum += hand_cards[i];
        }
        //手牌不足14张不满足胡牌条件
//        if (handCardsNum < 14) {
//            return false;
//        }

//        if (curCard < 34) {
//            hand_cards_tmp[curCard]++;
//        }
        //鬼牌数量
        int gui_num = 0;
        if (gui_index < 34) {//将癞子从手牌中提出来
            gui_num = hand_cards_tmp[gui_index];
            hand_cards_tmp[gui_index] = 0;
        }
        gui_num += 1;//添加一张鬼牌代替去掉的那一张牌
        ProbabilityItemTable ptbl = new ProbabilityItemTable();
        if (!split(hand_cards_tmp, gui_num, ptbl)) {
            return false;
        }

        return check_probability(ptbl, gui_num);
    }

    public static Tinglib getInstance() {
        return tinglib;
    }
    //拆分牌型对不同花色判断
    boolean split(int[] cards, int gui_num, ProbabilityItemTable ptbl) {
        if (!_split(cards, gui_num, 0, 0, 8, true, ptbl))
            return false;
        if (!_split(cards, gui_num, 1, 9, 17, true, ptbl))
            return false;
        if (!_split(cards, gui_num, 2, 18, 26, true, ptbl))
            return false;
        if (!_split(cards, gui_num, 3, 27, 33, false, ptbl))
            return false;

        return true;
    }

    boolean _split(int[] cards, int gui_num, int color, int min, int max, boolean chi, ProbabilityItemTable ptbl) {
        int key = 0;
        int num = 0;

        for (int i = min; i <= max; ++i) {
            //生成牌型编码
            key = key * 10 + cards[i];
            //牌数
            num = num + cards[i];
        }
        //当前类型牌没有在手牌中放行
        if (num > 0) {
            if (!list_probability(color, gui_num, num, key, chi, ptbl)) {
                return false;
            }
        }

        return true;
    }
    //癞子胡牌
    boolean list_probability(int color, int gui_num, int num, int key, boolean chi, ProbabilityItemTable ptbl) {
        boolean find = false;
        int anum = ptbl.array_num;//二维数组m的列(在能胡的情况下进行++操作)
        for (int i = 0; i <= gui_num; ++i) {
            int yu = (num + i) % 3;//牌数加癞子数对3求余
            if (yu == 1)//余一不满足胡牌条件
                continue;
            boolean eye = (yu == 2);//余二或零满足,余二表示有将牌
            if (find || TableMgr.getInstance().check(key, i, eye, chi)) {
                ProbabilityItem item = ptbl.m[anum][ptbl.m_num[anum]];

                ptbl.m_num[anum]++;

                item.eye = eye;
                item.gui_num = i;
                find = true;
            }
        }
        //m_num为四位数组分别代表四种花色，如果值小于<=0则无法胡牌
        if (ptbl.m_num[anum] <= 0) {
            return false;
        }

        ptbl.array_num++;
        return true;
    }

    boolean check_probability(ProbabilityItemTable ptbl, int gui_num) {
        // 全是鬼牌
        if (ptbl.array_num == 0) {
            return gui_num >= 2;
        }

        // 只有一种花色的牌的鬼牌(清一色)
        if (ptbl.array_num == 1)
            return true;

        // 尝试组合花色，能组合则胡
        for (int i = 0; i < ptbl.m_num[0]; ++i) {
            ProbabilityItem item = ptbl.m[0][i];
            boolean eye = item.eye;

            int gui = gui_num - item.gui_num;
            if (check_probability_sub(ptbl, eye, gui, 1, ptbl.array_num)) {
                return true;
            }
        }
        return false;
    }

    boolean check_probability_sub(ProbabilityItemTable ptbl, boolean eye, int gui_num, int level, int max_level) {
        for (int i = 0; i < ptbl.m_num[level]; ++i) {
            ProbabilityItem item = ptbl.m[level][i];

            if (eye && item.eye)
                continue;

            if (gui_num < item.gui_num)
                continue;
            //max_level:花色种类
            if (level < max_level - 1) {
                if (check_probability_sub(ptbl, eye || item.eye, gui_num - item.gui_num, level + 1, ptbl.array_num)) {
                    return true;
                }
                continue;
            }

            if (!eye && !item.eye && item.gui_num + 2 > gui_num)
                continue;
            return true;
        }

        return false;
    }

}

