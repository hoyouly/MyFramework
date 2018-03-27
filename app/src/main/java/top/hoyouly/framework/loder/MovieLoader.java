package top.hoyouly.framework.loder;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import top.hoyouly.framework.entity.Movie;
import top.hoyouly.framework.entity.MovieObject;
import top.hoyouly.framework.inter.MovieService;
import top.hoyouly.framework.net.ObjectLoader;
import top.hoyouly.framework.net.RetrofitServiceManager;

/**
 * Created by hoyouly on 18-3-27.
 */

public class MovieLoader extends ObjectLoader {
	private MovieService movieService;

	public MovieLoader() {
		movieService= RetrofitServiceManager.getInstance().creat(MovieService.class);
	}

	public Observable<List<Movie>> getMovie(int start,int count){
		Observable<List<Movie>> movieObservable = movieService.getTop250WithRxJava(start, count).map(new Func1<MovieObject, List<Movie>>() {
			@Override
			public List<Movie> call(MovieObject movieObject) {
				return null;
			}
		});
		return observe(movieObservable);

	}

	public Observable<String> getWeather(String cityid,String key){
		return observe(movieService.getWeather(cityid,key).map(new Func1<String, String>() {
			@Override
			public String call(String s) {
				return s;
			}
		}));

	};

}
