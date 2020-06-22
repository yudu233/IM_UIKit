package com.ycbl.im.uikit.business.contact.item;


/**
 * @Author : Rain
 * @Version : 1.0
 * @Org : https://www.yudu233.com
 * @CreateDate :  2020/5/15 10:35
 * @Description :通讯录数据项抽象类
 */

public abstract class AbsContactItem {
    /**
     * 所属的类型
     *
     * @see ItemTypes
     */
    public abstract int getItemType();

    /**
     * 所属的分组
     */
    public abstract String belongsGroup();

    protected final int compareType(AbsContactItem item) {
        return compareType(getItemType(), item.getItemType());
    }

    public static int compareType(int lhs, int rhs) {
        return lhs - rhs;
    }
}
