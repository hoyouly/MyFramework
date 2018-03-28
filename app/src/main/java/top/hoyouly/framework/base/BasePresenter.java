package top.hoyouly.framework.base;

import android.content.Intent;

/**
 * Created by hoyouly on 18/3/28.
 */

public interface BasePresenter {
    void onCreat();

    void onStart();

    void onStop();

    void onPause();

    void attchView(BaseView view);

    void attchIncomingIntent(Intent intent);

}
