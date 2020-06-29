package com.rain.chat;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.rain.chat.glide.GlideImageLoader;
import com.rain.chat.glide.GlideUtils;
import com.rain.library.controller.PhotoPickConfig;
import com.rain.library.impl.PhotoSelectCallback;
import com.rain.library.utils.MimeType;
import com.rain.messagelist.MsgAdapter;
import com.rain.messagelist.listener.SessionEventListener;
import com.rain.messagelist.listener.ViewHolderEventListener;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.message.SessionType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.model.ImageLoader;
import com.ycbl.im.uikit.msg.IMessageBuilder;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class MessageActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        // 该帐号为示例，请先注册
        String account = getIntent().getStringExtra("account");
        Log.e(TAG, "account: " + account );
        // 以单聊类型为例
        SessionType sessionType = SessionType.P2P;
        String text = "this is an example";

        List<IMessage> iMessages = new ArrayList<>();
        MsgAdapter msgAdapter = new MsgAdapter(iMessages, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(msgAdapter);


        findViewById(R.id.btn_receive).setOnClickListener(v -> {
            MyMessage textMessage = IMessageBuilder.createTextMessage(account, sessionType,
                    text + msgAdapter.getData().size());
            textMessage.getMessage().setDirect(MsgDirectionEnum.In);
            msgAdapter.addMessage(textMessage, false);
            IMessageBuilder.sendMessage(textMessage);
        });

        findViewById(R.id.btn_send).setOnClickListener(v -> {
            MyMessage textMessage = IMessageBuilder.createTextMessage(account, sessionType,
                    text + msgAdapter.getData().size());
            textMessage.getMessage().setDirect(MsgDirectionEnum.Out);
            msgAdapter.addMessage(textMessage, false);
            IMessageBuilder.sendMessage(textMessage);

        });

        findViewById(R.id.btn_sendImage).setOnClickListener(v -> new PhotoPickConfig
                .Builder(MessageActivity.this)
                .imageLoader(new GlideImageLoader())
                .setMimeType(MimeType.TYPE_IMAGE)
                .setCallback((PhotoSelectCallback) photos -> {
                    File file = new File(photos.get(0).getOriginalPath());
                    MyMessage imageMessage = IMessageBuilder.createImageMessage(
                            account, sessionType, file, file.getName());
                    imageMessage.getMessage().setDirect(MsgDirectionEnum.Out);
                    msgAdapter.addMessage(imageMessage, false);
                    IMessageBuilder.sendMessage(imageMessage);

                }).build());

        findViewById(R.id.btn_sendVideo).setOnClickListener(v -> new PhotoPickConfig
                .Builder(MessageActivity.this)
                .imageLoader(new GlideImageLoader())                //图片加载方式，支持任意第三方图片加载库
                .setMimeType(MimeType.TYPE_VIDEO)     //显示文件类型，默认全部（全部、图片、视频）
                .setCallback((PhotoSelectCallback) photos -> {
                    File file = new File(photos.get(0).getOriginalPath());
                    MediaPlayer mediaPlayer = getVideoMediaPlayer(file);
                    MyMessage videoMessage = IMessageBuilder.createVideoMessage(
                            account, sessionType, file, mediaPlayer.getDuration(),
                            mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight(), file.getName());
                    videoMessage.getMessage().setDirect(MsgDirectionEnum.Out);
                    msgAdapter.addMessage(videoMessage, false);
                    IMessageBuilder.sendMessage(videoMessage);

                }).build());

        //模拟铁粉
        List<String> fans = Arrays.asList("1", "2", "3", "4", "5");
        msgAdapter.setImageLoader(new ImageLoader() {
            @Override
            public void loadAvatarImage(FrameLayout frameLayout, boolean isReceiveMessage, String account) {
                if (isReceiveMessage && fans.contains(account)) {
                    ImageView imageView = new ImageView(MessageActivity.this);
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
            public void loadMessageImage(AppCompatImageView imageView, int width, int height, String path) {
                GlideUtils.loadImage(MessageActivity.this, path, width, height, imageView);
            }
        });

        msgAdapter.setSessionEventListener(new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMessage message) {
                Toast.makeText(MessageActivity.this, "头像点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAvatarLongClicked(Context context, IMessage message) {
                Toast.makeText(MessageActivity.this, "头像长按", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAckMsgClicked(Context context, IMessage message) {

            }
        });

        msgAdapter.setViewHolderEventListener(new ViewHolderEventListener() {
            @Override
            public boolean onViewHolderLongClick(View clickView, View viewHolderView, IMessage item) {
                Toast.makeText(MessageActivity.this, "消息长按", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onFailedBtnClick(IMessage resendMessage) {

            }

            @Override
            public void onPictureViewHolderClick(AppCompatImageView imageView, IMessage message) {
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation(MainActivity.this,
//                                imageView, "imageMessage");
//                Intent intent = new Intent(MainActivity.this, PreviewImageActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("message", message);
//                intent.putExtras(bundle);
//                startActivity(intent, optionsCompat.toBundle());
//
                List<IMessage> data = msgAdapter.getData();

                List<String> messages = new ArrayList<>();
                for (IMessage msg : data) {
                    if (msg.getMsgType() == MessageType.image) {
                        messages.add(msg.getMediaPath());
                    }
                }

                ImagePreview.getInstance().setContext(MessageActivity.this)
                        .setIndex(0)
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


    /**
     * 获取视频mediaPlayer
     *
     * @param file 视频文件
     * @return mediaPlayer
     */
    private MediaPlayer getVideoMediaPlayer(File file) {
        try {
            return MediaPlayer.create(this, Uri.fromFile(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
