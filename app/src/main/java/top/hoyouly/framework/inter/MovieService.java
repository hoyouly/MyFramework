package top.hoyouly.framework.inter;


import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;
import top.hoyouly.framework.entity.MovieObject;
import top.hoyouly.framework.entity.Translation1;

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
    Call<MovieObject> getTop250ByPost(@Field("start") int start, @Field("count") int count);

    @GET("top250")
    Observable<MovieObject> getTop250WithRxJava(@Query("start") int start,@Query("count") int count);


    @POST("/from")
    @Multipart
    @Streaming
    Call<ResponseBody> testFileUpload1(@Part("name") ResponseBody nameBody, @Part("age") ResponseBody ageBody,@Part MultipartBody.Part file);

    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translation1> getCall(@Field("i") String targetSentence);

     @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Observable<Translation1> getCallByRxJava(@Field("i") String targetSentence);



}
