package com.rain.chat.session.viewholder;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.netease.nimlib.sdk.msg.attachment.LocationAttachment;
import com.rain.chat.R;
import com.rain.chat.glide.GlideUtils;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.viewholder.MsgViewHolderBase;
import com.ycbl.im.uikit.impl.NimUIKitImpl;
import com.ycbl.im.uikit.msg.models.MyMsgAttachment;

/**
 * @Author : Rain
 * @CreateDate : 2020/9/3 17:06
 * @Version : 1.0
 * @Descroption : 位置消息
 */
public class MsgViewHolderLocation extends MsgViewHolderBase {

    private LocationAttachment location;

    public MsgViewHolderLocation(MultipleItemRvAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_location;
    }

    @Override
    public int viewType() {
        return MessageType.location.getValue();
    }

    @Override
    public void convert(@NonNull BaseViewHolder holder, IMessage data, int position) {
        super.convert(holder, data, position);
        changeView();
        MyMsgAttachment myMsgAttachment = (MyMsgAttachment) message.getAttachment();
        location = (LocationAttachment) myMsgAttachment.getAttachment();

        holder.setText(R.id.txv_locationAddress, location.getAddress());
        AppCompatImageView mapView = holder.itemView.findViewById(R.id.mapView);
        // FIXME: 2020/9/4 配置地图Key
        GlideUtils
                .loadImage(context, String.format(context.getString(R.string.nim_location_map),
                        location.getLongitude(),
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getLatitude(),
                        "c6edfdfa31b03ae98d37da5d5a9b6376"), mapView);
    }

    private void changeView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.width = ScreenUtils.getScreenWidth() / 6 * 4;
        layoutParams.height = SizeUtils.dp2px(180f);
        view.findViewById(R.id.mapRootView).setLayoutParams(layoutParams);
    }

    @Override
    protected void onItemClick() {
        super.onItemClick();
        if (NimUIKitImpl.getLocationProvider() != null) {
            NimUIKitImpl.getLocationProvider()
                    .openMap(context, location.getLongitude(),
                            location.getLatitude(), location.getAddress());
        }
    }
}
