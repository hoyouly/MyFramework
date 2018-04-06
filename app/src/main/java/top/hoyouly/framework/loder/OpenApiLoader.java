package top.hoyouly.framework.loder;

import rx.Observable;
import top.hoyouly.framework.base.BaseLoader;
import top.hoyouly.framework.bean.OpenApiResultBean;
import top.hoyouly.framework.bean.SatinBean;
import top.hoyouly.framework.config.ApiConfig;
import top.hoyouly.framework.bean.MeituBean;
import top.hoyouly.framework.inter.OpenApiService;
import top.hoyouly.framework.net.RetrofitServiceManager;

public class OpenApiLoader extends BaseLoader {
    private OpenApiService openApiService;

    public OpenApiLoader() {
        openApiService = RetrofitServiceManager.getInstance(ApiConfig.APIOPEN_BASE_URL).creat(OpenApiService.class);
    }

    public Observable<OpenApiResultBean<MeituBean>> getMeituList(int page) {
        return observe(openApiService.getMeituList(page));
    }

    public Observable<OpenApiResultBean<SatinBean>> getSatinList(int type, int page) {
        return observe(openApiService.getSatinList(type, page));
    }

}
