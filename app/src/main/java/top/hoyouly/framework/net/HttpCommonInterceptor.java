package top.hoyouly.framework.net;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.*;
import okhttp3.Request;

/**
 * 拦截器，向请求头添加公共参数
 * Created by hoyouly on 18-3-27.
 */

public class HttpCommonInterceptor implements Interceptor {
	private Map<String, String> headerParamsMap = new HashMap<String, String>();

	public HttpCommonInterceptor() {
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		okhttp3.Request oldRequest = chain.request();
		long t1 = System.nanoTime();
		Log.e("hoyouly", "HttpCommonInterceptor start" + String.format("Sending request %s on %s%n%s", oldRequest.url(), chain.connection(),
                oldRequest.headers()));
		//新的请求
		Request.Builder requestBuilder = oldRequest.newBuilder();
		requestBuilder.method(oldRequest.method(), oldRequest.body());

		if (headerParamsMap.size() > 0) {
			for (Map.Entry<String, String> params : headerParamsMap.entrySet()) {
				requestBuilder.addHeader(params.getKey(), params.getValue());
			}
		}
		Request newRequest = requestBuilder.build();

		Response response = chain.proceed(newRequest);
		long t2 = System.nanoTime();
		Log.e("hoyouly", "HttpCommonInterceptor end " + String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 -
                t1) / 1e6d, response.headers()));
		return response;
	}

	public static class Builder {
		HttpCommonInterceptor commonInterceptor;

		public Builder() {
			commonInterceptor = new HttpCommonInterceptor();
		}

		public Builder addHeaderParams(String key, String value) {
			commonInterceptor.headerParamsMap.put(key, value);
			return this;
		}

		public Builder addHeaderParams(String key, int value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public Builder addHeaderParams(String key, float value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public Builder addHeaderParams(String key, long value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public Builder addHeaderParams(String key, double value) {
			return addHeaderParams(key, String.valueOf(value));
		}

		public HttpCommonInterceptor build(){
			return commonInterceptor;
		}
	}
}
