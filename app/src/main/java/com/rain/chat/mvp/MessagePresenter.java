package com.rain.chat.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.rain.chat.session.action.ImageAction;
import com.rain.chat.session.action.LocationAction;
import com.rain.chat.session.action.VideoAction;
import com.rain.chat.session.module.Container;
import com.rain.chat.session.module.CustomerBaseAction;
import com.rain.inputpanel.action.ActionController;
import com.rain.inputpanel.action.BaseAction;
import com.rain.library_netease_sdk.NimClientController;
import com.ycbl.im.uikit.impl.MessageObserver;
import com.rain.library_netease_sdk.msg.models.MyMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/2 10:16
 * @Version : 1.0
 * @Descroption :
 */
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.IView> {

    public MessagePresenter(MessageContract.Model model, MessageContract.IView rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        NimClientController.registerMsgServiceObserve(incomingMessageObserver, true);
        // FIXME: 2020/9/2 已读回执监听
//        if (NimUIKitImpl.getOptions().shouldHandleReceipt) {
//            service.observeMessageReceipt(messageReceiptObserver, register);
//        }
    }

    MessageObserver<List<MyMessage>> incomingMessageObserver = (MessageObserver<List<MyMessage>>) messages -> {
        for (MyMessage imMessage : messages) {
            // FIXME: 2020/9/2 消息过滤

            //通知消息 -> 群成员变动的通知消息
//            if (imMessage.getAttachment() instanceof NotificationAttachment
//                    && ((NotificationAttachment) imMessage.getAttachment()).getType() == NotificationType.TransferOwner) {
//                ArrayList<String> targets = ((MemberChangeAttachment) imMessage.getAttachment()).getTargets();
//                boolean isUpdate = !targets.isEmpty() && targets.get(0).equals(NimHelper.getAccount())
//                        || imMessage.getFromAccount().equals(NimHelper.getAccount());
//                if (isUpdate) {
//                    //将群主转让给自己 -> 重新获取群成员信息
//                    ((TeamMessageActivity) getActivity()).queryTeamMemberList();
//                }
//            }
//
//            //好友资料更新消息
//            if (imMessage.getAttachment() instanceof UserProfileAttachment
//                    && ((UserProfileAttachment) imMessage.getAttachment()).getType() != UserProfileAttachment.MessageType.AGREE_ADD_FRIEND) {
//                //删除当前消息
//                continue;
//            } else if (imMessage.getAttachment() instanceof RedPacketOpenedAttachment) {
//                RedPacketOpenedAttachment attachment = (RedPacketOpenedAttachment) imMessage.getAttachment();
//                if (!attachment.belongTo(NimHelper.getAccount())) {
//                    continue;
//                } else {
//                    onMessageIncoming(messages);
//                }
//            } else {
//                onMessageIncoming(messages);
//            }

            mRootView.onMessageIncoming(imMessage);
        }
    };

    public void initAction(Container container) {
        List<BaseAction> actionList = getActionList();
        ActionController.getInstance().addActions(actionList);
        for (int i = 0; i < actionList.size(); i++) {
            CustomerBaseAction action = (CustomerBaseAction) actionList.get(i);
            action.setContainer(container);
        }
    }

    private List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        actions.add(new ImageAction());
        actions.add(new VideoAction());
        actions.add(new LocationAction());
        return actions;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NimClientController.registerMsgServiceObserve(incomingMessageObserver, false);
    }

}
