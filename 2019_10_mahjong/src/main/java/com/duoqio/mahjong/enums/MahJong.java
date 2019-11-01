package com.duoqio.mahjong.enums;

/**
 * @author hsk
 * @title MahJong
 * @package com.duoqio.boot.business.enums
 * @describe 麻将
 * @date 2019/10/14 15:16
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
public enum  MahJong {
    WAN_ZI_1("1万"),
    WAN_ZI_2("2万"),
    WAN_ZI_3("3万"),
    WAN_ZI_4("4万"),
    WAN_ZI_5("5万"),
    WAN_ZI_6("6万"),
    WAN_ZI_7("7万"),
    WAN_ZI_8("8万"),
    WAN_ZI_9("9万"),
    TIAO_ZI_1("1条"),
    TIAO_ZI_2("2条"),
    TIAO_ZI_3("3条"),
    TIAO_ZI_4("4条"),
    TIAO_ZI_5("5条"),
    TIAO_ZI_6("6条"),
    TIAO_ZI_7("7条"),
    TIAO_ZI_8("8条"),
    TIAO_ZI_9("9条"),
    TONG_ZI_1("1筒"),
    TONG_ZI_2("2筒"),
    TONG_ZI_3("3筒"),
    TONG_ZI_4("4筒"),
    TONG_ZI_5("5筒"),
    TONG_ZI_6("6筒"),
    TONG_ZI_7("7筒"),
    TONG_ZI_8("8筒"),
    TONG_ZI_9("9筒"),
    FENG_EAST("东"),
    FENG_SOUTH("南"),
    FENG_WEST("西"),
    FENG_NORTH("北"),
    FENG_ZHONG("中"),
    FENG_FA("发"),
    FENG_BAI("白 "),
    FOLLOW_SPRING("春"),
    FOLLOW_SUMMER("夏"),
    FOLLOW_AUTUMN("秋"),
    FOLLOW_WINTER("冬"),
    FOLLOW_MEI("梅"),
    FOLLOW_LAN("兰"),
    FOLLOW_ZHU("竹"),
    FOLLOW_JV("菊");

    private String s;

    MahJong(String s) {
        this.s = s;
    }

    public String getValue(){
        return s;
    }
}
