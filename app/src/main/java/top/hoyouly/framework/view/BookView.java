package top.hoyouly.framework.view;

import top.hoyouly.framework.base.BaseView;
import top.hoyouly.framework.bean.Book;

/**
 * Created by hoyouly on 18/3/28.
 */

public interface BookView extends BaseView {
    void onSuccess(Book book);
    void onError();

}
