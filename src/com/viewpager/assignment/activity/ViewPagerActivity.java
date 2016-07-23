package com.viewpager.assignment.activity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;

import com.viewpager.assignment.adapter.ViewPagerAdapter;
import com.viewpager.assignment.bean.ResultDetails;
import com.viewpager.assignment.fragment.ViewPagerFragment;
import com.viewpager.assignment.parent.BaseActivity;
import com.viewpager.assignment.service.DataServiceImp;
import com.viewpager.assignment.util.ApplicationConstant;
import com.viewpager.assignment.util.DateTimeUtil;

public class ViewPagerActivity extends BaseActivity {

	private ViewPager viewPager;
	private ArrayList<ResultDetails> response;
	private ConnectivityManager cm;
	private NetworkInfo netInfo;
	private ViewPagerAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		init();

	}

	private void init() {
		initView();
		initData();
		initMember();
	}

	/**
	 * Initialise View Pager
	 * 
	 * @param null
	 * 
	 */
	private void initViewPager() {
		if (response != null) {
			for (ResultDetails image : response) {
				mAdapter.addItem(new ViewPagerFragment(image));

			}
			viewPager.setAdapter(mAdapter);
		}
	}

	private void setData() {

		mAdapter = new ViewPagerAdapter(mActivity.getFragmentManager(),
				viewPager);
		sortResultDetails();
		initViewPager();

	}

	/**
	 * Sort List based on date
	 * 
	 * @param null
	 * 
	 */
	private void sortResultDetails() {
		for (int i = 0; i < response.size(); i++) {
			try {
				response.get(i).setDate(
						DateTimeUtil.convertToDate(response.get(i)
								.getUpdated_at(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(response, new Comparator<ResultDetails>() {
			public int compare(ResultDetails o1, ResultDetails o2) {
				if (o1.getDate() == null || o2.getDate() == null)
					return 0;
				return o2.getDate().compareTo(o1.getDate());
			}
		});

	}

	/**
	 * Get List of Issues from from Server
	 * 
	 * @param null
	 * 
	 */
	public class GetDetails extends AsyncTask<Void, String, String> {
		ProgressDialog progressDialog;

		@Override
		public void onPreExecute() {
			super.onPreExecute();

			showProgressDialog(mActivity, "Fetching data ,Please Wait...");
		}

		@Override
		protected String doInBackground(Void... params) {
			if (Looper.myLooper() == null) {
				Looper.prepare();
			}
			String result = null;
			try {
				response = new DataServiceImp()
						.getResponseFromServer(ApplicationConstant.BASE_URL);
				if (response != null && response.size() > 0) {
					result = "success";
				}
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				result = "failure";
				return result;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			if (!TextUtils.isEmpty(result)
					&& result.equalsIgnoreCase("Success")) {
				setData();
			} else
				Toast.makeText(getApplicationContext(),
						"Some error occur , Please try again later.",
						Toast.LENGTH_LONG).show();

		}
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.pager);
	}

	private void initMember() {
		if (netInfo != null && netInfo.isConnected()) {
			new GetDetails().execute();
		} else {
			Toast.makeText(getApplicationContext(), "No Network Available",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Initialise Internate Connection
	 * 
	 * @param null
	 * 
	 */
	private void initData() {
		cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		netInfo = cm.getActiveNetworkInfo();

	}

}