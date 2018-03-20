package top.hoyouly.framework.net.callback;

import top.hoyouly.framework.utils.IOUtilities;
import top.hoyouly.framework.utils.TextUtil;

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
