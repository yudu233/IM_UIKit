package com.rain.chat.session.action;

import androidx.annotation.NonNull;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.rain.chat.R;
import com.rain.chat.base.IM;
import com.rain.chat.session.module.CustomerBaseAction;
import com.rain.chat.utils.PermissionHelper;
import com.ycbl.im.uikit.NimUIKitImpl;
import com.ycbl.im.uikit.msg.models.MyMessage;

import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/7/7 18:40
 * @Version : 1.0
 * @Descroption : 位置消息功能按钮
 */
public class LocationAction extends CustomerBaseAction {

    public LocationAction() {
        super(R.drawable.action_location_selector, R.string.input_panel_location);
    }

    @Override
    public void onClick() {
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> granted) {
                        showMap();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                        if (!deniedForever.isEmpty()) {
                            PermissionHelper.showTipsDialog(getActivity(),
                                    getActivity().getString(R.string.text_positioning));
                        }
                    }
                }).request();
    }

    private void showMap() {
        if (NimUIKitImpl.getLocationProvider() != null) {
            NimUIKitImpl.getLocationProvider().requestLocation(getActivity(), (longitude, latitude, address) -> {
                MyMessage message = IM.getIMessageBuilder().createLocationMessage(
                        getAccount(), getSessionType(), latitude, longitude,
                        address);
                getContainer().proxy.sendMessage(message);
            });
        }
    }
}
