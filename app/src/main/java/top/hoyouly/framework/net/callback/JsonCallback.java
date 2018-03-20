package top.hoyouly.framework.net.callback;

import top.hoyouly.framework.utils.JsonParser;

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
