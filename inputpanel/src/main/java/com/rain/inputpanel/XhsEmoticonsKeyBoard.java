package com.rain.inputpanel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.rain.inputpanel.action.ActionsPanel;
import com.rain.inputpanel.adapter.PageSetAdapter;
import com.rain.inputpanel.data.PageSetEntity;
import com.rain.inputpanel.utils.EmoticonsKeyboardUtils;
import com.rain.inputpanel.widget.AutoHeightLayout;
import com.rain.inputpanel.widget.EmoticonsEditText;
import com.rain.inputpanel.widget.EmoticonsFuncView;
import com.rain.inputpanel.widget.EmoticonsIndicatorView;
import com.rain.inputpanel.widget.EmoticonsToolBarView;
import com.rain.inputpanel.widget.FuncLayout;

import java.util.ArrayList;


public class XhsEmoticonsKeyBoard extends AutoHeightLayout implements View.OnClickListener,
        EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsToolBarView.OnToolBarItemClickListener,
        EmoticonsEditText.OnBackKeyClickListener,
        FuncLayout.OnFuncChangeListener {

    //表情
    public static final int FUNC_TYPE_EMOTION = -1;
    //更多功能
    public static final int FUNC_TYPE_APPPS = -2;

    protected LayoutInflater mInflater;

    private AppCompatImageView
            buttonVoiceOrText,          //录音&文本切换按钮
            buttonFaceOrText,           //表情&文本切换按钮
            buttonMoreFunctionInText;   //更多功能布局按钮
    private AppCompatButton buttonVoice,//录音按钮
            buttonSendMessage;          //发送消息按钮
    private EmoticonsEditText inputTextView;    //文本输入框
    private FrameLayout sendLayoutView;         //发送消息&更多布局父控件
    private FuncLayout funcLayout;              //更多功能布局

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;

    protected boolean mDispatchKeyEventPreImeLock = false;

    public XhsEmoticonsKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflateKeyboardBar();
        initView();
        initFuncView();
    }

    /**
     * 加载表情键盘布局
     */
    protected void inflateKeyboardBar() {
        mInflater.inflate(R.layout.view_chatinput, this);
    }

    /**
     * 加载功能菜单布局
     * @return
     */
    protected View inflateFunc() {
        return mInflater.inflate(R.layout.layout_chatinput_emoji, null);
    }

    /**
     * 初始化View
     */
    protected void initView() {
        buttonVoiceOrText = findViewById(R.id.btn_voice_or_text);
        buttonVoice = findViewById(R.id.btn_voice);
        inputTextView = findViewById(R.id.et_chat);
        buttonFaceOrText = findViewById(R.id.btn_face_or_text);
        sendLayoutView = findViewById(R.id.sendLayout);
        buttonMoreFunctionInText = findViewById(R.id.buttonMoreFunctionInText);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);
        buttonVoiceOrText.setOnClickListener(this);
        buttonFaceOrText.setOnClickListener(this);
        buttonMoreFunctionInText.setOnClickListener(this);

        funcLayout = findViewById(R.id.funcLayout);
    }

    /**
     * 初始化功能菜单View
     */
    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
        funcLayout.addFuncView(FUNC_TYPE_APPPS, new ActionsPanel(mContext));
    }

    protected void initEmoticonFuncView() {
        View keyboardView = inflateFunc();
        funcLayout.addFuncView(FUNC_TYPE_EMOTION, keyboardView);
        mEmoticonsFuncView = findViewById(R.id.view_epv);
        mEmoticonsIndicatorView = findViewById(R.id.view_eiv);
        mEmoticonsToolBarView = findViewById(R.id.view_etv);
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        funcLayout.setOnFuncChangeListener(this);
    }

    protected void initEditView() {
        inputTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!inputTextView.isFocused()) {
                    inputTextView.setFocusable(true);
                    inputTextView.setFocusableInTouchMode(true);
                }
                return false;
            }
        });

        inputTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    buttonSendMessage.setVisibility(VISIBLE);
                    buttonMoreFunctionInText.setVisibility(GONE);
                } else {
                    buttonMoreFunctionInText.setVisibility(VISIBLE);
                    buttonSendMessage.setVisibility(GONE);
                }
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(View view) {
        funcLayout.addFuncView(FUNC_TYPE_APPPS, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(this);
        funcLayout.hideAllFuncView();
    }

    protected void showVoice() {
        inputTextView.setVisibility(GONE);
        buttonVoice.setVisibility(VISIBLE);
        reset();
    }

    protected void checkVoice() {
        if (buttonVoice.isShown()) {
            buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
        } else {
            buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
        }
    }

    protected void showText() {
        inputTextView.setVisibility(VISIBLE);
        buttonVoice.setVisibility(GONE);
    }

    protected void toggleFuncView(int key) {
        showText();
        funcLayout.toggleFuncView(key, isSoftKeyboardPop(), inputTextView);
    }

    @Override
    public void onFuncChange(int key) {
        checkVoice();
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) funcLayout.getLayoutParams();
        params.height = height;
        funcLayout.setLayoutParams(params);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        funcLayout.updateHeight(height);
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        funcLayout.setVisibility(true);
        onFuncChange(funcLayout.DEF_KEY);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (funcLayout.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(funcLayout.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        funcLayout.addOnKeyBoardListener(l);
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_voice_or_text) {
            if (inputTextView.isShown()) {
                buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
                showVoice();
            } else {
                showText();
                buttonVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
                EmoticonsKeyboardUtils.openSoftKeyboard(inputTextView);
            }
        } else if (i == R.id.btn_face_or_text) {
            buttonFaceOrText.setImageResource(isSoftKeyboardPop() ? R.drawable.btn_voice_or_text_keyboard :
                    R.drawable.icon_face_normal);
            toggleFuncView(FUNC_TYPE_EMOTION);
        } else if (i == R.id.buttonMoreFunctionInText) {
            toggleFuncView(FUNC_TYPE_APPPS);
        }
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override
    public void onBackKeyClick() {
        if (funcLayout.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (funcLayout.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return false;
        }
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return;
        }
        super.requestChildFocus(child, focused);
    }

    public boolean dispatchKeyEventInFullScreen(KeyEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext()) && funcLayout.isShown()) {
                    reset();
                    return true;
                }
            default:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    boolean isFocused;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        isFocused = inputTextView.getShowSoftInputOnFocus();
                    } else {
                        isFocused = inputTextView.isFocused();
                    }
                    if (isFocused) {
                        inputTextView.onKeyDown(event.getKeyCode(), event);
                    }
                }
                return false;
        }
    }

    public EmoticonsEditText getEtChat() {
        return inputTextView;
    }

    public Button getBtnVoice() {
        return buttonVoice;
    }

    public Button getBtnSend() {
        return buttonSendMessage;
    }

    public EmoticonsFuncView getEmoticonsFuncView() {
        return mEmoticonsFuncView;
    }

    public EmoticonsIndicatorView getEmoticonsIndicatorView() {
        return mEmoticonsIndicatorView;
    }

    public EmoticonsToolBarView getEmoticonsToolBarView() {
        return mEmoticonsToolBarView;
    }
}
