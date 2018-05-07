package top.hoyouly.framework;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.mv.UserActivity;


/**
 * 占坑专用Activity
 */
public class TmpActivity extends BaseActivity {
    private static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TmpActivity.this, UserActivity.class));

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            //欺骗AMN，将要启动的Activity替换成我们占坑用的Activity
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
            Field mDefaultField = activityManagerNativeClass.getDeclaredField("mDefault");
            Object gDefault = mDefaultField.get(null);

            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceFiled = singleton.getField("mInstance");
            mInstanceFiled.setAccessible(true);//启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
            //从AMN的gDefault对象中原始的IActivityManager对象
            final Object rawIActivityManager = mInstanceFiled.get(gDefault);

            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iActivityManagerInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("startActivity".equals(method.getName())) {
                        Intent raw;
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                index = i;
                                break;
                            }
                        }

                        raw = (Intent) args[index];
                        //替身用的包名
                        String packageName = getPackageName();

                        ComponentName componentName = new ComponentName(packageName, TmpActivity.class.getName());

                        Intent newIntent = new Intent();
                        newIntent.setComponent(componentName);
                        newIntent.putExtra(EXTRA_TARGET_INTENT, raw);

                        args[index] = newIntent;
                        return method.invoke(rawIActivityManager, args);
                    }
                    return method.invoke(rawIActivityManager, args);
                }
            });
            mDefaultField.set(gDefault, proxy);


            /**
             * 欺骗ActivityThread
             */

            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentActivityThread = currentActivityThreadField.get(null);
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(currentActivityThread);

            Field mCallBackField = Handler.class.getDeclaredField("mCallBack");
            mCallBackField.setAccessible(true);
            mCallBackField.set(mH, new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == 100) {
                        Object obj = msg.obj;
                        try {
                            Field intent = obj.getClass().getDeclaredField("intent");
                            intent.setAccessible(true);

                            Intent raw = (Intent) intent.get(obj);
                            Intent target = raw.getParcelableExtra(EXTRA_TARGET_INTENT);
                            raw.setComponent(target.getComponent());

                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
