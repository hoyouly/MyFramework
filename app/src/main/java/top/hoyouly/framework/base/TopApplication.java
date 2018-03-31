package top.hoyouly.framework.base;

import android.app.Application;

import top.hoyouly.framework.utils.Utils;

/**
 * Created by hoyouly on 18/3/31.
 */

public class TopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
