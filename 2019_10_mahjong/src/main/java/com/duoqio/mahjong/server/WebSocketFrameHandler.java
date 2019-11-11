/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.duoqio.mahjong.server;

import com.duoqio.mahjong.pojo.Player;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.pool.ChannelPool;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.boot.json.JsonParser;
import protobuf.MyDataInfo;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.UUID;


/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final static  String MYSELF = "[自己]{0}发送了消息：{1}";

    private final static  String OTHER = "[用户]{0}发送了消息：{1}";

    private final static  String JOIN = "[用户]{0}加入了房间";

    private final static  String LEAF = "[用户]{0}离开了房间{1}";

    private final static  String FULL = "房间已满";

    private final static  String CONSUCCESS = "连接成功";


    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static AttributeKey<String> ROOM = AttributeKey.valueOf("room");
    private static AttributeKey<String> PLAYER = AttributeKey.valueOf("player");


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        Channel channel = ctx.channel();
        //设置房间号
        channel.attr(ROOM).setIfAbsent(frame.text());
        Player player = new Player();
        player.setNickName("hsk");
        player.setPlayerId(123);
        channel.attr(PLAYER).setIfAbsent(player.toString());

        //获取房间号
        String roomId = channel.attr(ROOM).get();

        //消息处理
        channelGroup.forEach(ch -> {
            System.out.println(ch.attr(ROOM).get());
            System.out.println(ch.attr(PLAYER).get());
            if (frame.text().equals(ch.attr(ROOM).get())) {
                ch.writeAndFlush(new TextWebSocketFrame(MessageFormat.format(OTHER, channel.id(), frame.text())));
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush(new TextWebSocketFrame(MessageFormat.format(CONSUCCESS, channel.id())));
        channelGroup.add(channel);
    }

    //离开后向房间推送消息
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}


//protobuf的处理
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage myMessage) throws Exception {
//        Channel channel = ctx.channel();
//        //消息处理
//        MyDataInfo.MyMessage.DataType dataType = myMessage.getDataType();
//        //房间消息
//        if (dataType == MyDataInfo.MyMessage.DataType.RoomType) {
//
//        }else if (dataType == MyDataInfo.MyMessage.DataType.GameType) {
//
//        }else {
//            System.out.println("未知消息格式");
//        }
//    }