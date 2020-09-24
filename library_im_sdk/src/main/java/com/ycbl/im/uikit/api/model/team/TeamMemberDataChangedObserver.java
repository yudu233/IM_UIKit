package com.ycbl.im.uikit.api.model.team;

import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/9/23 17:59
 * @Description : UIKit 与 app 群成员数据变更监听接口
 */

public interface TeamMemberDataChangedObserver<K> {

    /**
     * 成员更新
     *
     * @param members 成员列表
     */
    void onUpdateTeamMember(List<K> members);

    /**
     * 成员删除
     *
     * @param members 成员列表
     */
    void onRemoveTeamMember(List<K> members);
}
