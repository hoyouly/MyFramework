package top.hoyouly.framework.inter;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import top.hoyouly.framework.bean.User;

/**
 * Created by hoyouly on 18/3/19.
 */

public interface IUserBiz {
    //通过GET标记为get请求，GET中所填写的value,即 user,会与baseUrl组成完整的路径，baseUrl()会在构造函数中给出
    @GET("user")
    Call<List<User>> getUser();

}
