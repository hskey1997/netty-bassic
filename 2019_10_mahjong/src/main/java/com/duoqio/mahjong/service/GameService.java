package com.duoqio.mahjong.service;

import com.duoqio.mahjong.pojo.Room;

/**
 * @author hsk
 * @title GameService
 * @package com.duoqio.mahjong.service
 * @describe 游戏
 * @date 2019/11/6 16:27
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
public interface GameService {

    Room startGame(Integer roomId,String type) throws Exception;


}
