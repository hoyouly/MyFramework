package top.hoyouly.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import top.hoyouly.framework.base.BaseActivity;

/**
 * Created by hoyouly on 18/3/22.
 */

public class RxjavaActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observer<String> observer=new Observer<String>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Subscriber<String> sub=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("RxJava");
                subscriber.onCompleted();
            }
        });
        observable=Observable.just("Hello","Hi","RxJava");

        String[] array=new String[]{"Hello","Hi","RxJava"};
        observable=Observable.from(array);

        observable.subscribe(observer);
        observable.subscribe(sub);

        Action1<String> onNextAction=new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };

        Action1<Throwable > onErrotAction=new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };

        Action0 onCompletedAction=new Action0() {
            @Override
            public void call() {

            }
        };

        observable.subscribe(onNextAction,onErrotAction,onCompletedAction);


    }
}
