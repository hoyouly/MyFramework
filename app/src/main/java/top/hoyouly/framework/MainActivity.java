package top.hoyouly.framework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
import top.hoyouly.framework.bean.Book;
import top.hoyouly.framework.bean.MovieObject;
import top.hoyouly.framework.bean.Translation;
import top.hoyouly.framework.inter.MovieService;
import top.hoyouly.framework.presenter.BookPresenter;
import top.hoyouly.framework.view.BookView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

	private Button btn;
	private TextView content;
	public static final String BASE_URL = "https://api.douban.com/v2/movie/";
	private static final String tag = "MainActivity";
	private BookPresenter bookPresenter;

	private BookView bookView=new BookView() {
        @Override
        public void onSuccess(Book book) {
            content.setText(book.toString());
        }

        @Override
        public void onError() {
            Log.d("hoyouly", "onError: ");
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.btn);
		content = (TextView) findViewById(R.id.content);
		btn.setOnClickListener(this);

		bookPresenter=new BookPresenter();
		bookPresenter.onCreat();
		bookPresenter.attchView(bookView);

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

	public Interceptor getCacheInterceptor(){
	    Interceptor cacheInterceptor= new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
//                if (!NetworkUtils.isConnected()) {
//                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
//                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//                }
                okhttp3.Response response = chain.proceed(request);
//                if (NetworkUtils.isConnected()) {//有网络情况下，根据请求接口的设置，配置缓存。
//                    //这样在下次请求时，根据缓存决定是否真正发出请求。
//                    String cacheControl = request.cacheControl().toString();
//                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
//                    //将其超时时间这是为0即可:String cacheControl="Cache-Control:public,max-age=0"
//                    int maxAge = 60 * 60; // read from cache for 1 minute
//                    return response.newBuilder()
////                            .header("Cache-Control", cacheControl)
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .removeHeader("Pragma")
//                            .build();
//                } else {
//                    //无网络
//                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                    return response.newBuilder()
////                            .header("Cache-Control", "public,only-if-cached,max-stale=360000")
//                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
//                            .removeHeader("Pragma")
//                            .build();
//                }
                return response;
            }
        };
	    return  cacheInterceptor;
    }


	private void postRequest() {
//		HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
//		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



		LogInterceptor logInterceptor=new LogInterceptor();
        File httpCacheDirectory=new File(getCacheDir(),"OkHttpCache");
        Cache cache=new Cache(httpCacheDirectory,10*1024*1024);

		OkHttpClient okHttpClient=new OkHttpClient.Builder()
				.addNetworkInterceptor(getCacheInterceptor())//添加拦截
                .addInterceptor(getCacheInterceptor())
                .cache(cache)////设置缓存
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return null;
                    }
                })
				.build();

		Retrofit retrofit=new Retrofit.Builder()//
				.baseUrl("http://fanyi.youdao.com/")//
				.client(okHttpClient)//
				.addConverterFactory(GsonConverterFactory.create())//
				.build();

		MovieService service=retrofit.create(MovieService.class);
		Call<Translation> call=service.getCall("I love you");
		call.enqueue(new Callback<Translation>() {
			@Override
			public void onResponse(Call<Translation> call, Response<Translation> response) {
				Log.d("hoyouly", getClass().getSimpleName() + " -> onResponse:currentThread  "+Thread.currentThread());
				Log.d("hoyouly", getClass().getSimpleName() + " -> onResponse: "+response.body().getTranslateResult().get(0).get(0).getTgt());
			}

			@Override
			public void onFailure(Call<Translation> call, Throwable t) {
                Log.e("hoyouly", getClass().getSimpleName() + " -> onFailure: " + t.getMessage());
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
				.subscribe(new Observer<Translation>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(Translation translation) {
						Log.d("hoyouly", getClass().getSimpleName() + " -> onNext:currentThread  "+Thread.currentThread());

						Log.d("hoyouly", getClass().getSimpleName() + " -> onNext: "+ translation.getTranslateResult().get(0).get(0).getTgt());
					}
				});
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn:
//				testRxJavaRetrofit();
//				postRequest();
//				postRequestByRxjava();
                bookPresenter.getSearchBook("金瓶梅", null, 0, 1);
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

		Subscription subscriber=movieService.getTop250WithRxJava1(0,20)
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
				android.util.Log.e("hoyouly", "onResponse: " + movieObject.subjects.toString());
			}
		});

	}

	private class LogInterceptor implements Interceptor{
		@Override
		public okhttp3.Response intercept(Chain chain) throws IOException {
			Request request = chain.request();
			long t1 = System.nanoTime();
			Log.d("hoyouly", "LogInterceptor start" + String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

			okhttp3.Response response = chain.proceed(request);
			long t2 = System.nanoTime();

			Log.d("hoyouly", "LogInterceptor end " + String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
			return response;
		}
	}

    @Override
    protected void onStop() {
        super.onStop();
        bookPresenter.onStop();
    }
}
