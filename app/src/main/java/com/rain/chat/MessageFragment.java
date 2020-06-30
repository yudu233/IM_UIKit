package com.rain.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.chat.constant.Extras;
import com.rain.chat.session.list.MessageListPanelEx;
import com.rain.chat.session.module.Container;
import com.rain.chat.session.module.ModuleProxy;
import com.rain.messagelist.message.SessionType;
import com.ycbl.im.uikit.msg.IMessageBuilder;
import com.ycbl.im.uikit.msg.controller.IMessageController;
import com.ycbl.im.uikit.msg.models.MyMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MessageFragment
 * @CreateDate: 2020/6/29 20:56
 * @Describe:
 */
public class MessageFragment extends Fragment implements ModuleProxy {

    private static final String TAG = "MessageFragment";
    // p2p对方Account或者群id
    protected String sessionId;

    //会话类型
    protected SessionType sessionType;

    private Container container;

    //列表module
    protected MessageListPanelEx messageListPanel;
    private View rootView;


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, viewGroup, false);
        rootView.findViewById(R.id.btn_send).setOnClickListener(v -> {
            MyMessage textMessage = IMessageBuilder.createTextMessage(sessionId, sessionType,
                    "ssss");
            textMessage.getMessage().setDirect(MsgDirectionEnum.Out);
            IMessageController.sendMessage(textMessage);
            messageListPanel.update(textMessage);

        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIntent();
    }

    private void parseIntent() {
        Bundle arguments = getArguments();
        sessionId = arguments.getString(Extras.EXTRA_ACCOUNT);
        sessionType = (SessionType) arguments.getSerializable(Extras.EXTRA_SESSION_TYPE);
        MyMessage anchor = (MyMessage) arguments.getSerializable(Extras.EXTRA_ANCHOR);
        Log.e(TAG, "parseIntent: " + getActivity());
        container = new Container(getActivity(), sessionId, sessionType, this);


        if (messageListPanel == null) {
            messageListPanel = new MessageListPanelEx(container, rootView, anchor, false, true);
        } else {
//            messageListPanel.reload(container, anchor);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
