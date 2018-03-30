package top.hoyouly.framework.bean;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by hoyouly on 18-3-30.
 */

public class Food {
	public String description;
	public String img;
	public String keywords;
	public String summary;
	public Food() {
	}

	public Food(String description, String img, String keywords, String summary) {
		this.description = description;
		this.img = img;
		this.keywords = keywords;
		this.summary = summary;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
