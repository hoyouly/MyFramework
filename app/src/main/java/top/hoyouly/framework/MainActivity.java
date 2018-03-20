package top.hoyouly.framework;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;

import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.entity.Entity;
import top.hoyouly.framework.net.Request;
import top.hoyouly.framework.net.callback.JsonCallback;
import top.hoyouly.framework.net.callback.StringCallback;
import top.hoyouly.framework.test.UrlHelper;


public class MainActivity extends BaseActivity implements View.OnClickListener {

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


    private void requestJson(){
        Request request=new Request(UrlHelper.test_String_url, Request.RequestMethod.GET);
        request.setCallback(new JsonCallback() {
            @Override
            public void onFailure(Exception result) {

            }

            @Override
            public void onSuccess(Object result) {
               ArrayList<Entity> entities= (ArrayList<Entity>) result;
                for (Entity entity:entities){

                }
            }
        }.setReturnType(new TypeToken<ArrayList<Entity>>(){}.getType()));

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
