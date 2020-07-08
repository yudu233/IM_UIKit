package com.rain.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rain.chat.constant.Extras;
import com.rain.chat.session.list.MessageListPanelEx;
import com.rain.chat.session.module.Container;
import com.rain.chat.session.module.ModuleProxy;
import com.rain.chat.weight.SimpleAppsGridView;
import com.rain.inputpanel.XhsEmoticonsKeyBoard;
import com.rain.inputpanel.data.EmoticonEntity;
import com.rain.inputpanel.emoji.Constants;
import com.rain.inputpanel.emoji.EmojiBean;
import com.rain.inputpanel.interfaces.EmoticonClickListener;
import com.rain.inputpanel.utils.SimpleCommonUtils;
import com.rain.inputpanel.widget.FuncLayout;
import com.rain.messagelist.message.SessionType;
import com.ycbl.im.uikit.msg.models.MyMessage;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MessageFragment
 * @CreateDate: 2020/6/29 20:56
 * @Describe:
 */
public class MessageFragment extends Fragment implements ModuleProxy, FuncLayout.OnFuncKeyBoardListener {

    private static final String TAG = "MessageFragment";
    // p2p对方Account或者群id
    protected String sessionId;

    //会话类型
    protected SessionType sessionType;

    private Container container;

    //列表module
    protected MessageListPanelEx messageListPanel;
    private View rootView;
    private XhsEmoticonsKeyBoard inputView;


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, viewGroup, false);
        inputView = rootView.findViewById(R.id.inputView);
        initInputView();
//        rootView.findViewById(R.id.btn_send).setOnClickListener(v -> {
//            MyMessage textMessage = IMessageBuilder.createTextMessage(sessionId, sessionType,
//                    "ssss");
//            textMessage.getMessage().setDirect(MsgDirectionEnum.Out);
//            IMessageController.sendMessage(textMessage);
//            messageListPanel.update(textMessage);
//
//        });
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


    private void initInputView() {
        SimpleCommonUtils.initEmoticonsEditText(inputView.getEtChat());
        inputView.setAdapter(SimpleCommonUtils.getCommonAdapter(getActivity(), emoticonClickListener));
        inputView.addOnFuncKeyBoardListener(this);
//        inputView.addFuncView(new SimpleAppsGridView(getActivity()));

    }


    private EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(inputView.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {

                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index = inputView.getEtChat().getSelectionStart();
                    Editable editable = inputView.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public void OnFuncPop(int height) {

    }

    @Override
    public void OnFuncClose() {

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
