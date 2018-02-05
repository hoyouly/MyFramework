package com.example.hoyouly.myframework.net.callback;

import com.example.hoyouly.myframework.utils.IOUtilities;
import com.example.hoyouly.myframework.utils.TextUtil;

/**
 * Created by hoyouly on 15/5/3.
 */
public abstract class StringCallback extends AbstractCallback {

    @Override
    protected String bindData(String content) {
        if(TextUtil.isValidate(path)){
            return  IOUtilities.readFromFile(path);
        }
        return content;
    }
}
