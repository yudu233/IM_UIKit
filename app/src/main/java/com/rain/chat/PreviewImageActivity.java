package com.rain.chat;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.rain.messagelist.model.IMessage;
import com.ycbl.im.uikit.msg.models.MyMessage;

/**
 * @Author : Rain
 * @CreateDate : 2020/6/24 11:57
 * @Version : 1.0
 * @Descroption :
 */
public class PreviewImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview_image);
        AppCompatImageView imageView = findViewById(R.id.imageView);

//        postponeEnterTransition();

        getWindow().setBackgroundDrawableResource(R.color.transparent);
        MyMessage message = (MyMessage) getIntent().getSerializableExtra("message");
        String mediaPath = message.getMediaPath();
        imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
//        supportStartPostponedEnterTransition();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 不使用finish()
        supportFinishAfterTransition();
    }
}
