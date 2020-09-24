package com.ycbl.im.uikit.api.model.team;

import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/9/23 18:00
 * @Description : UIKit 与 app 群数据变更监听接口
 */

public interface TeamDataChangedObserver<T> {

    /**
     * 群更新
     *
     * @param teams 群列表
     */
    void onUpdateTeams(List<T> teams);

    /**
     * 群删除
     *
     * @param team) 群
     */
    void onRemoveTeam(T team);
}
