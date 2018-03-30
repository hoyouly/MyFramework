package top.hoyouly.framework.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by hoyouly on 18-3-30.
 */

public class ImageUtils {

	@BindingAdapter({"img"})
	public static void loadInternetImage(ImageView iv, String img) {
		Picasso.get().load(img).into(iv);
	}


}
