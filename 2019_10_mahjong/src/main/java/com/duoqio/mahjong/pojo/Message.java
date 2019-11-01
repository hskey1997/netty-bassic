package com.duoqio.mahjong.pojo;

/**
 * @author hsk
 * @title Message
 * @package com.duoqio.mahjong.pojo
 * @describe
 * @date 2019/10/18 16:40
 * @copyright 重庆多企源科技有限公司
 * @website {[图片]http://www.duoqio.com/index.asp?source=code}
 */
public class Message {
    private String name;
    private String time;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
