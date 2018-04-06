package top.hoyouly.framework.mvpview;

import java.util.List;

import top.hoyouly.framework.base.BaseMVPView;

/**
 * Created by hoyouly on 18-4-4.
 */

public interface BenefitView<T> extends BaseMVPView {
	void setPageState(boolean isLoading);

	void setListData(List<T> benefitBeans,int type);

	void onRefreshComplete();

	void onLoadMoreComplete();

	void onInitFailed();
}
