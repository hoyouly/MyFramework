package top.hoyouly.framework;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.entity.Entity;
import top.hoyouly.framework.net.Request;
import top.hoyouly.framework.net.callback.JsonCallback;
import top.hoyouly.framework.net.callback.StringCallback;
import top.hoyouly.framework.test.UrlHelper;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btn;
    private TextView content;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.btn);
        content= (TextView) findViewById(R.id.content);
        btn.setOnClickListener(this);

//        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL)//
//                //表示需要用什么转换器来解析返回值
//                .addConverterFactory(GsonConverterFactory.create())//GsonConverterFactory 是默认提供的Gson 转换器，Retrofit 也支持其他的一些转换器
//                .build();
//
//        //获取接口实例
//        MovieService movieService=retrofit.create(MovieService.class);
//        //调用方法得到一个call  Call其实在Retrofit中就是行使网络请求并处理返回值的类，
//        // 调用的时候会把需要拼接的参数传递进去，此处最后得到的url完整地址为https://api.douban.com/v2/movie/top250
//
//        Call<MovieObject> call=movieService.getTop250(0,20);
//        //进行网络异步请求
//        call.enqueue(new Callback<MovieObject>() {
//            @Override
//            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
//                Log.d("hoyouly", "onResponse: "+response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<MovieObject> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//        //同步请求  这里需要注意的是网络请求一定要在子线程中完成，不能直接在UI线程执行，不然会crash
//        try {
//            Response<MovieObject> response=call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(tag, "Item: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d(tag, "Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(tag, "Error!");
//            }
//        };
//
//        //Subscriber 是Observe的抽象类，对Observe进行了扩展，但是基本使用方法一直
//        //实质上，在 RxJava 的 subscribe 过程中，Observer 也总是会先被转换成一个 Subscriber 再使用
//        Subscriber<String> subscriber=new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(tag, "Item: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d(tag, "Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(tag, "Error!");
//            }
//        };
//
//        Observable observable= Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> sub) {
//                sub.onNext("Hello");
//                sub.onNext("Hi");
//                sub.onNext("Aloha");
//                sub.onCompleted();
//            }
//        });
//
//        //可以看到，这里传入了一个 OnSubscribe 对象作为参数。OnSubscribe 会被存储在返回的 Observable 对象中，
//        // 它的作用相当于一个计划表，当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，
//        // 事件序列就会依照设定依次触发（对于上面的代码，就是观察者Subscriber 将会被调用三次 onNext() 和一次 onCompleted()）。
//        // 这样，由被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式。

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                   requestString();
                String[] array=new String[]{"Hello","Hi","RxJava"};
                //        observable=Observable.from(array);

                Observable.from(array).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("hoyouly", getClass().getSimpleName() + " -> call: "+s);
                    }
                });

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
