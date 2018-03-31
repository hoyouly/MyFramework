package top.hoyouly.framework.loder;

import rx.Observable;
import top.hoyouly.framework.base.BaseLoader;
import top.hoyouly.framework.bean.GankBean;
import top.hoyouly.framework.inter.GankService;
import top.hoyouly.framework.net.RetrofitServiceManager;

/**
 * Created by hoyouly on 18/3/28.
 * 这个类其实就是为了更好的调用BookService中的方法，
 */

public class GankLoader extends BaseLoader {
    private GankService gankService;

    public GankLoader() {
        gankService = RetrofitServiceManager.getInstance().creat(GankService.class);
    }

    //定义了一个和 BookService 中同名的方法，里面其实就是调用 BookService 中的这个方法
    //这样，把 BookService 中定义的方法都封装到 DataManager 中，
    // 以后无论在哪个要调用方法时直接在DataManager 中调用就可以了，而不是重复建立 BookService 的实例化，再调用其中的方法。
    public Observable<GankBean> getGankBenefitList(int num, int page){
        Observable<GankBean> observable = gankService.getGankBenefitList(num,page);
        return observe(observable);
    }
}
