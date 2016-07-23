package com.viewpager.assignment.parent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected BaseActivity mActivity;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initView();
		initMember();
		initData();
		initListner();
	}

	// dialog while fetching data
	public void showProgressDialog(Activity mActivity, String message) {
		if (progressDialog == null)
			progressDialog = new ProgressDialog(mActivity);
		else if (progressDialog.isShowing())
			progressDialog.dismiss();
		progressDialog.setTitle("Processing");
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	// dismiss dialog after data is fetched
	public void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	private void initMember() {

		mActivity = BaseActivity.this;
	}

	private void initListner() {

	}

	private void initData() {

	}

	private void initView() {

	}

}
