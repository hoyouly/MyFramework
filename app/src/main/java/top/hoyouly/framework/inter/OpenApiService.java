package top.hoyouly.framework.inter;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import top.hoyouly.framework.bean.MeituBean;
import top.hoyouly.framework.bean.OpenApiResultBean;
import top.hoyouly.framework.bean.SatinBean;

public interface OpenApiService {

    //meituApi?page=1

    @GET("meituApi")
    Observable<OpenApiResultBean<MeituBean>> getMeituList(@Query("page")int page);


    //satinApi?type=1&page=1

    @GET("satinApi")
    Observable<OpenApiResultBean<SatinBean>> getSatinList(@Query("type") int type,@Query("page") int page);
}
