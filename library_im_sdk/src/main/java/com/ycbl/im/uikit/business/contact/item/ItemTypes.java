package com.ycbl.im.uikit.business.contact.item;

/**
 * @Author : Rain
 * @Version : 1.0
 * @Org : https://www.yudu233.com
 * @CreateDate :  2020/5/15 10:35
 * @Description :  通讯录列表项类型
 */
public interface ItemTypes {

    /**
     * 基础类型
     */
    int TEXT = -2;

    int LABEL = -1;

    /**
     * 扩展类型
     */
    int FUNC = 0; // 功能项

    int FRIEND = 1; // 好友项

    int TEAM = 2; // 群组项

    int TEAM_MEMBER = 3; // 群成员

    int MSG = 4; // 消息

    /**
     * 子类型
     */
    interface TEAMS {
        int BASE = ItemTypes.TEAM << 16;

        int NORMAL_TEAM = BASE + 1; // 普通群

        int ADVANCED_TEAM = BASE + 2; // 高级群
    }
}
