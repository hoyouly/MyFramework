package top.hoyouly.framework.bean;

import java.util.List;

/**
 * Created by hoyouly on 18-3-23.
 */

public class Student {
	public String name;
	public String nickName;
	public List<Courses> courses;

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
