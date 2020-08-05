package com.rain.inputpanel.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @CreateDate : 2020/7/7 18:47
 * @Version : 1.0
 * @Descroption : 功能面板控制器
 */
public class ActionController {

    private static ActionController INSTANCE;

    private List<BaseAction> actions = new ArrayList<>();

    public static ActionController getInstance() {
        if (INSTANCE == null) {
            synchronized (ActionController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ActionController();
                }
            }
        }
        return INSTANCE;
    }

    public ActionController() {
    }

    public List<BaseAction> getActions() {
        return actions;
    }

    public void addAction(BaseAction action) {
        actions.add(action);
    }

    public void addActions(List<BaseAction> data){
        actions.addAll(data);
    }
}
