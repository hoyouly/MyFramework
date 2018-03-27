package top.hoyouly.framework.net;

/**
 * Created by hoyouly on 18-3-27.
 */

public class BaseResponse<T> {
	public int status;
	public String message;
	public T data;// 接口返回JSON数据结构，就是T，

	/**
	 * 判断是否返回成功，
	 * @return
	 */
	public boolean isSusscess(){
		return status==200;
	}

}
