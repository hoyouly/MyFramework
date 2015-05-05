package com.example.hoyouly.myframework;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoyouly.myframework.net.Request;
import com.example.hoyouly.myframework.net.callback.StringCallback;
import com.example.hoyouly.myframework.test.UrlHelper;

import java.io.File;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btn;
    private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.btn);
        content= (TextView) findViewById(R.id.content);
        btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                   requestString();
                break;

        }
    }

    private void requestString() {
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.pathSeparator+System.currentTimeMillis()+".txt";
        Request request=new Request(UrlHelper.test_String_url, Request.RequestMethod.GET);
        request.setCallback(new StringCallback() {
            @Override
            public void onFailure(Exception result) {
            }

            @Override
            public void onSuccess(Object result) {
                content.setText((String)result);

            }
        }.setPath(path));
        request.execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
