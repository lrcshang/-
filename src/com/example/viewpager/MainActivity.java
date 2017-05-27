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
		// ��ʼ
		initview();
		initData();
		initAdapter();
		
		//������ѭ
		new Thread(){public void run() {
			isUIVisable=true;
			while (isUIVisable) {
				try {
					Thread.sleep(2000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//������һλ
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
		viewPager.setOnPageChangeListener(this);// ���ù�������

	}

	private void initData() {
		int[] imageResIds = new int[] { R.drawable.a, R.drawable.c, R.drawable.b,
				R.drawable.b, R.drawable.a };

		desc = new String[] { "ffdfd", "dasfgerf", "dwsdsdsd", "���ķ������", "�󷽵�˵��������" };
		// ��ʼ��Ҫչʾ��5��ImageView
		ImageView imageView;
		LayoutParams layoutParams;
		imageViewlist = new ArrayList<ImageView>();
		for (int i = 0; i < imageResIds.length; i++) {
			imageView = new ImageView(this);
			imageView.setBackgroundResource(imageResIds[i]);
			imageViewlist.add(imageView);
			// ��С�׵�ָʾ��
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
		//Ĭ�������м��ĳ��λ��   ��������ѭ��
		viewPager.setCurrentItem(5000000);
		linearLayout.getChildAt(0).setEnabled(true);
		text.setText(desc[0]);
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			//��������ѭ��
			return Integer.MAX_VALUE;
		}

		// 3.ָ�����õ��ж��߼����̶�д��
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// �������µ���Ŀ���з�������view�Ƿ���Ա����ã�
			// �����жϹ���
			return view == object;
		}

		// 1. ����Ҫ��ʾ����Ŀ����,������Ŀ
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// container ���� ��ǰViewpager position
			// position ��ǰҪ��ʾ��Ŀ��λ��
			int newposition=position%imageViewlist.size(); 
			ImageView imageView = imageViewlist.get(newposition);
			// ��View������ӵ� container��
			container.addView(imageView);
			// ��View���󷵻ظ���ܣ���������
			return imageView;// ������д�������׳��쳣
		}

		// 2. ������Ŀ
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// object Ҫ���ٵĶ���
			container.removeView((View) object);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// ����״̬�仯ʱ����

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// ����ʱ����

	}

	int lastEnablePoint = 0;

	@Override
	public void onPageSelected(int position) {
		// �µ���Ŀ��ѡ��ʱ����
		int newposition=position%imageViewlist.size(); 
		text.setText(desc[newposition]);
		// View childAt = linearLayout.getChildAt(position);
		// for (int i = 0; i < linearLayout.getChildCount(); i++) {
		// childAt.setEnabled(position==i);
		// }
		linearLayout.getChildAt(lastEnablePoint).setEnabled(false);
		linearLayout.getChildAt(newposition).setEnabled(true);
		//��¼֮ǰ��λ��
		lastEnablePoint = newposition;

	}
	@Override
		protected void onDestroy() {
			super.onDestroy();
			isUIVisable=false;
		}
}
