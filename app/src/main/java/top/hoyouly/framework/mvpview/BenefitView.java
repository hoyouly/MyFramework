package top.hoyouly.framework.mvpview;

import java.util.List;

import top.hoyouly.framework.base.BaseMVPView;
import top.hoyouly.framework.bean.GankDataBean;

/**
 * Created by hoyouly on 18-4-4.
 */

public interface BenefitView extends BaseMVPView {
	void setPageState(boolean isLoading);

	void setListData(List<GankDataBean> benefitBeans,int type);

	void onRefreshComplete();

	void onLoadMoreComplete();

	void onInitFailed();
}
