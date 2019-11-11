package com.duoqio.mahjong.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hsk
 * @title Player
 * @package com.duoqio.mahjong.pojo
 * @describe 玩家
 * @date 2019/11/5 11:42
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
@Data
public class Player extends Cards implements Serializable {
    Integer playerId;

    BigDecimal money;

    //玩家状态0:空闲中1:已准备2:游戏中
    Integer status;

    String nickName;

    Integer imgUrl;
    //出牌顺序
    Integer turn;
}
