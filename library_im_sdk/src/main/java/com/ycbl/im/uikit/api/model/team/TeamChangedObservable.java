package com.ycbl.im.uikit.api.model.team;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Rain
 * @Version : 1.0
 * @CreateDate :  2020/9/23 18:00
 * @Description : 群、群成员变更通知接口
 */

public class TeamChangedObservable<T, K> {

    private List<TeamDataChangedObserver<T>> teamObservers = new ArrayList<>();
    private List<TeamMemberDataChangedObserver<K>> memberObservers = new ArrayList<>();

    private Handler uiHandler;

    public TeamChangedObservable(Context context) {
        uiHandler = new Handler(context.getMainLooper());
    }

    public synchronized void registerTeamDataChangedObserver(TeamDataChangedObserver<T> o, boolean register) {
        if (register) {
            if (teamObservers.contains(o)) {
                return;
            }
            teamObservers.add(o);
        } else {
            teamObservers.remove(o);
        }
    }

    public synchronized void registerTeamMemberDataChangedObserver(TeamMemberDataChangedObserver<K> o, boolean register) {
        if (register) {
            if (memberObservers.contains(o)) {
                return;
            }
            memberObservers.add(o);
        } else {
            memberObservers.remove(o);
        }
    }

    public synchronized void notifyTeamDataUpdate(final List<T> teams) {
        uiHandler.post(() -> {
            for (TeamDataChangedObserver<T> o : teamObservers) {
                o.onUpdateTeams(teams);
            }
        });
    }

    public synchronized void notifyTeamDataRemove(final T team) {
        uiHandler.post(() -> {
            for (TeamDataChangedObserver<T> o : teamObservers) {
                o.onRemoveTeam(team);
            }
        });

    }

    public synchronized void notifyTeamMemberDataUpdate(final List<K> members) {
        uiHandler.post(() -> {
            for (TeamMemberDataChangedObserver<K> o : memberObservers) {
                o.onUpdateTeamMember(members);
            }
        });
    }

    public synchronized void notifyTeamMemberRemove(final List<K> members) {
        uiHandler.post(() -> {
            for (TeamMemberDataChangedObserver<K> o : memberObservers) {
                o.onRemoveTeamMember(members);
            }
        });
    }
}
