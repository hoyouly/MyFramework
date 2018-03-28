package top.hoyouly.framework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.config.ApiConfig;


public class OkHttpActivity extends BaseActivity implements View.OnClickListener {

    private Button btn;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        content = (TextView) findViewById(R.id.content);
        btn.setOnClickListener(this);
        Log.e("hoyouly", "onCreate: current thread :"+Thread.currentThread());
//        getDataSycn();
        postDataSycn();
        getDataAsync();
        postDataAsync();
    }

    private void postDataAsync() {
        OkHttpClient okHttpClient=new OkHttpClient();
//        FormBody.Builder formBody=new FormBody.Builder();//创建表单请求体
//        formBody.add("username","张三");//设置键值对传递参数
//        Request request=new Request.Builder()//创建Request对象
//                .url(BAIDU_URL)//设置URL
//                .post(formBody.build())//传递请求体
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });



        MediaType mediaType= MediaType.parse("application/json; charset=utf-8");//数据格式为json
        String jsonStr = "{\"username\":\"lisi\",\"nickname\":\"李四\"}";//json数据.
        RequestBody requestBody=RequestBody.create(mediaType,jsonStr);
        Request request=new Request.Builder()//创建Request对象
                .url(ApiConfig.DOUBAN_MOVIE_URL)//设置URL
                .post(requestBody)//传递请求体
                .build();

        okHttpClient.newCall(request).cancel();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream=response.body().byteStream();//从服务器得到输入流对象
                long sum=0;
                File dir=new File("文件夹路径");
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File file=new File(dir,"文件名");//根据目录和文件名得到file对象
                FileOutputStream fos=new FileOutputStream(file);
                byte[] bytes=new byte[1024*8];
                int len=0;
                while ((len=inputStream.read(bytes))!=-1){
                    fos.write(bytes);
                }
                fos.flush();
//                return file;
            }
        });

        int groudId=0;
        MediaType mediaType1=MediaType.parse("File/*");//数据类型为文件类型
        File file=new File("文件路径");
        RequestBody requestBody1=RequestBody.create(mediaType1,file);

        MultipartBody body=new MultipartBody.Builder()//
                .setType(MultipartBody.FORM)//
                .addFormDataPart("groupId",""+groudId)////添加键值对参数
                .addFormDataPart("title","title")//
                .addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("file/*"),file))//添加文件
                .build();
//        final Request request = new Request.Builder()
//                //.cacheControl(new CacheControl().noCache().b)
//                .url(BAIDU_URL)
//                .post(body)
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {。。。});
   }

    private void postDataSycn() {

    }

    RequestBody uploadBody=new RequestBody() {
        @Override
        public MediaType contentType() {
            return null;
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            FileInputStream fio=new FileInputStream(new File("文件名字"));
            byte[] buffer=new byte[1024*8];
            if(fio.read(buffer)!=-1){
                sink.write(buffer);
            }

        }
    };

    /**
     * 使用OkHttp 的同步get请求数据
     */
    private void getDataSycn() {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()//
                            .url(ApiConfig.DOUBAN_MOVIE_URL)//请求接口，如果需要传递参数，拼接到后面
                            .build();//创建Request对象
                    Response response = null;
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {//请求成功
                        Log.e("hoyouly", "currentThread: " + Thread.currentThread() + " \n code: " + response.code() + " \n body: " + response.body().string() + " \n" + response.body().toString() + " \n " + "message: " + response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getDataAsync() {
        OkHttpClient okHttpClient = new OkHttpClient();//创建OkHttpClient对象
        Request request = new Request.Builder()//
                .url("http://www.baidu.com")//请求接口，如果需要传递参数，拼接到后面
                .build();//创建Request对象
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//请求成功
                    Log.e("hoyouly", "onResponse currentThread: " + Thread.currentThread() //
                            + " \n code: " + response.code() //
                            + " \n body: " + response.body().string()//
                            + " \n message: " + response.message());
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
