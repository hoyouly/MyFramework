package top.hoyouly.framework.bean;

import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by hoyouly on 18/3/31.
 */

public class GankDataBean {

    /**
     * _id : 5a7c42c8421aa90d24a065d4
     * createdAt : 2018-02-08T20:30:00.798Z
     * desc : 一个动画效果的播放控件，播放，暂停，停止之间的动画切换
     * images : ["http://img.gank.io/c1ee3231-648d-4449-a455-04a13731b2e1"]
     * publishedAt : 2018-02-22T08:24:35.209Z
     * source : web
     * type : Android
     * url : https://github.com/SwiftyWang/AnimatePlayButton
     * used : true
     * who : null
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private Object who;
    private List<String> images;

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

    public Object getWho() {
        return who;
    }

    public void setWho(Object who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    public void onItemClick(View view){
        Toast.makeText(view.getContext(),toString() , Toast.LENGTH_SHORT).show();
        setDesc(toString());
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
        sb.append(",\"who\":").append(who);
        sb.append(",\"images\":").append(images);
        sb.append('}');
        return sb.toString();
    }
}
