package com.rain.messagelist.viewholder;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.rain.messagelist.MsgAdapter;
import com.rain.messagelist.R;
import com.rain.messagelist.listener.SessionEventListener;
import com.rain.messagelist.listener.ViewHolderEventListener;
import com.rain.messagelist.message.SessionType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.model.ImageLoader;
import com.rain.messagelist.utils.TimeUtil;
import com.rain.messagelist.widget.PerfectClickListener;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgViewHolderBase
 * @CreateDate: 2020/6/6 16:36
 * @Describe:
 */
public abstract class MsgViewHolderBase extends BaseItemProvider<IMessage, BaseViewHolder> {

    private static final String TAG = "MsgViewHolderBase";

    protected View view;
    protected IMessage message;
    private AppCompatImageView alertButton, avatarLeft, avatarRight;
    private ProgressBar progressBar;
    private LinearLayout bodyContainer;
    private FrameLayout avatarLeftLayout, avatarRightLayout, contentContainer;
    private AppCompatTextView timeTextView, nameTextView, readReceiptTextView, ackMsgTextView;

    public MultipleItemRvAdapter adapter;

    public MsgViewHolderBase(MultipleItemRvAdapter adapter) {
        this.adapter = adapter;
    }

    // 根据layout id查找对应的控件
    protected <T extends View> T findViewById(int id) {
        return (T) view.findViewById(id);
    }

    abstract protected int getContentResId();

//    // 在该接口中根据layout对各控件成员变量赋值
//    abstract protected void inflateContentView();
//
//    // 将消息数据项与内容的view进行绑定
//    abstract protected void bindContentView();

    // 内容区域长按事件响应处理。该接口的优先级比adapter中有长按事件的处理监听高
    // 当该接口返回为true时，adapter的长按事件监听不会被调用到。
    protected boolean onItemLongClick() {
        return false;
    }

    // 当是接收到的消息时，内容区域背景的drawable id
    protected int leftBackground() {
        return 0;
    }

    // 当是发送出去的消息时，内容区域背景的drawable id
    protected int rightBackground() {
        return 0;
    }

    // 返回该消息是不是居中显示
    protected boolean isMiddleItem() {
        return false;
    }

    // 是否显示头像，默认为显示
    protected boolean isShowHeadImage() {
        return true;
    }

    // 是否显示气泡背景，默认为显示
    protected boolean isShowBubble() {
        return true;
    }

    // 是否显示已读，默认为显示
    protected boolean shouldDisplayReceipt() {
        return true;
    }

    // 判断消息方向，是否是接收到的消息
    protected boolean isReceivedMessage() {
        return message.isReceivedMessage();
    }

    protected final MsgAdapter getMsgAdapter() {
        return (MsgAdapter) adapter;
    }

    // 设置FrameLayout子控件的gravity参数
    protected final void setGravity(View view, int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = gravity;
    }

    //是否显示昵称
    protected boolean shouldDisplayNick() {
        return message.getSessionType() == SessionType.Team.getValue() && isReceivedMessage() && !isMiddleItem();
    }

    // 设置控件的长宽
    protected void setLayoutParams(int width, int height, View views) {
            ViewGroup.LayoutParams maskParams = views.getLayoutParams();
            maskParams.width = width;
            maskParams.height = height;
        views.setLayoutParams(maskParams);
    }

    @Override
    public int layout() {
        return R.layout.im_message_item;
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        this.view = holder.itemView;
        this.message = data;

        inflate();
        refresh();
    }

    protected final void inflate() {
        timeTextView = findViewById(R.id.message_item_time);
        avatarLeftLayout = findViewById(R.id.rll_avatar_left);
        avatarRightLayout = findViewById(R.id.rll_avatar_right);
        avatarLeft = findViewById(R.id.imv_avatar_left);
        avatarRight = findViewById(R.id.imv_avatar_right);

        nameTextView = findViewById(R.id.message_item_nickname);
        bodyContainer = findViewById(R.id.message_item_body);
        progressBar = findViewById(R.id.message_item_progress);
        alertButton = findViewById(R.id.message_item_alert);
        readReceiptTextView = findViewById(R.id.textViewAlreadyRead);
        ackMsgTextView = findViewById(R.id.team_ack_msg);
        contentContainer = findViewById(R.id.message_item_content);

        // 这里只要inflate出来后加入一次即可
        if (contentContainer.getChildCount() == 0) {
            View.inflate(view.getContext(), getContentResId(), contentContainer);
        }
    }

    protected final void refresh() {
        setHeadImageView();
        setNameTextView();
        setTimeTextView();
        setMsgStatus();
        setOnClickListener();
        setLongClickListener();
        setContent();
        setReadReceipt();
        setAckMsg();
    }


    /**
     * 设置头像
     */
    private void setHeadImageView() {

        if (!isShowHeadImage() || isMiddleItem()) {
            avatarLeftLayout.setVisibility(View.GONE);
            avatarRightLayout.setVisibility(View.GONE);
            return;
        }
        Log.d(TAG, "setHeadImageView: " + isReceivedMessage());
        Log.d(TAG, "setHeadImageView: " + isShowHeadImage() + isMiddleItem());
        FrameLayout showView = isReceivedMessage() ? avatarLeftLayout : avatarRightLayout;
        FrameLayout hideView = isReceivedMessage() ? avatarRightLayout : avatarLeftLayout;
        AppCompatImageView avatarView = isReceivedMessage() ? avatarLeft : avatarRight;
        hideView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
        //TODO: 2020/6/18 加载头像
        //TODO: 2020/6/18 头像添加其他图标
        getMsgAdapter().getImageLoader().loadAvatarImage(showView, isReceivedMessage(), message.getFromAccount());
    }

    /**
     * 设置用户昵称
     */
    private void setNameTextView() {
        // TODO: 2020/6/9 需要先判断是否是群聊且是收到的消息并不居中（非时间/tips消息）
        if (!shouldDisplayNick()) {
            nameTextView.setVisibility(View.GONE);
            return;
        }
        nameTextView.setVisibility(View.VISIBLE);
        nameTextView.setText(message.getUser().getName());
    }

    /**
     * 设置消息时间显示
     */
    private void setTimeTextView() {
        // TODO: 2020/6/9 需要先判断该条消息是否需要显示时间
        if (getMsgAdapter().needShowTime(message)) {
            timeTextView.setVisibility(View.VISIBLE);
        } else {
            timeTextView.setVisibility(View.GONE);
            return;
        }

        String text = TimeUtil.getTimeShowString(message.getTime(), false);
        timeTextView.setText(text);
    }

    /**
     * 设置消息状态，更新UI
     * 包含：发送中、发送失败
     */
    private void setMsgStatus() {

    }

    /**
     * View单击事件
     */
    private void setOnClickListener() {
        // 重发按钮响应事件
        if (adapter != null) {
            alertButton.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    getMsgAdapter().getViewHolderEventListener().onFailedBtnClick(message);
                }
            });
        }

        // 内容区域点击事件响应， 相当于点击了整项Item
        contentContainer.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //分发到具体的ViewHolder
                onClick(v);
            }
        });

        // 头像点击事件响应
        if (getMsgAdapter().getSessionEventListener() != null) {
            PerfectClickListener avatarClickListener = new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    getMsgAdapter().getSessionEventListener().onAvatarClicked(mContext, message);
                }
            };
            avatarLeftLayout.setOnClickListener(avatarClickListener);
            avatarRightLayout.setOnClickListener(avatarClickListener);
        }
    }

    /**
     * View长按事件
     */
    private void setLongClickListener() {

        if (getMsgAdapter().getViewHolderEventListener() != null) {
            View.OnLongClickListener onItemLongClickListener = v -> {
                if (!onItemLongClick()) {
                    getMsgAdapter().getViewHolderEventListener().onViewHolderLongClick(contentContainer, v, message);
                    return true;
                }
                return false;
            };
            //消息长按事件响应处理
            contentContainer.setOnLongClickListener(onItemLongClickListener);
        }


        // 头像长按事件响应处理
        if (getMsgAdapter().getSessionEventListener() != null) {
            View.OnLongClickListener avatarLongClickListener = v -> {
                getMsgAdapter().getSessionEventListener().onAvatarLongClicked(mContext, message);
                return true;
            };
            avatarLeftLayout.setOnLongClickListener(avatarLongClickListener);
            // avatarRight.setOnLongClickListener(avatarLongClickListener);
        }
    }


    /**
     * 消息UI展示调整
     */
    private void setContent() {
        if (!isShowBubble() && !isMiddleItem()) {
            return;
        }
        // 调整container的位置
        int index = isReceivedMessage() ? 0 : 4;
        if (bodyContainer.getChildAt(index) != contentContainer) {
            bodyContainer.removeView(contentContainer);
            bodyContainer.addView(contentContainer, index);
        }

        if (isMiddleItem()) {
            setGravity(bodyContainer, Gravity.CENTER);
        } else {
            if (isReceivedMessage()) {
                setGravity(bodyContainer, Gravity.LEFT);
                contentContainer.setBackgroundResource(leftBackground());
            } else {
                setGravity(bodyContainer, Gravity.RIGHT);
                contentContainer.setBackgroundResource(rightBackground());
            }
        }
    }

    /**
     * 设置消息已读
     */
    private void setReadReceipt() {
        if (shouldDisplayReceipt() && !TextUtils.isEmpty(getMsgAdapter().getUuid()) && message.getUuid().equals(getMsgAdapter().getUuid())) {
            readReceiptTextView.setVisibility(View.VISIBLE);
        } else {
            readReceiptTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 已读回执
     */
    private void setAckMsg() {
        // TODO: 2020/6/16 暂不实现
    }


}
