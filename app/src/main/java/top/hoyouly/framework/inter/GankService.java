package top.hoyouly.framework.inter;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import top.hoyouly.framework.bean.GankBean;

/**
 * Created by hoyouly on 18-3-30.
 */

public interface GankService {
	@GET("%E7%A6%8F%E5%88%A9/{num}/{page}") //数据类型/请求个数/第几页
	Observable<GankBean> getGankBenefitList(@Path("num")int num, @Path("page") int page);
}
