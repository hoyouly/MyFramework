package top.hoyouly.framework.bean;

/**
 * Created by hoyouly on 18/3/25.
 */

import android.databinding.BaseObservable;

/**
 * Created by Phil on 2017/8/17.
 */

public class User3 extends BaseObservable {
	private String id;
	private String name;
	private String blog;

	public User3() {

	}

	public User3(String id, String name, String blog) {
		this.id = id;
		this.name = name;
		this.blog = blog;
	}

	public void setId(String id) {
		this.id = id;
//		notifyPropertyChanged(BR.id);
	}

//	@Bindable
	public String getId() {
		return this.id;
	}


	public void setName(String name) {
		this.name = name;
//		notifyPropertyChanged(BR.name);
	}

//	@Bindable
	public String getName() {
		return this.name;
	}

	public void setBlog(String blog) {
		this.blog = blog;
//		notifyPropertyChanged(BR.blog);
	}

//	@Bindable
	public String getBlog() {
		return this.blog;
	}
}
