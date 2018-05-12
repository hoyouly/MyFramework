package top.hoyouly.framework;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;


/**
 * 占坑专用Activity
 */
public class AnrActivity extends Activity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

//        SystemClock.sleep(30*1000);
//        button = findViewById(R.id.btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SystemClock.sleep(30000);
//            }
//        });


        new Thread(){
            @Override
            public void run() {
                super.run();
                testANR();
            }
        }.start();

        SystemClock.sleep(10);
        initView();

        AsyncTask
    }

    private synchronized void initView() {
        button = findViewById(R.id.btn);
    }

    private synchronized void testANR() {
        SystemClock.sleep(30*1000);
    }
}
