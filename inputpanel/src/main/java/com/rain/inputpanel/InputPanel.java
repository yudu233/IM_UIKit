package com.rain.inputpanel;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.rain.inputpanel.utils.EmoticonsKeyboardUtils;
import com.rain.inputpanel.widget.EmoticonsEditText;
import com.rain.inputpanel.widget.FuncLayout;

/**
 * @Author : Rain
 * @CreateDate : 2020/7/3 14:38
 * @Version : 1.0
 * @Descroption :
 */
public class InputPanel extends LinearLayout implements ViewTreeObserver.OnPreDrawListener {

    private static final String TAG = "InputPanel";
    private Context mContext;

    private AppCompatImageView
            buttonVoiceOrText,          //录音&文本切换按钮
            buttonFaceOrText,           //表情&文本切换按钮
            buttonMoreFunctionInText;   //更多功能布局按钮
    private AppCompatButton buttonVoice,//录音按钮
            buttonSendMessage;          //发送消息按钮
    private EmoticonsEditText inputTextView;    //文本输入框
    private FrameLayout sendLayoutView;         //发送消息&更多布局父控件
    private FuncLayout funcLayout;              //更多功能布局

    private boolean isShowFaceView = false;

    public InputPanel(Context context) {
        super(context);
        init(context);
    }

    public InputPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        inflate(context, R.layout.view_chatinput, this);
        buttonVoiceOrText = findViewById(R.id.btn_voice_or_text);
        buttonVoice = findViewById(R.id.btn_voice);
        inputTextView = findViewById(R.id.et_chat);
        buttonFaceOrText = findViewById(R.id.btn_face_or_text);
        sendLayoutView = findViewById(R.id.sendLayout);
        buttonMoreFunctionInText = findViewById(R.id.buttonMoreFunctionInText);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);
        funcLayout = findViewById(R.id.funcLayout);

        buttonVoiceOrText.setOnClickListener(mOnClickListener);
        buttonFaceOrText.setOnClickListener(mOnClickListener);

        getViewTreeObserver().addOnPreDrawListener(this);

    }

    protected OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_voice_or_text) {
                if (inputTextView.isShown()) {
                    //展示录音控件
                    showVoiceView();
                } else {
                    //展示输入框控件
                    showTextView();
                }
            } else if (id == R.id.btn_face_or_text) {
                if (isShowFaceView) {
                    //展示输入框控件
                    isShowFaceView = false;
                    showTextView();
                } else {
                    //展示表情控件
                    isShowFaceView = true;
                    showFaceView();
                }
            }
        }
    };

    /**
     * 展示表情控件
     * 1.录音&文字按钮更换为录音按钮
     * 2.展示emoji表情区域
     * 3.表情&文字按钮切换为软键盘按钮
     * 4.关闭软键盘
     */
    private void showFaceView() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(inputTextView);
        buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
        // TODO: 2020/7/3 展示表情区域
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                funcLayout.setVisibility(VISIBLE);
            }
        }, 500);
        buttonFaceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
    }

    /**
     * 展示录音控件
     * 1.录音按钮切换为软键盘按钮
     * 2.展示录音控件，隐藏输入框控件
     * 3.表情按钮重置
     * 4.如果软键盘开启则关闭
     * 5.功能区域开启则隐藏
     */
    private void showVoiceView() {
        buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
        inputTextView.setVisibility(GONE);
        buttonVoice.setVisibility(VISIBLE);
        reset();
    }

    /**
     * 展示输入框控件
     * 1.软键盘按钮切换为录音按钮
     * 2.展示录音控件，隐藏输入框控件
     * 3.表情按钮重置
     * 4.开启软键盘
     * 5.功能区域开启则隐藏
     */
    private void showTextView() {
        EmoticonsKeyboardUtils.openSoftKeyboard(inputTextView);
        buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
        buttonVoice.setVisibility(GONE);
        inputTextView.setVisibility(VISIBLE);
        funcLayout.setVisibility(GONE);
    }

    /**
     * 重置UI，恢复默认展示
     */
    private void reset() {
        buttonFaceOrText.setImageResource(R.drawable.icon_face_normal);
        EmoticonsKeyboardUtils.closeSoftKeyboard(inputTextView);
        funcLayout.hideAllFuncView();
    }

    /**
     * 功能区域是否显示
     *
     * @return
     */
    private boolean isFuncViewShow() {
        return funcLayout.getVisibility() == VISIBLE;
    }

    /**
     * 软键盘是否显示
     *
     * @return
     */
    public boolean isKeyboardVisible() {
        return false;
    }

    @Override
    public boolean onPreDraw() {
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnPreDrawListener(this);
    }


}
