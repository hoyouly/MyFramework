package top.hoyouly.framework.net;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by hoyouly on 15/5/3.
 */
public class RequestTask extends AsyncTask<Object,Integer,Object> {

    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Object doInBackground(Object... objects) {
        try {
            Thread.sleep(2000);
            HttpResponse response=HttpClientUtil.execute(request);
            return request.callback.handle(response);
        } catch (IOException e) {
            //抛异常我就把异常返回出去，因为我需要告诉上一层，即UI线程知道这个事情
            return e;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }


    @Override
    protected void onPostExecute(Object result) {
        if(result instanceof  Exception){//说明是把返回的时一个异常
            request.callback.onFailure((Exception)result);
        }else{
            request.callback.onSuccess(result);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
