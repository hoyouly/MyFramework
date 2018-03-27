package top.hoyouly.framework.net;

import rx.functions.Func1;

/**
 * Created by hoyouly on 18-3-27.
 * PlayLoad 继承Func1,接收一个BaseResponse<T> ,就是接口返回的JSON数据结构，返回的是T，就是data,
 */

public class PayLoad<T> implements Func1<BaseResponse<T>,T> {
	@Override
	public T call(BaseResponse<T> tBaseResponse) {
		if(!tBaseResponse.isSusscess()){//获取数据失败时，包装一个Fault 抛给上层处理错误
			throw  new Fault(tBaseResponse.status,tBaseResponse.message);
		}

		return tBaseResponse.data;
	}
}
