package top.hoyouly.framework.utils;

/**
 * Created by hoyouly on 18/3/27.
 */

public abstract class Singleton<T> {

    private T instance;
    protected abstract T creat();

    public final T get(){
        if(instance==null){
            synchronized (this){
                if(instance==null){
                    instance=creat();
                }
            }
        }
        return instance;
    }

}
