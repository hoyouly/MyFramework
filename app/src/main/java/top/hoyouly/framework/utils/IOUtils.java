package top.hoyouly.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hoyouly on 15/5/3.
 */
public class IOUtils {


    /**
     * 读取文件中的东西，并转换成字符串
     * @param path 文件路径
     * @return
     */
    public static String readFromFile(String path) {
        ByteArrayOutputStream out=null;
        InputStream in=null;
        try {
            File f=new File(path);
            in= new FileInputStream(f);
            out=new ByteArrayOutputStream(1024);
            byte[] b=new byte[1000];
            int n;
            while ((n=in.read(b))!=-1){
                out.write(b,0,n);
            }
            out.flush();
            return new String(out.toByteArray());


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
