package top.hoyouly.framework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.entity.MovieObject;
import top.hoyouly.framework.entity.Translation1;
import top.hoyouly.framework.inter.MovieService;


public class MainActivity extends BaseActivity implements View.OnClickListener {

	private Button btn;
	private TextView content;
	public static final String BASE_URL = "https://api.douban.com/v2/movie/";
	private static final String tag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.btn);
		content = (TextView) findViewById(R.id.content);
		btn.setOnClickListener(this);

//		FormBody.Builder builder=new FormBody.Builder();
//		builder.add("key","value");
	}

	private void getRequest() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)//
				//表示需要用什么转换器来解析返回值
				.addConverterFactory(GsonConverterFactory.create())//GsonConverterFactory 是默认提供的Gson 转换器，Retrofit 也支持其他的一些转换器
				.build();

		//获取接口实例
		MovieService movieService = retrofit.create(MovieService.class);
		//调用方法得到一个call  Call其实在Retrofit中就是行使网络请求并处理返回值的类，
		// 调用的时候会把需要拼接的参数传递进去，此处最后得到的url完整地址为https://api.douban.com/v2/movie/top250

		Call<MovieObject> call = movieService.getTop250(0, 20);
		//进行网络异步请求
		call.enqueue(new Callback<MovieObject>() {
			@Override
			public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
				MovieObject body = response.body();
				Log.e("hoyouly", "onResponse: " + body.title + "  currentThread:  " + Thread.currentThread());
				Log.e("hoyouly", "onResponse: " + body.subjects.toString());
			}

			@Override
			public void onFailure(Call<MovieObject> call, Throwable t) {
				t.printStackTrace();
				Log.e("hoyouly", getClass().getSimpleName() + " -> onFailure: " + t.getMessage());
			}
		});
	}

	private void postRequest() {
		Retrofit retrofit=new Retrofit.Builder()//
				.baseUrl("http://fanyi.youdao.com/")//
				.addConverterFactory(GsonConverterFactory.create())//
				.build();

		MovieService service=retrofit.create(MovieService.class);
		Call<Translation1> call=service.getCall("I love you");
		call.enqueue(new Callback<Translation1>() {
			@Override
			public void onResponse(Call<Translation1> call, Response<Translation1> response) {
				Log.d("hoyouly", getClass().getSimpleName() + " -> onResponse:currentThread  "+Thread.currentThread());
				Log.d("hoyouly", getClass().getSimpleName() + " -> onResponse: "+response.body().getTranslateResult().get(0).get(0).getTgt());
			}

			@Override
			public void onFailure(Call<Translation1> call, Throwable t) {

			}
		});
	}
	private void postRequestByRxjava() {
		Retrofit retrofit=new Retrofit.Builder()//
				.baseUrl("http://fanyi.youdao.com/")//
				.addConverterFactory(GsonConverterFactory.create())//
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
				.build();

		MovieService service=retrofit.create(MovieService.class);

		service.getCallByRxJava("hello world").subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Translation1>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(Translation1 translation1) {
						Log.d("hoyouly", getClass().getSimpleName() + " -> onNext:currentThread  "+Thread.currentThread());

						Log.d("hoyouly", getClass().getSimpleName() + " -> onNext: "+translation1.getTranslateResult().get(0).get(0).getTgt());
					}
				});
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn:
//				testRxJavaRetrofit();
				postRequest();
				postRequestByRxjava();
				break;
		}
	}


	public void testRxJavaRetrofit(){
		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)//
				//表示需要用什么转换器来解析返回值
				.addConverterFactory(GsonConverterFactory.create())//GsonConverterFactory 是默认提供的Gson 转换器，Retrofit 也支持其他的一些转换器
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
				.build();

		//获取接口实例
		MovieService movieService = retrofit.create(MovieService.class);
		//调用方法得到一个call  Call其实在Retrofit中就是行使网络请求并处理返回值的类，
		// 调用的时候会把需要拼接的参数传递进去，此处最后得到的url完整地址为https://api.douban.com/v2/movie/top250

		Subscription subscriber=movieService.getTop250WithRxJava(0,20)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<MovieObject>() {
			@Override
			public void onCompleted() {
				Log.d("hoyouly", getClass().getSimpleName() + " -> onCompleted: ");
			}

			@Override
			public void onError(Throwable e) {
				Log.e("hoyouly", getClass().getSimpleName() + " -> onFailure: " + e.getMessage());
			}

			@Override
			public void onNext(MovieObject movieObject) {
				Log.e("hoyouly", "onResponse: " + movieObject.title + "  currentThread:  " + Thread.currentThread());
				Log.e("hoyouly", "onResponse: " + movieObject.subjects.toString());
			}
		});

	}

}
