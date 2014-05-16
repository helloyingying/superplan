package com.liu.planplus.activity;

import com.liu.planplus.R;
import com.liu.planplus.util.SystemUiHider;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LoadActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		Handler x = new Handler();// 定义一个handle对象
		// 设置3秒钟延迟执行splashhandler线程。其实你这里可以再新建一个线程去执行初始化工作，如判断SD,网络状态等
		x.postDelayed(new splashhandler(), 2000);
/*
		// 此处实现读取数据库的操作
		Thread newThread; // 声明一个子线程
		newThread = new Thread(new Runnable() {
			@Override
			public void run() {
			}
		});
		newThread.start(); // 启动线程
*/
	}

	class splashhandler implements Runnable {
		public void run() {
			startActivity(new Intent(getApplication(), MainActivity.class));// 这个线程的作用3秒后就是进入到你的主界面
			LoadActivity.this.finish();// 把当前的LaunchActivity结束掉
		}
	}
}
