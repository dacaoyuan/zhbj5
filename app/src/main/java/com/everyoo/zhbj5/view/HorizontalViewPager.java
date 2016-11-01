package com.everyoo.zhbj5.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 11个子页签水平滑动的Viewpager, 暂时不用
 * 
 * @author Kevin
 * 
 */
public class HorizontalViewPager extends ViewPager {

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {
		super(context);
	}

	/**
	 * 事件分发, 请求父控件及祖宗控件是否拦截事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getCurrentItem() != 0) {
			getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求,
																	// 不拦截
		} else {// 如果是第一个页面,需要显示侧边栏, 请求父控件拦截
			getParent().requestDisallowInterceptTouchEvent(false);// 拦截
		}
		return super.dispatchTouchEvent(ev);
	}

}
