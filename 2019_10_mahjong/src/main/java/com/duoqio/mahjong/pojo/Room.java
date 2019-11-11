package com.duoqio.mahjong.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hsk
 * @title Room
 * @package com.duoqio.mahjong.pojo
 * @describe 游戏房间
 * @date 2019/11/5 11:19
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
@Data
public class Room extends Cards implements Serializable {

    //房间id
    Integer roomId;

    //房间状态0:空闲中 1:游戏进行中
    Integer status;

    //房间连接数(channel)
    Integer conNum;

    //完成局数
    Integer completeNum;

    //玩家列表
    List<Player> playerList;


}
