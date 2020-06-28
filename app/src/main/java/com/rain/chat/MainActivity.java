package com.rain.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.rain.chat.glide.GlideImageLoader;
import com.rain.crow.bean.MediaData;
import com.rain.crow.controller.PhotoPickConfig;
import com.rain.crow.impl.PhotoSelectCallback;
import com.rain.crow.utils.MimeType;
import com.rain.messagelist.MsgAdapter;
import com.rain.messagelist.listener.SessionEventListener;
import com.rain.messagelist.listener.ViewHolderEventListener;
import com.rain.messagelist.message.MessageType;
import com.rain.messagelist.model.IMessage;
import com.rain.messagelist.model.ImageLoader;
import com.ycbl.im.uikit.msg.models.DefaultUser;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        // 该帐号为示例，请先注册
        String account = "testAccount";
        // 以单聊类型为例
        SessionTypeEnum sessionType = SessionTypeEnum.P2P;
        String text1 = "this is an example";
        String text2 = "this is an example!!!";


//        MyMessage textMessage11 = IMessageBuilder.createTextMessage(
//                "aaa", SessionType.P2P, "this is an example");
//        MyMessage textMessage22 = IMessageBuilder.createImageMessage(
//                "aaa", SessionType.P2P, null, "");
        Log.d(TAG, "onCreate: " + Environment.getExternalStorageDirectory());

        IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, text1);
        IMMessage textMessage1 = MessageBuilder.createTextMessage(account, sessionType, text2);


        textMessage.setFromAccount("1");
        textMessage.setDirect(MsgDirectionEnum.In);
        textMessage1.setDirect(MsgDirectionEnum.Out);

        List<IMessage> iMessages = new ArrayList<>();
        iMessages.add(new MyMessage(MessageType.text, textMessage, new DefaultUser(textMessage)));
        iMessages.add(new MyMessage(MessageType.text, textMessage1, new DefaultUser(textMessage)));

        MsgAdapter msgAdapter = new MsgAdapter(iMessages, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(msgAdapter);

        findViewById(R.id.btn_receive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType,
                        text1 + msgAdapter.getData().size());
                textMessage.setDirect(MsgDirectionEnum.In);
                msgAdapter.addMessage(new MyMessage(MessageType.text, textMessage,
                        new DefaultUser(textMessage)), false);
            }
        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType,
                        text1 + msgAdapter.getData().size());
                textMessage1.setDirect(MsgDirectionEnum.Out);

                msgAdapter.addMessage(new MyMessage(MessageType.text, textMessage,
                        new DefaultUser(textMessage)), false);
            }
        });

        findViewById(R.id.btn_sendImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PhotoPickConfig
                        .Builder(MainActivity.this)
                        .imageLoader(new GlideImageLoader())                //图片加载方式，支持任意第三方图片加载库
                        .spanCount(PhotoPickConfig.GRID_SPAN_COUNT)         //相册列表每列个数，默认为3
                        .pickMode(PhotoPickConfig.MODE_PICK_SINGLE)           //设置照片选择模式为单选，默认为单选
                        .maxPickSize(PhotoPickConfig.DEFAULT_CHOOSE_SIZE)   //多选时可以选择的图片数量，默认为1张
                        .setMimeType(MimeType.TYPE_ALL)     //显示文件类型，默认全部（全部、图片、视频）
                        .showCamera(true)           //是否展示相机icon，默认展示
                        .clipPhoto(false)            //是否开启裁剪照片功能，默认关闭
                        .clipCircle(false)          //是否裁剪方式为圆形，默认为矩形
                        .showOriginal(true)         //是否显示原图按钮，默认显示
                        .startCompression(true)     //是否开启压缩，默认true
                        .setCallback(new PhotoSelectCallback() {
                            @Override
                            public void selectResult(ArrayList<MediaData> photos) {

                                String originalPath = photos.get(0).getOriginalPath();
                                IMMessage imageMessage = MessageBuilder.createImageMessage(
                                        account, sessionType, new File(originalPath));
                                imageMessage.setDirect(MsgDirectionEnum.Out);
                                msgAdapter.addMessage(new MyMessage(MessageType.image, imageMessage,
                                        new DefaultUser(imageMessage)), false);

                            }
                        })     //回调
                        .build();
            }
        });

        //模拟铁粉
        List<String> fans = Arrays.asList("1", "2", "3", "4", "5");
        msgAdapter.setImageLoader(new ImageLoader() {
            @Override
            public void loadAvatarImage(FrameLayout frameLayout, boolean isReceiveMessage, String account) {
                if (isReceiveMessage && fans.contains(account)) {
                    ImageView imageView = new ImageView(MainActivity.this);
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
            public void loadMessageImage(AppCompatImageView imageView, String path) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            }

            @Override
            public void loadVideoImage(AppCompatImageView imageView, String path) {

            }
        });

        msgAdapter.setSessionEventListener(new SessionEventListener() {
            @Override
            public void onAvatarClicked(Context context, IMessage message) {
                Toast.makeText(MainActivity.this, "头像点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAvatarLongClicked(Context context, IMessage message) {
                Toast.makeText(MainActivity.this, "头像长按", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAckMsgClicked(Context context, IMessage message) {

            }
        });

        msgAdapter.setViewHolderEventListener(new ViewHolderEventListener() {
            @Override
            public boolean onViewHolderLongClick(View clickView, View viewHolderView, IMessage item) {
                Toast.makeText(MainActivity.this, "消息长按", Toast.LENGTH_SHORT).show();
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

                ImagePreview.getInstance().setContext(MainActivity.this)
                        .setIndex(0)
                        .setImageList(messages)
                        .setEnableClickClose(true)
                        .setEnableDragClose(true)
                        .setShowDownButton(false)
                        .start();

            }
        });


    }
}
