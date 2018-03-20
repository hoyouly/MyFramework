package top.hoyouly.framework.inter;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import top.hoyouly.framework.entity.MovieObject;

/**
 * Created by hoyouly on 18/3/19.
 */

public interface MovieService {
    ////获取豆瓣Top250 榜单
    @GET("top250") //标签后面是这个接口的 尾址top250,完整的地址应该是 baseUrl+尾址
    //参数 使用@Query标签，如果参数多的话可以用@QueryMap标签，接收一个Map
    //@Query表示请求参数，将会以key=value的方式拼接在url后面
    Call<MovieObject> getTop250(@Query("start") int start, @Query("count") int count);


    @FormUrlEncoded  //必须加上 @FormUrlEncoded标签，否则会抛异常
    @POST("top250")
    //参数标签用 @Field 或者@Body或者FieldMap   使用POST方式时，必须要有参数，否则会抛异常,
    Call<MovieObject> getTop250ByPost(@Field("statr") int start, @Field("conut") int count);
}
