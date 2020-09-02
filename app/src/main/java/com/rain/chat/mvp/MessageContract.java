package com.rain.chat.mvp;

import com.ycbl.im.uikit.msg.models.MyMessage;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/2 10:15
 * @Version : 1.0
 * @Descroption :
 */
public interface MessageContract {
    interface Model extends BaseModel{

    }

    interface IView extends BaseView{

        /**
         * 新消息接收
         * @param imMessage
         */
        void onMessageIncoming(MyMessage imMessage);
    }
}
