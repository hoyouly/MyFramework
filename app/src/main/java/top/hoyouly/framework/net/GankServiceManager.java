package top.hoyouly.framework.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.hoyouly.framework.config.ApiConfig;

/**
 * Created by hoyouly on 18-3-27.
 */

public class GankServiceManager {
	private static final int DEFAULT_TIME_OUT = 5;// 超时时间5秒
	private static final int DEFAULT_READ_TIME_OUT = 10;
	private Retrofit retrofit;

	public GankServiceManager() {

		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
		builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

		HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()//
				.addHeaderParams("paltform", "Android")//
				.addHeaderParams("usertoke", "")//
				.addHeaderParams("userid", "")//
				.build();
		builder.addInterceptor(commonInterceptor);

		retrofit = new Retrofit.Builder()//
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
				.addConverterFactory(GsonConverterFactory.create())//
				.baseUrl(ApiConfig.GANK_DATA_BASE_URL)//
				.client(builder.build())//
				.build();

	}

	private static class SingletonHolder {
		private static final GankServiceManager INSTANCE = new GankServiceManager();
	}

	/**
	 * 获取RetorfitServiceManager
	 * @return
	 */
	public static GankServiceManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 获取对应的service
	 * @param service
	 * @param <T>
	 * @return
	 */
	public <T> T creat(Class<T> service){
		return retrofit.create(service);
	}



}
