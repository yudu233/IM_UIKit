package com.rain.messagelist.model;

import java.io.Serializable;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/19 10:24
 * @Version : 1.0
 * @Descroption : 用户资料
 */
public interface IUser extends Serializable {

    String getId();

    String getName();

    String getAvatar();

}
