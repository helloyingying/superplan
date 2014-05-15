package com.liu.superplan.activity;

import com.liu.superplan.R;
import com.liu.superplan.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
		
		//此处实现读取数据库的操作
		 Thread newThread; //声明一个子线程
		 newThread = new Thread(new Runnable() {
		     @Override
		             public void run() {
				    	 try
				 	    {
				 	    	Thread.currentThread();
				 	    	Thread.sleep(2000);//毫秒
				 	    }
				 	    catch(Exception e){} 
		             }
		         });
		     newThread.start(); //启动线程
	    
	    Intent intent  = new Intent(LoadActivity.this, MainActivity.class);
	    startActivity(intent);
	}

}
