package top.hoyouly.framework.base;

/**
 * Created by hoyouly on 18-4-4.
 */

public class BasePersenter<V extends BaseMVPView> {

    /**
     * 绑定的view
     */
    protected V mvpView;


    /**
     * 绑定view，一般在初始化的时候调用该方法
     * @param mvpView
     */
    public void attachView(V mvpView){
        this.mvpView =mvpView;
    }

    /**
     * 断开View，一般在onDestroy中执行
     */
    public void detachView(){
        this.mvpView =null;
    }

    /**
     * 是否与该View建立连接，
     * 每次调用业务q请求的时候都需要先调用该方法检查是否与View进行了连接
     * @return
     */
    public boolean isViewAttached(){
        return this.mvpView !=null;
    }


    /**
     * 获取连接的View
     * @return
     */
    public V getView(){
        return mvpView;
    }

}
