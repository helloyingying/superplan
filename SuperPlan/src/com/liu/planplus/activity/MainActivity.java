package com.liu.planplus.activity;

import com.liu.planplus.R;
import com.liu.planplus.view.TitleView.OnLeftButtonClickListener;
import com.liu.planplus.view.TitleView.OnRightButtonClickListener;
import com.liu.planplus.view.TitleView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {
	private TitleView mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mTitle = (TitleView) findViewById(R.id.title);
		mTitle.setTitle(R.string.plantitle);
		mTitle.setLeftButton(R.string.exit, new OnLeftButtonClickListener(){

			@Override
			public void onClick(View button) {
			}
			
		});
		mTitle.setRightButton(R.string.more, new OnRightButtonClickListener() {

			@Override
			public void onClick(View button) {

			}
		});
		
	}

	//点击menu按键时显示或隐藏各种选项
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
