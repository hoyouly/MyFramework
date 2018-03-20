package com.example.hoyouly.myframework.net;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Map;

/**
 * Created by hoyouly on 15/5/3.
 */
public class HttpClientUtil {

    /**
     * 不希望在上一层调用get 或者post 方法，通过一个方法来执行到底是是get  或者post方法
     * execute  就是为了执行请求方法，至于是get 还是post  就在execute中执行
     */
    public static HttpResponse execute(Request request) throws IOException {
        switch (request.method) {
            case GET:
               return get(request);
            case POST:
               return post(request);
            default:
                throw new IllegalArgumentException("the method "+request.method+" does not support");
//            case PUT:
//                break;
//            case DELETE:
//                break;

        }

    }

    public static HttpResponse get(Request request) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(request.url);
        addHeader(get,request.header);
        return client.execute(get);


    }

    public static HttpResponse post(Request request) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(request.url);
        addHeader(post, request.header);
        if(request.entity==null){
            throw  new IllegalStateException("you forgot to set post content to the HttpPost");
        }else{
            post.setEntity(request.entity);

        }
        return client.execute(post);


    }


    /**
     * 添加header的方法
     * @param request
     * @param headers  因为可能有多个header ，所以是一个map集合
     */
    public  static void addHeader(HttpUriRequest request,Map<String,String> headers){
        if(headers!=null&&headers.size()>0){
            for (Map.Entry<String,String> entry:headers.entrySet()){
                request.addHeader(entry.getKey(),entry.getValue());
            }
        }
    }

}
