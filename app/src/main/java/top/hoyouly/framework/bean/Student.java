package top.hoyouly.framework.bean;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hoyouly on 18-3-23.
 */

public class Student {
	public String name;
	public String nickName;
	public List<Courses> courses;
	public int age;
	public String userface;

    public Student(String name, String userface) {
        this.name = name;
        this.userface = userface;
    }

    public Student() {
    }



    @BindingAdapter("bind:userface")
    public static void getInternetImage(ImageView imageView, String userface){
        Picasso.get().load(userface).into(imageView);
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}
}
