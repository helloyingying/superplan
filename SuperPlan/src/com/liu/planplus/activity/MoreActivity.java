package com.liu.planplus.activity;

import com.liu.planplus.R;
import com.liu.planplus.toolClass.Constants;
import com.liu.planplus.toolClass.RotationHelper;
import com.liu.planplus.view.TitleView;
import com.liu.planplus.view.TitleView.OnLeftButtonClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Intent;

public class MoreActivity extends Activity {

	String tag = "";

	RotationHelper rotateHelper;
	private TitleView mTitle;
	public RelativeLayout layout_more;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more);
		
		mTitle = (TitleView) findViewById(R.id.title);
		mTitle.setTitle(R.string.plantitle);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener(){
			@Override
			public void onClick(View button) {
				rotateHelper = new RotationHelper(MoreActivity.this,
						Constants.KEY_SECOND_CLOCKWISE);
				rotateHelper.applyFirstRotation(layout_more, 0, 90);
			}
			
		});
		
		layout_more = (RelativeLayout) findViewById(R.id.layout_more);
		showView();
	}

	public void showView() {
		/* 取得Intent中的Bundle对象 */
		Bundle bundle = this.getIntent().getExtras();

		if (bundle != null) {
			/* 取得Bundle对象中的数据 */
			tag = bundle.getString("front");
		}

		System.out.println("bundle =" + tag);

		if (tag.equals("First")) {
			rotateHelper = new RotationHelper(this, Constants.KEY_SECOND_INVERSE);
			rotateHelper.applyLastRotation(layout_more, 90, 0);
		}
	}

	public void jumpToMain() {
		Intent in = new Intent();
		in.setClass(this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("second", "Second");
		in.putExtras(bundle);
		startActivity(in);
		finish();
	}
}
