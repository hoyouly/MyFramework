package com.example.hoyouly.myframework.net.inter;

import org.apache.http.HttpResponse;

/**
 * Created by hoyouly on 15/5/3.
 */
public interface ICallback {

    void onFailure(Exception result);

    void onSuccess(Object result);

    Object handle(HttpResponse response);
}
