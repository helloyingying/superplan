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

		Handler x = new Handler();// ����һ��handle����
		// ����3�����ӳ�ִ��splashhandler�̡߳���ʵ������������½�һ���߳�ȥִ�г�ʼ�����������ж�SD,����״̬��
		x.postDelayed(new splashhandler(), 2000);
/*
		// �˴�ʵ�ֶ�ȡ���ݿ�Ĳ���
		Thread newThread; // ����һ�����߳�
		newThread = new Thread(new Runnable() {
			@Override
			public void run() {
			}
		});
		newThread.start(); // �����߳�
*/
	}

	class splashhandler implements Runnable {
		public void run() {
			startActivity(new Intent(getApplication(), MainActivity.class));// ����̵߳�����3�����ǽ��뵽���������
			LoadActivity.this.finish();// �ѵ�ǰ��LaunchActivity������
		}
	}
}
