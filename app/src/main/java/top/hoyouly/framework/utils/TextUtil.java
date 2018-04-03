package top.hoyouly.framework.utils;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.TextView;

import top.hoyouly.framework.bean.User6;

/**
 * Created by hoyouly on 15/5/3.
 */
public class TextUtil {
    /**
     * 判断字符串是否为null，或者是空字符串
     * @param content
     * @return
     */
    public static  boolean isValidate(String content){
        return content!=null&&"".equals(content.trim());
    }
    public static String doubleWord(final String word) {
        return word+"_"+word;
    }


    public static String displayPassowrdText(String text) {
        if (text == null) {
            return null;
        }
        String s = "";
        for (int i = 0; i < text.length(); i++) {
            s = s + "*";
        }

        return s;
    }


    @BindingAdapter(value = {"password"})//可以简写为 @BindingAdapter({"password"})
    public static  void displayPassowrdText(TextView view, String text) {
        if (text == null) {
            Log.d("BindingAdapter setText", "为空");
            return;
        }

        String s = "";
        for (int i = 0; i < text.length(); i++) {
            s = s + "*";
        }

        view.setText(s);
    }

    public void onMyClick(User6 u) {
        Log.d("hoyouly", u.content.get() + "");
    }


}
