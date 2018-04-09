package top.hoyouly.framework;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;

import top.hoyouly.framework.base.BaseActivity;
import top.hoyouly.framework.view.LargeImageView;


public class LargeImageViewActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_largeimageview);

		LargeImageView largeImageView = findViewById(R.id.liv);

		try {
			InputStream is = getAssets().open("world_1.jpg");
			largeImageView.setInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
