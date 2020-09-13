package com.rain.chat;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.netease.nimlib.sdk.RequestCallback;
import com.rain.chat.base.IM;
import com.rain.chat.constant.Extras;
import com.rain.chat.glide.GlideUtils;
import com.rain.chat.mvp.MessageContract;
import com.rain.chat.mvp.MessageModel;
import com.rain.chat.mvp.MessagePresenter;
import com.rain.chat.session.action.ImageAction;
import com.rain.chat.session.action.LocationAction;
import com.rain.chat.session.action.VideoAction;
import com.rain.chat.session.list.MessageListPanelEx;
import com.rain.chat.session.module.Container;
import com.rain.chat.session.module.CustomerBaseAction;
import com.rain.chat.session.module.ModuleProxy;
import com.rain.crow.utils.Rlog;
import com.rain.inputpanel.XhsEmoticonsKeyBoard;
import com.rain.inputpanel.action.ActionController;
import com.rain.inputpanel.action.BaseAction;
import com.rain.inputpanel.data.EmoticonEntity;
import com.rain.inputpanel.emoji.Constants;
import com.rain.inputpanel.emoji.EmojiBean;
import com.rain.inputpanel.interfaces.EmoticonClickListener;
import com.rain.inputpanel.utils.SimpleCommonUtils;
import com.rain.inputpanel.widget.FuncLayout;
import com.rain.messagelist.MsgAdapter;
import com.rain.messagelist.listener.SessionEventListener;
import com.rain.messagelist.listener.ViewHolderEventListener;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.SessionType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.model.ImageLoader;
import com.ycbl.im.uikit.msg.controller.IMessageController;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.shinichi.library.ImagePreview;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MessageFragment
 * @CreateDate: 2020/6/29 20:56
 * @Describe:
 */
public class MessageFragment extends Fragment implements ModuleProxy,
        FuncLayout.OnFuncKeyBoardListener, MessageContract.IView {

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
    private MyMessage anchor;
    private MessagePresenter messagePresenter;
    private MsgAdapter msgAdapter;


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagePresenter = new MessagePresenter(new MessageModel(), this);
        Bundle arguments = getArguments();
        sessionId = arguments.getString(Extras.EXTRA_ACCOUNT);
        sessionType = (SessionType) arguments.getSerializable(Extras.EXTRA_SESSION_TYPE);
        anchor = (MyMessage) arguments.getSerializable(Extras.EXTRA_ANCHOR);
        container = new Container(getActivity(), sessionId, sessionType, this);

        //初始化功能消息菜单栏
        messagePresenter.initAction(container);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_message, viewGroup, false);
        inputView = rootView.findViewById(R.id.inputView);
        initInputView();
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseIntent();
    }

    private void parseIntent() {
        if (messageListPanel == null) {
            messageListPanel = new MessageListPanelEx(container, rootView, anchor, false, true);
        } else {
//            messageListPanel.reload(container, anchor);
        }
        adapterDefaultConfig();
    }

    private void adapterDefaultConfig() {
        messageListPanel.getRecyclerView().setOnTouchListener((v, event) -> {
            inputView.reset();
            return false;
        });
        msgAdapter = messageListPanel.getMsgAdapter();

        //模拟铁粉
        List<String> fans = Arrays.asList("1", "2", "3", "4", "5", "2018cytx3", "2018cytx2");
        msgAdapter.setImageLoader(new ImageLoader() {
            @Override
            public void loadAvatarImage(FrameLayout frameLayout, boolean isReceiveMessage, String account) {
                if (isReceiveMessage && fans.contains(account)) {
                    ImageView imageView = new ImageView(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            new ViewGroup.LayoutParams(50, 50));
                    imageView.setLayoutParams(lp);  //设置图片的大小
                    imageView.setImageBitmap(BitmapFactory.
                            decodeResource(getResources(), R.mipmap.icon_fans_avatar));
                    frameLayout.addView(imageView);
                    // TODO: 2020/6/20 加载用户头像
                }
            }

            @Override
            public void loadMessageImage(AppCompatImageView imageView, int width, int height,
                                         String path, String extension) {
                GlideUtils.loadImage(getContext(), path, width, height, imageView, extension);
            }
        });

        msgAdapter.setSessionEventListener(new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMessage message) {
                Toast.makeText(getContext(), "头像点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAvatarLongClicked(Context context, IMessage message) {
                Toast.makeText(getContext(), "头像长按", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAckMsgClicked(Context context, IMessage message) {

            }
        });

        msgAdapter.setViewHolderEventListener(new ViewHolderEventListener() {
            @Override
            public boolean onViewHolderLongClick(View clickView, View viewHolderView, IMessage item) {
                Toast.makeText(getContext(), "消息长按", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onFailedBtnClick(IMessage resendMessage) {

            }

            @Override
            public void onPictureViewHolderClick(AppCompatImageView imageView, IMessage message) {
                List<IMessage> data = msgAdapter.getData();

                List<String> messages = new ArrayList<>();
                for (IMessage msg : data) {
                    if (msg.getMsgType() == MessageType.image) {
                        Rlog.e(msg.getMediaPath() + "-------------");
                        messages.add(msg.getMediaPath());
                    }
                }

                ImagePreview.getInstance().setContext(getContext())
                        .setIndex(messages.indexOf(message.getMediaPath()))
                        .setImageList(messages)
                        .setEnableClickClose(true)
                        .setEnableDragClose(true)
                        .setShowDownButton(false)
                        .start();

            }

            @Override
            public void onVideoViewHolderClick(AppCompatImageView imageView, IMessage message) {
                Log.e(TAG, "onVideoViewHolderClick: ");
            }
        });
    }


    private void initInputView() {
        SimpleCommonUtils.initEmoticonsEditText(inputView.getEtChat());
        inputView.setAdapter(SimpleCommonUtils.getCommonAdapter(getActivity(), emoticonClickListener));
        inputView.addOnFuncKeyBoardListener(this);

        inputView.getEtChat().setOnSizeChangedListener((w, h, oldw, oldh) ->
                messageListPanel.doScrollToBottom());

        //发送文本消息
        inputView.getBtnSend().setOnClickListener(v -> {
            MyMessage message = IM.getIMessageBuilder().createTextMessage(
                    sessionId, sessionType, inputView.getEtChat().getText().toString());
            sendMessage(message);
            inputView.getEtChat().setText("");
        });

    }

    /**
     * 发送消息
     *
     * @param message
     */
    @Override
    public boolean sendMessage(MyMessage message) {
        IMessageController.sendMessage(message, false, new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                //消息发送成功
            }

            @Override
            public void onFailed(int code) {
                //消息发送失败

            }

            @Override
            public void onException(Throwable exception) {
                //异常
            }
        });

        //发送消息后，更新本地消息列表
        messageListPanel.onMsgSend(message);
        return true;
    }


    @Override
    public void onMessageIncoming(MyMessage message) {
        messageListPanel.onIncomingMessage(message);
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
        messageListPanel.doScrollToBottom();
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
        ActionController.getInstance().clearActions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public boolean isFullScreenFuncView(KeyEvent event) {
        return inputView.dispatchKeyEventInFullScreen(event);
    }

}
