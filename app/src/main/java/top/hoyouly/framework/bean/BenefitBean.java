package top.hoyouly.framework.bean;

import android.view.View;
import android.widget.Toast;

/**
 * Created by hoyouly on 18-3-30.
 * 福利 url 的对应的实体
 * http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
 *
 */

public class BenefitBean {

	/**
	 * _id : 5a967b41421aa91071b838f7
	 * createdAt : 2018-02-28T17:49:53.265Z
	 * desc : MusicLibrary-一个丰富的音频播放SDK
	 * publishedAt : 2018-03-12T08:44:50.326Z
	 * source : web
	 * type : Android
	 * url : https://github.com/lizixian18/MusicLibrary
	 * used : true
	 * who : lizixian
	 */

	private String _id;
	private String createdAt;
	private String desc;
	private String publishedAt;
	private String source;
	private String type;
	private String url;
	private boolean used;
	private String who;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"_id\":\"").append(_id).append('\"');
		sb.append(",\"createdAt\":\"").append(createdAt).append('\"');
		sb.append(",\"desc\":\"").append(desc).append('\"');
		sb.append(",\"publishedAt\":\"").append(publishedAt).append('\"');
		sb.append(",\"source\":\"").append(source).append('\"');
		sb.append(",\"type\":\"").append(type).append('\"');
		sb.append(",\"url\":\"").append(url).append('\"');
		sb.append(",\"used\":").append(used);
		sb.append(",\"who\":\"").append(who).append('\"');
		sb.append('}');
		return sb.toString();
	}

	public void onItemClick(View view){
		Toast.makeText(view.getContext(),toString() , Toast.LENGTH_SHORT).show();
		setDesc(toString());
	}
}
