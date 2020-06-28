package com.rain.messagelist;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.ProviderDelegate;
import com.rain.messagelist.listener.SessionEventListener;
import com.rain.messagelist.listener.ViewHolderEventListener;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.MsgViewHolderFactory;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.model.ImageLoader;
import com.rain.messagelist.viewholder.MsgViewHolderBase;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgAdaper01
 * @CreateDate: 2020/6/6 16:31
 * @Describe:
 */
public class MsgAdapter extends MultipleItemRvAdapter<IMessage, BaseViewHolder> {

    private String messageId;
    private ImageLoader imageLoader;
    private Activity activity;
    private SessionEventListener sessionEventListener;
    private ViewHolderEventListener viewHolderEventListener;

    public MsgAdapter(@Nullable List<IMessage> data, Activity activity) {
        super(data);
        finishInitialize();
        timedItems = new HashSet<>();
        this.activity = activity;
    }

    @Override
    protected int getViewType(IMessage message) {
        return message.getMsgType().getValue();
    }

    @Override
    public void registerItemProvider() {

        /**
         * 通过 {@link MsgViewHolderFactory#register(MessageType, Class)} 动态注册ViewHolder
         * 使用反射取出对应的Holder通过框架 {@link ProviderDelegate#registerProvider(BaseItemProvider)}
         * 将所有类型的Holder注册
         */
        List<Class<? extends MsgViewHolderBase>> holders = MsgViewHolderFactory.getAll();
        for (Class<? extends BaseItemProvider> holder : holders) {
            Constructor c = holder.getDeclaredConstructors()[0]; // 第一个显式的构造函数
            c.setAccessible(true);
            MsgViewHolderBase viewHolder = null;
            try {
                viewHolder = (MsgViewHolderBase) c.newInstance(new Object[]{this});
            } catch (Exception e) {
                e.printStackTrace();
            }
            mProviderDelegate.registerProvider(viewHolder);
        }


//        //动态注册viewHolder
//        Iterator<Map.Entry<MessageType, Class<? extends MsgViewHolderBase>>> iterator =
//                MsgViewHolderFactory.getAllViewHolders().entrySet().iterator();
//        while (iterator.hasNext()) {
//            try {
//                Map.Entry<MessageType, Class<? extends MsgViewHolderBase>> next = iterator.next();
//                Class<? extends MsgViewHolderBase> cls = next.getValue();
//                Constructor c = cls.getDeclaredConstructors()[0]; // 第一个显式的构造函数
//                c.setAccessible(true);
//                MsgViewHolderBase viewHolder = (MsgViewHolderBase) c.newInstance(new Object[]{this});
//                mProviderDelegate.registerProvider(viewHolder);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        //默认注册方式
        //mProviderDelegate.registerProvider(new MsgViewHolderText(this));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, IMessage item) {

        /**
         * helper对应的是某一行View的缓存，与Item不是一一对应的关系
         * 如下设计：item与helper是一一对应的关系，因此每次convert都需要拿当前回调的baseHolder进行convert，
         * RecyclerViewHolder可以从baseHolder中取出该行View所有的子View进行数据绑定（
         * 相当于需要经历inflate->refresh的过程）。
         */
        Class<? extends MsgViewHolderBase> cls = MsgViewHolderFactory.getViewHolderByType(item);
        Constructor c = cls.getDeclaredConstructors()[0];
        c.setAccessible(true);
        try {
            MsgViewHolderBase viewHolder = (MsgViewHolderBase) c.newInstance(new Object[]{this});
            viewHolder.convert(helper, item, helper.getAdapterPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据消息id获取位置
     *
     * @param msgId 消息id
     * @return message at list position
     */
    public int getMessagePositionById(String msgId) {
        for (IMessage message : getData()) {
            if (message.getUuid().contentEquals(msgId)) {
                return getData().indexOf(message);
            }
        }
        return -1;
    }

    /**
     * 添加消息
     *
     * @param message        消息体
     * @param scrollToBottom 是否滚动到底部
     */
    public void addMessage(IMessage message, boolean scrollToBottom) {
        if (message == null) return;
        getData().add(message);
        notifyItemRangeInserted(getData().size() - 1 + getLoadMoreViewCount(), 1);
        // TODO: 2020/6/6 滚动到底部
    }

    /**
     * 根据消息体移除消息
     *
     * @param message message to be deleted
     */
    public void deleteMessage(IMessage message) {
        if (message != null) {
            deleteMsgById(message.getUuid());
        }
    }

    /**
     * 根据消息id移除消息
     *
     * @param msgId 消息id
     */
    public void deleteMsgById(String msgId) {
        // TODO: 2020/6/18 如果该条消息有显示时间 是否需要删除时间(普通删除/撤回)
        int index = getMessagePositionById(msgId);
        if (index >= 0) {
            getData().remove(index);
            notifyItemRemoved(index);
        }
    }

    /**
     * 根据消息体删除多条消息
     *
     * @param messages messages list to be deleted
     */
    public void deleteMessages(List<IMessage> messages) {
        for (IMessage message : messages) {
            deleteMsgById(message.getUuid());
        }
    }

    /**
     * 根据消息id删除多条消息
     *
     * @param msgIds ids array of identifiers of messages to be deleted
     */
    public void deleteMsgById(String[] msgIds) {
        for (String msgId : msgIds) {
            deleteMsgById(msgId);
        }
    }

    /**
     * 更新消息
     *
     * @param position 位置角标 position
     * @param message  消息体 message
     */
    public void updateMessage(int position, IMessage message) {
        if (position < 0) return;
        setData(position, message);
        notifyItemChanged(position);
    }

    /**
     * 清空列表消息
     * clear messages at list
     */
    public void clearMessages() {
        getData().clear();
        notifyDataSetChanged();
    }

    /**
     * 获取消息列表数据
     *
     * @return messages get all messages data
     */
    public List<IMessage> getMessageList() {
        return getData();
    }

    public void setUuid(String messageId) {
        this.messageId = messageId;
    }

    public String getUuid() {
        return messageId;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setSessionEventListener(SessionEventListener listener) {
        this.sessionEventListener = listener;
    }

    public void setViewHolderEventListener(ViewHolderEventListener listener) {
        this.viewHolderEventListener = listener;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public Activity getActivity() {
        return activity;
    }

    public SessionEventListener getSessionEventListener() {
        return sessionEventListener;
    }

    public ViewHolderEventListener getViewHolderEventListener() {
        return viewHolderEventListener;
    }

    /**
     * *********************** 时间显示处理 ***********************
     */

    private Set<String> timedItems; // 需要显示消息时间的消息ID
    private IMessage lastShowTimeItem; // 用于消息时间显示,判断和上条消息间的时间间隔

    public boolean needShowTime(IMessage message) {
        return timedItems.contains(message.getUuid());
    }

    /**
     * 列表加入新消息时，更新时间显示
     *
     * @param items     消息列表
     * @param fromStart 是否是第一次加载
     * @param update    是否更新时间
     */
    public void updateShowTimeItem(List<IMessage> items, boolean fromStart, boolean update) {
        IMessage anchor = fromStart ? null : lastShowTimeItem;
        for (IMessage message : items) {
            if (setShowTimeFlag(message, anchor)) {
                anchor = message;
            }
        }

        if (update) {
            lastShowTimeItem = anchor;
        }
    }

    /**
     * 是否显示时间item
     */
    private boolean setShowTimeFlag(IMessage message, IMessage anchor) {
        boolean update = false;

        if (hideTimeAlways()) {
            setShowTime(message, false);
        } else {
            if (anchor == null) {
                setShowTime(message, true);
                update = true;
            } else {
                long time = anchor.getTime();
                long now = message.getTime();

                if (now - time == 0) {
                    // 消息撤回时使用
                    setShowTime(message, true);
                    lastShowTimeItem = message;
                    update = true;
                    // TODO: 2020/6/18 配置消息列表每隔多久显示一条消息时间信息
                    //NimUIKitImpl.getOptions().displayMsgTimeWithInterval)
                } else if (now - time < (300000)) {
                    setShowTime(message, false);
                } else {
                    setShowTime(message, true);
                    update = true;
                }
            }
        }

        return update;
    }

    private void setShowTime(IMessage message, boolean show) {
        if (show) {
            timedItems.add(message.getUuid());
        } else {
            timedItems.remove(message.getUuid());
        }
    }

    /**
     * 删除消息时
     *
     * @param messageItem
     * @param index
     */
    private void relocateShowTimeItemAfterDelete(IMessage messageItem, int index) {
        // 如果被删的项显示了时间，需要继承
        if (needShowTime(messageItem)) {
            setShowTime(messageItem, false);
            if (getData().size() > 0) {
                IMessage nextItem;
                if (index == getData().size()) {
                    //删除的是最后一项
                    nextItem = getItem(index - 1);
                } else {
                    //删除的不是最后一项
                    nextItem = getItem(index);
                }

                // 增加其他不需要显示时间的消息类型判断
                if (hideTimeAlways()) {
                    setShowTime(nextItem, false);
                    if (lastShowTimeItem != null && lastShowTimeItem != null
                            && lastShowTimeItem.isTheSame(messageItem)) {
                        lastShowTimeItem = null;
                        for (int i = getData().size() - 1; i >= 0; i--) {
                            IMessage item = getItem(i);
                            if (needShowTime(item)) {
                                lastShowTimeItem = item;
                                break;
                            }
                        }
                    }
                } else {
                    setShowTime(nextItem, true);
                    if (lastShowTimeItem == null
                            || (lastShowTimeItem != null && lastShowTimeItem.isTheSame(messageItem))) {
                        lastShowTimeItem = nextItem;
                    }
                }
            } else {
                lastShowTimeItem = null;
            }
        }
    }


    /**
     * 是否显示时间
     *
     * @return
     */
    private boolean hideTimeAlways() {
        // TODO: 2020/6/18 配置项可配置
        return false;
    }

}

