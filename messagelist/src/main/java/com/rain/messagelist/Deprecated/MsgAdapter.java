package com.rain.messagelist.Deprecated;

import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.R;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: MsgAdapter
 * @CreateDate: 2020/6/6 10:28
 * @Describe:
 */
public class MsgAdapter extends BaseMultiItemQuickAdapter<MessageMultipleItem, BaseViewHolder> {


    /**
     * viewType->view holder class
     */
    private SparseArray<Class<? extends RecyclerViewHolder>> holderClasses;

    /**
     * viewType->view holder instance
     */
    private Map<Integer, Map<IMessage, RecyclerViewHolder>> multiTypeViewHolders;

    private Map<Class<? extends MsgViewHolderBase>, Integer> holder2ViewType;


    public MsgAdapter(List<MessageMultipleItem> data) {
        super(data);
        holder2ViewType = new HashMap<>();
        Iterator<Map.Entry<Integer, Class<? extends MsgViewHolderBase>>> iterator =
                MsgViewHolderFactory.getAllViewHolders().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Class<? extends MsgViewHolderBase>> next = iterator.next();
            holder2ViewType.put(next.getValue(), next.getKey());
            addItemType(next.getKey(), R.layout.im_message_item, next.getValue());
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageMultipleItem item) {
        RecyclerViewHolder h = multiTypeViewHolders.get(helper.getItemViewType()).get(item.getMessage());
        if (h == null) {
            // build
            try {
                Class<? extends RecyclerViewHolder> cls = holderClasses.get(helper.getItemViewType());
                Constructor c = cls.getDeclaredConstructors()[0]; // 第一个显式的构造函数
                c.setAccessible(true);
                h = (RecyclerViewHolder) c.newInstance(new Object[]{this});
                multiTypeViewHolders.get(helper.getItemViewType()).put(item.getMessage(), h);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // convert
        if (h != null) {
            h.convert(helper, item.getMessage(), helper.getAdapterPosition());
        }
    }

    protected void addItemType(int type, int layoutResId, Class<? extends RecyclerViewHolder> viewHolderClass) {
        addItemType(type, layoutResId);
        // view holder class
        if (holderClasses == null) {
            holderClasses = new SparseArray<>();
        }
        holderClasses.put(type, viewHolderClass);

        // view holder
        if (multiTypeViewHolders == null) {
            multiTypeViewHolders = new HashMap<>();
        }
        multiTypeViewHolders.put(type, new HashMap<IMessage, RecyclerViewHolder>());
    }

    @Override
    public int getItemViewType(int position) {
        return holder2ViewType.get(MsgViewHolderFactory.getViewHolderByType(getData().get(position).getMessage()));
    }

    /**
     * 添加消息
     *
     * @param message        消息体
     * @param scrollToBottom 是否滚动到底部
     */
    public void addMessage(MessageMultipleItem message, boolean scrollToBottom) {
        getData().add(message);
        notifyItemRangeInserted(getData().size() - 1 + getLoadMoreViewCount(), 1);
        // TODO: 2020/6/6 滚动到底部
    }

    public void deleteMessage(IMessage message){
        getData().remove(message);
    }

    /**
     * 获取消息列表数据
     *
     * @return messages
     */
    public List<IMessage> getMessageList() {
        List<IMessage> messages = new ArrayList<>();
        for (MessageMultipleItem item : getData()) {
            IMessage message = item.getMessage();
            messages.add(message);
        }
        return messages;
    }
}
