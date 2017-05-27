package com.example.viewpager;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener {
boolean isUIVisable=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始
		initview();
		initData();
		initAdapter();
		
		//开启轮循
		new Thread(){public void run() {
			isUIVisable=true;
			while (isUIVisable) {
				try {
					Thread.sleep(2000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//往下跳一位
				runOnUiThread(new Runnable() {
					public void run() {
						viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
					}
				});
				
			}
		};}.start();
	}

	ViewPager viewPager;
	ArrayList<ImageView> imageViewlist;
	LinearLayout linearLayout;
	View positview;
	TextView text;
	String[] desc;

	private void initview() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		linearLayout = (LinearLayout) findViewById(R.id.linear);
		text = (TextView) findViewById(R.id.textview);
		viewPager.setOnPageChangeListener(this);// 设置滚动监听

	}

	private void initData() {
		int[] imageResIds = new int[] { R.drawable.a, R.drawable.c, R.drawable.b,
				R.drawable.b, R.drawable.a };

		desc = new String[] { "ffdfd", "dasfgerf", "dwsdsdsd", "发的发的身份", "大方的说法第三方" };
		// 初始化要展示的5个ImageView
		ImageView imageView;
		LayoutParams layoutParams;
		imageViewlist = new ArrayList<ImageView>();
		for (int i = 0; i < imageResIds.length; i++) {
			imageView = new ImageView(this);
			imageView.setBackgroundResource(imageResIds[i]);
			imageViewlist.add(imageView);
			// 加小白的指示器
			positview = new View(this);
			positview.setBackgroundResource(R.drawable.selector_bg_point);

			layoutParams = new LinearLayout.LayoutParams(15, 15);
			if (i != 0) {
				layoutParams.leftMargin = 10;
			}
			positview.setEnabled(false);
			linearLayout.addView(positview, layoutParams);

		}
	}

	private void initAdapter() {
		viewPager.setAdapter(new MyAdapter());
		int pos=Integer.MAX_VALUE/2-(Integer.MAX_VALUE/2%imageViewlist.size());
		//默认设置中间的某个位置   向左无限循环
		viewPager.setCurrentItem(5000000);
		linearLayout.getChildAt(0).setEnabled(true);
		text.setText(desc[0]);
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			//向右无限循环
			return Integer.MAX_VALUE;
		}

		// 3.指定复用的判断逻辑，固定写法
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// 当滑到新的条目，有返回来，view是否可以被复用，
			// 返回判断规则
			return view == object;
		}

		// 1. 返回要显示的条目内容,创建条目
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// container 容器 当前Viewpager position
			// position 当前要显示条目的位置
			int newposition=position%imageViewlist.size(); 
			ImageView imageView = imageViewlist.get(newposition);
			// 把View对象添加到 container中
			container.addView(imageView);
			// 把View对象返回给框架（适配器）
			return imageView;// 必须重写，否则抛出异常
		}

		// 2. 销毁条目
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// object 要销毁的对象
			container.removeView((View) object);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// 滚动状态变化时调用

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// 滚动时调用

	}

	int lastEnablePoint = 0;

	@Override
	public void onPageSelected(int position) {
		// 新的条目被选中时调用
		int newposition=position%imageViewlist.size(); 
		text.setText(desc[newposition]);
		// View childAt = linearLayout.getChildAt(position);
		// for (int i = 0; i < linearLayout.getChildCount(); i++) {
		// childAt.setEnabled(position==i);
		// }
		linearLayout.getChildAt(lastEnablePoint).setEnabled(false);
		linearLayout.getChildAt(newposition).setEnabled(true);
		//记录之前的位置
		lastEnablePoint = newposition;

	}
	@Override
		protected void onDestroy() {
			super.onDestroy();
			isUIVisable=false;
		}
}
