package com.liu.planplus.activity;

import com.liu.planplus.R;
import com.liu.planplus.toolClass.Constants;
import com.liu.planplus.toolClass.RotationHelper;
import com.liu.planplus.view.TitleView.OnLeftButtonClickListener;
import com.liu.planplus.view.TitleView.OnRightButtonClickListener;
import com.liu.planplus.view.TitleView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	private TitleView mTitle;
	public LinearLayout layout_main;
	String tag;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		layout_main = (LinearLayout) findViewById(R.id.layout_main);
		showView();
		
		mTitle = (TitleView) findViewById(R.id.title);
		mTitle.setTitle(R.string.plantitle);
		mTitle.setLeftButton(R.string.exit, new OnLeftButtonClickListener(){
			@Override
			public void onClick(View button) {
				finish();
			}
			
		});
		mTitle.setRightButton(R.string.more, new OnRightButtonClickListener() {

			@Override
			public void onClick(View button) {
				rotateHelper = new RotationHelper(MainActivity.this, Constants.KEY_FIRST_INVERSE);
				rotateHelper.applyFirstRotation(layout_main, 0, -90);
			}
		});
		
	}

	public void showView() {
		Bundle bundle = this.getIntent().getExtras();

		if (bundle != null) {
			tag = bundle.getString("second");
			System.out.println("tag =" + tag);
			if (tag.equals("Second")) {
				rotateHelper = new RotationHelper(this,
						Constants.KEY_FIRST_CLOCKWISE);
				rotateHelper.applyLastRotation(layout_main, -90, 0);
			}
		}
	}

	RotationHelper rotateHelper;

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		rotateHelper = new RotationHelper(this, Constants.KEY_FIRST_INVERSE);
//		rotateHelper.applyFirstRotation(layout_main, 0, -90);
//	}

	public void jumpToMore() {
		Intent in = new Intent();
		in.setClass(this, MoreActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("front", "First");
		in.putExtras(bundle);
		// in.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(in);
		finish();
	}
	
}
