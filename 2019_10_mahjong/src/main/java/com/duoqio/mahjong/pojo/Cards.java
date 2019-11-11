package com.duoqio.mahjong.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hsk
 * @title Cards
 * @package com.duoqio.mahjong.pojo
 * @describe 麻将牌
 * @date 2019/11/5 11:36
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
@Data
public class Cards implements Serializable {

    //麻将
    List<Integer> majiang;

    //桌上剩余麻将
    List<Integer> tableMajiangStack;

    //玩家手中的麻将
    List<Integer> playerMajiangStack;

    //玩家打出的麻将
    List<Integer> playerThrowMajiangStack;

}
