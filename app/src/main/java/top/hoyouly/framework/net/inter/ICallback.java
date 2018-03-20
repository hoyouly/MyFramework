package top.hoyouly.framework.net.inter;

import org.apache.http.HttpResponse;

/**
 * Created by hoyouly on 15/5/3.
 */
public interface ICallback<T> {

    void onFailure(Exception result);

    void onSuccess(T result);

    T handle(HttpResponse response);
}
