package top.hoyouly.framework;

import android.os.Bundle;

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
            InputStream is=getAssets().open("qm.jpg");
			largeImageView.setInputStream(is);

        } catch (Exception e) {
			e.printStackTrace();
		}

	}
}
