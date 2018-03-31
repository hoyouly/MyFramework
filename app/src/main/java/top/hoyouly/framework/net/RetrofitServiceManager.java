package top.hoyouly.framework.net;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.hoyouly.framework.config.ApiConfig;
import top.hoyouly.framework.utils.NetworkUtils;
import top.hoyouly.framework.utils.Utils;

/**
 * Created by hoyouly on 18-3-27.
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;// 超时时间5秒
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit retrofit;

    private CookieJar cookieJar=new CookieJar() {//
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return null;
        }
    };

    public RetrofitServiceManager() {
		File httpCacheDirectory = new File(Utils.getContext().getCacheDir(), "OkHttpCache");
		Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()//
                .addHeaderParams("paltform", "Android")//
                .addHeaderParams("usertoke", "")//
                .addHeaderParams("userid", "")//
                .build();

        builder.addInterceptor(commonInterceptor)//
                .addNetworkInterceptor(getCacheInterceptor())//
//                .cookieJar(cookieJar)
                .cache(cache);

        retrofit = new Retrofit.Builder()//
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
                .addConverterFactory(GsonConverterFactory.create())//
                .baseUrl(ApiConfig.GANK_DATA_BASE_URL)//
                .client(builder.build())//
                .build();

    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * 获取RetorfitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T creat(Class<T> service) {
        return retrofit.create(service);
    }


    public Interceptor getCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                if (!NetworkUtils.isConnected()) {
                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                okhttp3.Response response = chain.proceed(request);
                if (NetworkUtils.isConnected()) {//有网络情况下，根据请求接口的设置，配置缓存。
                    //这样在下次请求时，根据缓存决定是否真正发出请求。
                    String cacheControl = request.cacheControl().toString();
                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
                    //将其超时时间这是为0即可:String cacheControl="Cache-Control:public,max-age=0"
                    int maxAge = 60 * 60; // read from cache for 1 minute
                    return response.newBuilder()
                            //header("Cache-Control", cacheControl)
                            .header("Cache-Control", "public, max-age=" + maxAge).removeHeader("Pragma").build();
                } else {
                    //无网络
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return response.newBuilder()
                            //.header("Cache-Control", "public,only-if-cached,max-stale=360000")
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale).removeHeader("Pragma").build();
                }
            }
        };
        return cacheInterceptor;
    }

}
