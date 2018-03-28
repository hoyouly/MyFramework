package top.hoyouly.framework.presenter;

import android.content.Intent;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;
import top.hoyouly.framework.base.BasePresenter;
import top.hoyouly.framework.entity.Book;
import top.hoyouly.framework.loder.BookLoader;
import top.hoyouly.framework.view.BookView;
import top.hoyouly.framework.base.BaseView;

/**
 * Created by hoyouly on 18/3/28.
 */

public class BookPresenter implements BasePresenter {

    private BookLoader bookLoader;
    private Book mBook;
    private BookView bookView;
    //CompositeSubscription是用来存放RxJava中的订阅关系的。注意请求完数据要及时清掉这个订阅关系，不然会发生内存泄漏
    private CompositeSubscription compositeSubscription;

    @Override
    public void onCreat() {
        bookLoader=new BookLoader();
        compositeSubscription=new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if(compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attchView(BaseView view) {
        bookView= (BookView) view;
    }

    @Override
    public void attchIncomingIntent(Intent intent) {

    }

    public void getSearchBook(String name,String tag,int start,int count){
        //向CompositeSubscription添加一个订阅关系
        compositeSubscription.add(bookLoader.getSearchBook(name, tag, start, count)//
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        if (mBook != null) {
                            bookView.onSuccess(mBook);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                    }
                }));
    }

}
