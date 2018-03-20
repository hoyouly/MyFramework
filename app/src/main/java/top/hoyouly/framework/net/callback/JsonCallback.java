package com.example.hoyouly.myframework.net.callback;

import com.example.hoyouly.myframework.utils.JsonParser;

/**
 * Created by hoyouly on 15/5/3.
 */
public  abstract  class JsonCallback<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String content) {
        if(mReturnClass!=null){
            return JsonParser.deserializeByJson(content,mReturnClass);
        }else if(mReturnType!=null){
            return JsonParser.deserializeByJson(content,mReturnType);

        }
        return null;
    }
}
