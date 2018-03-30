package top.hoyouly.framework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.bean.Movie;
import top.hoyouly.framework.bean.MovieObject;
import top.hoyouly.framework.config.ApiConfig;
import top.hoyouly.framework.inter.MovieService;
import top.hoyouly.framework.inter.SubscriberOnNextListener;
import top.hoyouly.framework.loder.MovieLoader;
import top.hoyouly.framework.subscriber.ProgressDialogSubscriber;


public class RetrofitActivity extends BaseActivity implements View.OnClickListener {

    private Button btn;
    private TextView content;
    private MovieLoader movieLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= (Button) findViewById(R.id.btn);
        btn.setText("Retrofit");
        content= (TextView) findViewById(R.id.content);
        btn.setOnClickListener(this);
        movieLoader=new MovieLoader();
    }
    private void getMovieList(){
        SubscriberOnNextListener<List<Movie>> onNextListener=new SubscriberOnNextListener<List<Movie>>() {
            @Override
            public void onNext(List<Movie> movies) {
//                Log.d("hoyouly", getClass().getSimpleName() + " -> onNext: "+movies.toString());
            }
        };
        movieLoader.getMovie(0,10)//
                .subscribe(new ProgressDialogSubscriber<List<Movie>>(onNextListener,RetrofitActivity.this));
    }

    private void getRequest() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(ApiConfig.DOUBAN_MOVIE_URL)//
                //表示需要用什么转换器来解析返回值
                .addConverterFactory(GsonConverterFactory.create())//GsonConverterFactory 是默认提供的Gson 转换器，Retrofit 也支持其他的一些转换器
                .build();

        //获取接口实例
        MovieService movieService=retrofit.create(MovieService.class);
        //调用方法得到一个call  Call其实在Retrofit中就是行使网络请求并处理返回值的类，
        // 调用的时候会把需要拼接的参数传递进去，此处最后得到的url完整地址为https://api.douban.com/v2/movie/top250

        Call<MovieObject> call=movieService.getTop250(0,20);
        //进行网络异步请求
        call.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                Log.e("hoyouly", "onResponse: "+response.body().subjects+"  \n currentThread: "+Thread.currentThread());
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                t.printStackTrace();
                Log.e("hoyouly", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
//                getRequest();
                getMovieList();
                break;

        }
    }
}
