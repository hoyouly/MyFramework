package top.hoyouly.framework.loder;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import top.hoyouly.framework.base.BaseLoader;
import top.hoyouly.framework.bean.Movie;
import top.hoyouly.framework.bean.MovieObject;
import top.hoyouly.framework.inter.MovieService;
import top.hoyouly.framework.net.PayLoad;
import top.hoyouly.framework.net.RetrofitServiceManager;

/**
 * Created by hoyouly on 18-3-27.
 */

public class MovieLoader extends BaseLoader {
	private MovieService movieService;

	public MovieLoader() {
		movieService = RetrofitServiceManager.getInstance().creat(MovieService.class);
	}

	public Observable<List<Movie>> getMovie(int start, int count) {

		Observable<List<Movie>> movieObservable = movieService.getTop250WithRxJava1(start, count).map(new Func1<MovieObject, List<Movie>>() {
			@Override
			public List<Movie> call(MovieObject movieObject) {
				return movieObject.subjects;
			}
		});
		return observe(movieObservable);
	}

	public Observable<List<Movie>> getMovie2(int start, int count) {
		Observable<List<Movie>> movieObservable = movieService.getTop250WithRxJava2(start, count).map(new PayLoad<List<Movie>>());
		return observe(movieObservable);
	}

	public Observable<String> getWeather(String cityid, String key) {
		return observe(movieService.getWeather(cityid, key).map(new Func1<String, String>() {
			@Override
			public String call(String s) {
				return s;
			}
		}));
	}

	;

}
