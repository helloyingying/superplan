package com.liu.planplus.toolClass;

import com.liu.planplus.activity.MainActivity;
import com.liu.planplus.activity.MoreActivity;

import android.app.Activity;
import android.view.animation.Animation;

public class DisplayNextView implements Animation.AnimationListener {

	Object obj;
	Activity ac;
	int order;

	public DisplayNextView(Activity ac, int order) {
		this.ac = ac;
		this.order = order;
	}

	public void onAnimationStart(Animation animation) {
	}

	public void onAnimationEnd(Animation animation) {
		doSomethingOnEnd(order);
	}

	public void onAnimationRepeat(Animation animation) {
	}

	private final class SwapViews implements Runnable {
		public void run() {
			switch (order) {
			case Constants.KEY_FIRST_INVERSE:
				((MainActivity) ac).jumpToMore();
				break;
			case Constants.KEY_SECOND_CLOCKWISE:
				((MoreActivity)ac).jumpToMain();
				break;
			}
		}
	}

	public void doSomethingOnEnd(int _order) {
		switch (_order) {
		case Constants.KEY_FIRST_INVERSE:
			((MainActivity) ac).layout_main.post(new SwapViews());
			break;

		case Constants.KEY_SECOND_CLOCKWISE:
			((MoreActivity)ac).layout_more.post(new SwapViews());
			break;
		}
	}
}
