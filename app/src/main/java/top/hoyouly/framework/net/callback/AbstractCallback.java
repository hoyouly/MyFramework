package top.hoyouly.framework.net.callback;

import top.hoyouly.framework.net.inter.ICallback;
import top.hoyouly.framework.utils.TextUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by hoyouly on 15/5/3.
 * 数据解析的工具类
 */
public abstract  class AbstractCallback<T> implements ICallback<T> {
    private static final int IO_BUFFER_SIZE = 1024*6;
    public String path;//用来存储文件的位置
    public Class<T> mReturnClass;
    public Type mReturnType;

    public  T handle(HttpResponse response){
        HttpEntity entity=response.getEntity();
        try {
            switch (response.getStatusLine().getStatusCode()){
                case HttpStatus.SC_OK://返回是200，解析数据
                    //服务返回的数据格式包括：json，file,xml,image,String,
                    if(TextUtil.isValidate(path)){
                        //把流写道文件中去
                        FileOutputStream fos=new FileOutputStream(path);
                        InputStream in=null;
                        if(entity.getContentEncoding()!=null){
                            String encoding=entity.getContentEncoding().getValue();
                            if(encoding!=null&&"gzip".equalsIgnoreCase(encoding)){
                                in=new GZIPInputStream(entity.getContent());
                            }else if(encoding!=null&&"deflat".equalsIgnoreCase(encoding)) {
                                in=new DeflaterInputStream(entity.getContent());

                            }
                        }else{
                            in=entity.getContent();
                        }

                        byte[] b=new byte[IO_BUFFER_SIZE];
                        int read;
                        while ((read=in.read(b))!=-1){
                            //TODO  update progress 更新进度的操作
                            fos.write(b,0,read);
                        }
                        fos.flush();
                        fos.close();
                        in.close();
                        return bindData(path);
                    }else{//说明不需要把数据存到存储卡上
                       return bindData(EntityUtils.toString(entity));
                    }
                default:
                    break;
            }

            return  null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;

    }

    public AbstractCallback<T> setPath(String path) {
        this.path = path;
        return  this;
    }
    public AbstractCallback<T> setReturnClass(Class<T> clz) {
        this.mReturnClass=clz;
        return  this;
    }
    public AbstractCallback<T> setReturnType(Type type) {
        this.mReturnType=type;
        return  this;
    }

    /**
     * 解析服务器返回的数据
     * @param content
     */
    protected T bindData(String content){
        return null;
    }

}
