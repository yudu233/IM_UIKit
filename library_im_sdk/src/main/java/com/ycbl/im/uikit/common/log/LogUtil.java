package com.ycbl.im.uikit.common.log;


import com.ycbl.im.uikit.common.log.sdk.LogBase;
import com.ycbl.im.uikit.common.log.sdk.wrapper.NimLog;

public class LogUtil extends NimLog {

    private static final String LOG_FILE_NAME_PREFIX = "IM";

    public static void init(String logDir, int level) {
        final LogBase.LogInterceptor interceptor = new LogBase.LogInterceptor() {
            @Override
            public boolean checkValidBeforeWrite() {
                return true;
            }
        };

        NimLog.initDateNLog(null, logDir, LOG_FILE_NAME_PREFIX, level, 0, 0, true, interceptor);
    }

    public static void ui(String msg) {
        getLog().i("ui", buildMessage(msg));
    }

    public static void res(String msg) {
        getLog().i("res", buildMessage(msg));
    }

    public static void audio(String msg) {
        getLog().i("AudioRecorder", buildMessage(msg));
    }
}
