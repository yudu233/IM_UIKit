package com.rain.messagelist.message;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/18 10:10
 * @Version : 1.0
 * @Descroption :
 */
public enum SessionType {
    None(-1),

    /**
     * 单聊
     */
    P2P(0),

    /**
     * 群聊
     */
    Team(1),

    /**
     * 超大群
     */
    SUPER_TEAM(2),

    /**
     * 系统消息
     */
    System(10001),

    /**
     * 聊天室
     */
    ChatRoom(10002);

    private int value;

    SessionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SessionType typeOfValue(int value) {
        for (SessionType e : values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return P2P;
    }
}