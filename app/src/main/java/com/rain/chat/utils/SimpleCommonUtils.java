package com.rain.chat.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.blankj.utilcode.util.PathUtils;
import com.rain.inputpanel.R;
import com.rain.inputpanel.adapter.BigEmoticonsAndTitleAdapter;
import com.rain.inputpanel.adapter.EmoticonsAdapter;
import com.rain.inputpanel.adapter.PageSetAdapter;
import com.rain.inputpanel.data.EmoticonEntity;
import com.rain.inputpanel.data.EmoticonPageEntity;
import com.rain.inputpanel.data.EmoticonPageSetEntity;
import com.rain.inputpanel.emoji.Constants;
import com.rain.inputpanel.emoji.DefEmoticons;
import com.rain.inputpanel.emoji.EmojiBean;
import com.rain.inputpanel.emoji.EmojiFilter;
import com.rain.inputpanel.imageloader.ImageBase;
import com.rain.inputpanel.imageloader.ImageLoader;
import com.rain.inputpanel.interfaces.EmoticonClickListener;
import com.rain.inputpanel.interfaces.EmoticonDisplayListener;
import com.rain.inputpanel.interfaces.PageViewInstantiateListener;
import com.rain.inputpanel.widget.EmoticonPageView;
import com.rain.inputpanel.widget.EmoticonsEditText;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;


public class SimpleCommonUtils {

    public static void initEmoticonsEditText(EmoticonsEditText etContent) {
        etContent.addEmoticonFilter(new EmojiFilter());
    }

    public static EmoticonClickListener getCommonEmoticonClickListener(final EditText editText) {
        return new EmoticonClickListener() {
            @Override
            public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
                if (isDelBtn) {
                    SimpleCommonUtils.delClick(editText);
                } else {
                    if (o == null) {
                        return;
                    }
                    if (actionType == Constants.EMOTICON_CLICK_TEXT) {
                        String content = null;
                        if (o instanceof EmojiBean) {
                            content = ((EmojiBean) o).emoji;
                        } else if (o instanceof EmoticonEntity) {
                            content = ((EmoticonEntity) o).getContent();
                        }

                        if (TextUtils.isEmpty(content)) {
                            return;
                        }
                        int index = editText.getSelectionStart();
                        Editable editable = editText.getText();
                        editable.insert(index, content);
                    }
                }
            }
        };
    }

    public static PageSetAdapter sCommonPageSetAdapter;

    public static PageSetAdapter getCommonAdapter(Context context, EmoticonClickListener emoticonClickListener) {

        if (sCommonPageSetAdapter != null) {
            return sCommonPageSetAdapter;
        }

        PageSetAdapter pageSetAdapter = new PageSetAdapter();

        addEmojiPageSetEntity(pageSetAdapter, context, emoticonClickListener);
        addMrZhangPageSetEntity(pageSetAdapter, context, emoticonClickListener);

        return pageSetAdapter;
    }

    /**
     * 插入emoji表情集
     *
     * @param pageSetAdapter
     * @param context
     * @param emoticonClickListener
     */
    public static void addEmojiPageSetEntity(PageSetAdapter pageSetAdapter, Context context, final EmoticonClickListener emoticonClickListener) {
        ArrayList<EmojiBean> emojiArray = new ArrayList<>();
        Collections.addAll(emojiArray, DefEmoticons.sEmojiArray);
        EmoticonPageSetEntity emojiPageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(4)
                .setRow(8)
                .setEmoticonList(emojiArray)
                .setIPageViewInstantiateItem(getDefaultEmoticonPageViewInstantiateItem(new EmoticonDisplayListener<Object>() {
                    @Override
                    public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {
                        final EmojiBean emojiBean = (EmojiBean) object;
                        if (emojiBean == null && !isDelBtn) {
                            return;
                        }

                        viewHolder.ly_root.setBackgroundResource(R.drawable.bg_emoticon);

                        if (isDelBtn) {
                            viewHolder.iv_emoticon.setImageResource(R.drawable.icon_del);
                        } else {
                            viewHolder.iv_emoticon.setImageResource(emojiBean.icon);
                        }

                        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (emoticonClickListener != null) {
                                    emoticonClickListener.onEmoticonClick(emojiBean, Constants.EMOTICON_CLICK_TEXT, isDelBtn);
                                }
                            }
                        });
                    }
                }))
                .setShowDelBtn(EmoticonPageEntity.DelBtnStatus.LAST)
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("icon_emoji"))
                .build();
        pageSetAdapter.add(emojiPageSetEntity);
    }


    public static void addMrZhangPageSetEntity(PageSetAdapter pageSetAdapter, Context context,
                                               EmoticonClickListener emoticonClickListener) {
        String filePath = PathUtils.getInternalAppFilesPath() + "/emoticon/mr_zhang";
        EmoticonPageSetEntity<EmoticonEntity> emoticonPageSetEntity = ParseDataUtils.parseDataFromFile(
                context, filePath, "mr_zhang.zip", "mr_zhang.xml");
        if (emoticonPageSetEntity == null) {
            return;
        }
        EmoticonPageSetEntity pageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(emoticonPageSetEntity.getLine())
                .setRow(emoticonPageSetEntity.getRow())
                .setEmoticonList(emoticonPageSetEntity.getEmoticonList())
                .setIPageViewInstantiateItem(getEmoticonPageViewInstantiateItem(BigEmoticonsAndTitleAdapter.class, emoticonClickListener))
                .setIconUri(ImageBase.Scheme.FILE.toUri(filePath + "/" + emoticonPageSetEntity.getIconUri()))
                .build();
        pageSetAdapter.add(pageSetEntity);
    }


    @SuppressWarnings("unchecked")
    public static Object newInstance(Class _Class, Object... args) throws Exception {
        return newInstance(_Class, 0, args);
    }

    @SuppressWarnings("unchecked")
    public static Object newInstance(Class _Class, int constructorIndex, Object... args) throws Exception {
        Constructor cons = _Class.getConstructors()[constructorIndex];
        return cons.newInstance(args);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getDefaultEmoticonPageViewInstantiateItem(final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return getEmoticonPageViewInstantiateItem(EmoticonsAdapter.class, null, emoticonDisplayListener);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(final Class _class, EmoticonClickListener onEmoticonClickListener) {
        return getEmoticonPageViewInstantiateItem(_class, onEmoticonClickListener, null);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(final Class _class, final EmoticonClickListener onEmoticonClickListener, final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return new PageViewInstantiateListener<EmoticonPageEntity>() {
            @Override
            public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                if (pageEntity.getRootView() == null) {
                    EmoticonPageView pageView = new EmoticonPageView(container.getContext());
                    pageView.setNumColumns(pageEntity.getRow());
                    pageEntity.setRootView(pageView);
                    try {
                        EmoticonsAdapter adapter = (EmoticonsAdapter) newInstance(_class, container.getContext(), pageEntity, onEmoticonClickListener);
                        if (emoticonDisplayListener != null) {
                            adapter.setOnDisPlayListener(emoticonDisplayListener);
                        }
                        pageView.getEmoticonsGridView().setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return pageEntity.getRootView();
            }
        };
    }

    public static EmoticonDisplayListener<Object> getCommonEmoticonDisplayListener(final EmoticonClickListener onEmoticonClickListener, final int type) {
        return new EmoticonDisplayListener<Object>() {
            @Override
            public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {

                final EmoticonEntity emoticonEntity = (EmoticonEntity) object;
                if (emoticonEntity == null && !isDelBtn) {
                    return;
                }
                viewHolder.ly_root.setBackgroundResource(R.drawable.bg_emoticon);

                if (isDelBtn) {
                    viewHolder.iv_emoticon.setImageResource(R.drawable.icon_del);
                } else {
                    try {
                        ImageLoader.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEmoticonClickListener != null) {
                            onEmoticonClickListener.onEmoticonClick(emoticonEntity, type, isDelBtn);
                        }
                    }
                });
            }
        };
    }

    public static void delClick(EditText editText) {
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
    }
}
