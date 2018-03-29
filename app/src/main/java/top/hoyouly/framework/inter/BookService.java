package top.hoyouly.framework.inter;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import top.hoyouly.framework.bean.Book;

/**
 * Created by hoyouly on 18/3/28.
 */

public interface BookService {

    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name//
            , @Query("tag") String tag//
            , @Query("start") int start//
            , @Query("count") int count);

}
