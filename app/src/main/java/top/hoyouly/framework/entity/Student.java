package top.hoyouly.framework.entity;

import java.util.List;

/**
 * Created by hoyouly on 18-3-23.
 */

public class Student {
	private String name;
	private List<Courses> courses;

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
