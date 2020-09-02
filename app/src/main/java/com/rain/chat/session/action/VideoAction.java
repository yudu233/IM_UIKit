package com.rain.chat.session.action;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rain.chat.R;
import com.rain.chat.base.IM;
import com.rain.chat.glide.GlideImageLoader;
import com.rain.chat.session.module.CustomerBaseAction;
import com.rain.crow.PhotoPick;
import com.rain.crow.bean.MediaData;
import com.rain.crow.controller.PhotoPickConfig;
import com.rain.crow.impl.PhotoSelectCallback;
import com.rain.crow.utils.MimeType;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @Author : Rain
 * @CreateDate : 2020/7/29 14:32
 * @Version : 1.0
 * @Descroption : 视频消息功能按钮
 */
public class VideoAction extends CustomerBaseAction {

    public VideoAction() {
        super(R.drawable.action_video_selector, R.string.input_panel_video);
    }

    @Override
    public void onClick() {

        new MaterialDialog.Builder(getActivity())
                .title(getActivity().getString(R.string.action_video_title))
                .items(R.array.pick_video_arrays)
                .itemsCallback((dialog, itemView, position, text) -> {
                    if (position == 0) {
                        //拍摄
                        showVideoCamera();
                    } else if (position == 1) {
                        //相册
                        showPhotoAlbum();
                    }
                }).show();


    }

    private void showVideoCamera() {
        // FIXME: 2020/8/27 录制视频实现
    }

    private void showPhotoAlbum() {
        PhotoPick.from(getActivity())
                .imageLoader(new GlideImageLoader())
                .pickMode(PhotoPickConfig.MODE_PICK_MORE)
                .setMimeType(MimeType.ofVideo())
                .maxPickSize(9)
                .setCallback((PhotoSelectCallback) photos ->
                        sendVideoMessages(photos))
                .build();
    }

    @SuppressLint("CheckResult")
    private void sendVideoMessages(ArrayList<MediaData> photos) {
        // FIXME: 2020/8/27 压缩

        //发送
        Flowable.fromIterable(photos)
                .map(mediaData -> new File(mediaData.getCompressionPath()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            MediaPlayer mediaPlayer = getVideoMediaPlayer(data);
                            int duration = mediaPlayer == null ? 0 : mediaPlayer.getDuration();
                            int width = mediaPlayer == null ? 0 : mediaPlayer.getVideoWidth();
                            int height = mediaPlayer == null ? 0 : mediaPlayer.getVideoWidth();
                            MyMessage message = IM.getIMessageBuilder().createVideoMessage(
                                    getAccount(), getSessionType(), data, duration, width, height,
                                    data.getName());
                            getContainer().proxy.sendMessage(message);
                        }
                );
    }

    /**
     * 获取视频mediaPlayer
     *
     * @param file 视频文件
     * @return mediaPlayer
     */
    private MediaPlayer getVideoMediaPlayer(File file) {
        try {
            return MediaPlayer.create(getActivity(), Uri.fromFile(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
