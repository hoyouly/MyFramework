package top.hoyouly.framework.bean;

import java.util.List;

public class OpenApiResultBean<T> {
    /**
     * code : 200
     * msg : 成功!
     * data : []
     */

    private int code;
    private String msg;
    private List<T> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}