package top.hoyouly.framework;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.bean.Courses;
import top.hoyouly.framework.bean.Student;

/**
 * Created by hoyouly on 18/3/22.
 */

public class RxjavaActivity extends BaseActivity {
	int imgResId ;
	ImageView imageView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rxjava);
		imageView= (ImageView) findViewById(R.id.btn);
		imgResId=R.mipmap.ic_launcher;
//		new AlertDialog.Builder(this).setIcon().setTitle();


//		Observer<String> observer = new Observer<String>() {
//			@Override
//			public void onCompleted() {
//
//			}
//
//			@Override
//			public void onError(Throwable e) {
//
//			}
//
//			@Override
//			public void onNext(String s) {
//
//			}
//		};
//
//		Subscriber<String> sub = new Subscriber<String>() {
//			@Override
//			public void onCompleted() {
//
//			}
//
//			@Override
//			public void onError(Throwable e) {
//
//			}
//
//			@Override
//			public void onNext(String s) {
//
//			}
//		};
//
//		Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//			@Override
//			public void call(Subscriber<? super String> subscriber) {
//				subscriber.onNext("Hello");
//				subscriber.onNext("Hi");
//				subscriber.onNext("RxJava");
//				subscriber.onCompleted();
//			}
//		});
//		observable = Observable.just("Hello", "Hi", "RxJava");
//
//		String[] array = new String[]{"Hello", "Hi", "RxJava"};
//		observable = Observable.from(array);
//
//		observable.subscribe(observer);
//		observable.subscribe(sub);
//
//		Action1<String> onNextAction = new Action1<String>() {
//			@Override
//			public void call(String s) {
//			}
//		};
//
//		Action1<Throwable> onErrorAction = new Action1<Throwable>() {
//			@Override
//			public void call(Throwable throwable) {
//			}
//		};
//
//		Action0 onCompletedAction = new Action0() {
//			@Override
//			public void call() {
//			}
//		};
//
//		//自动创建Subscriber,并且使用onNextAction来定义onNext()方法
//		observable.subscribe(onNextAction);
//		//自动创建Subscriber,并且使用onNextAction来定义onNext()方法，使用onErrorAction来定义onError()方法
//		observable.subscribe(onNextAction, onErrorAction);
//		//自动创建Subscriber,并且使用onNextAction来定义onNext()方法，使用onErrorAction来定义onError()方法，使用onCompleteAction来定义onComplete()方法
//		observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
//
//		Schedulers.immediate();
//		Schedulers.newThread();
//		Schedulers.computation();
//
//		Observable.just(1, 2, 3, 4)//
//				.subscribeOn(Schedulers.io())//指定subcribe()发生的线程是在io线程，即被创建的事件的内容 1、2、3、4 将会在 IO 线程发出
//				.observeOn(AndroidSchedulers.mainThread())//指定Subscribe的回调线程是在Android主UI线程
//				.subscribe(new Action1<Integer>() {
//					@Override
//					public void call(Integer integer) {//这个方法将会在Android 主线程中执行
//
//					}
//				});
		Log.d("hoyouly", getClass().getSimpleName() + " -> onCreate: "+Thread.currentThread());
		Observable.create(new Observable.OnSubscribe<Drawable>() {
			@TargetApi(Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void call(Subscriber<? super Drawable> subscriber) {
				Log.d("hoyouly", getClass().getSimpleName() + " -> call: "+Thread.currentThread());
				Drawable drawable = getTheme().getDrawable(imgResId);
				subscriber.onNext(drawable);
				subscriber.onCompleted();
			}
		})//
		.subscribeOn(Schedulers.io())//指定subscribe()即 加载图片过程 是在io线程中
		.observeOn(AndroidSchedulers.mainThread())//指定Subscribe回调是在Android UI线程中，即设置图片则被设定在了主线程
		.subscribe(new Observer<Drawable>() {
					@Override
					public void onCompleted() {
						Log.d("hoyouly", getClass().getSimpleName() + " -> onCompleted: "+Thread.currentThread());
					}

					@Override
					public void onError(Throwable e) {
						Toast.makeText(getApplicationContext(), "加载图片失败", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onNext(Drawable drawable) {
						Log.d("hoyouly", getClass().getSimpleName() + " -> onNext: "+Thread.currentThread());
						imageView.setImageDrawable(drawable);
					}
				});


		Observable.just("images/logo.png")//输入类型是String
				.map(new Func1<String, Bitmap>() { //Func1 两个泛型变量，String,Bitmap
					@Override
					public Bitmap call(String s) {// 参数类型是String，返回类型是Bitmap
						return null;
					}
				})//
				.subscribe(new Action1<Bitmap>() {//一个参数类型，输入类型是Bitmap
					@Override
					public void call(Bitmap bitmap) {//参数类型是Bitmap，返回类型是void

					}
				});

		flatmapTest();

	}

	private void flatmapTest() {
		//打印一组学生的名字
		final Student [] students=new Student[]{};
		final Subscriber<String> subscriber = new Subscriber<String>() {
			@Override
			public void onCompleted() {}

			@Override
			public void onError(Throwable e) {}

			@Override
			public void onNext(String s) {
				//输出来的就是学生的名字
			}
		};

		Func1<Student, String> func1 = new Func1<Student, String>() {
			@Override
			public String call(Student student) {
				return student.name;
			}
		};

		Observable.from(students)//
				.map(func1)//
				.subscribe(subscriber);

		//打印每个学生所选的课程

		Subscriber<Student> studentSubscriber=new Subscriber<Student>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Student student) {
				//得到的是学生信息，然后通过for循环打印课程
				for (Courses courses:student.courses) {
					Log.d("hoyouly", getClass().getSimpleName() + " -> onNext: "+courses.toString());
				}
			}
		};

		Observable.from(students).subscribe(studentSubscriber);

		//使用flatmap()方式打印

		Subscriber<Courses> coursesSubscriber=new Subscriber<Courses>() {
			@Override
			public void onCompleted() {}

			@Override
			public void onError(Throwable e) {}

			@Override
			public void onNext(Courses courses) {
				//得到的就是每一个课程的名字
			}
		};

		Observable.from(students)//
				.flatMap(new Func1<Student, Observable<Courses>>() {
					@Override
					public Observable<Courses> call(Student student) {
						return Observable.from(student.courses);
					}
				})//
				.subscribe(coursesSubscriber);


//		Observable<Student> stu=new Observable<Student>(new Observable.OnSubscribe<Student>(){
//			@Override
//			public void call(Subscriber<? super Student> subscriber) {
//
//			}
//		});

		final Subscriber<String> stringSubscriber=new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String s) {

			}
		};

		Subscriber<Student> subscriber1=new Subscriber<Student>(stringSubscriber){
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Student student) {
//				stringSubscriber.onNext();
			}
		};


	}

}
