package  com.viewpager.assignment.adapter;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ViewPagerAdapter extends FragmentPagerAdapter implements
		OnPageChangeListener {
	private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
	private ViewPager mPager;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public ViewPagerAdapter(FragmentManager fm, ViewPager pager) {
		super(fm);
		mPager = pager;
		mPager.setOnPageChangeListener(this);
	}

	public void addItem(Fragment fragment) {
		mFragments.add(fragment);
	}

	@Override
	public Fragment getItem(int arg0) {
		return mFragments.get(arg0);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {

	}

}