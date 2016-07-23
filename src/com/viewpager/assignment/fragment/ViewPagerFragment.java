package com.viewpager.assignment.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpager.assignment.activity.R;
import com.viewpager.assignment.bean.ResultDetails;

public class ViewPagerFragment extends Fragment {

	private ResultDetails data;
	private View view;
	private TextView title;
	private TextView author;
	private TextView desc;

	public ViewPagerFragment() {
	}

	public ViewPagerFragment(ResultDetails data) {
		this.data = data;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.item_pager, null);
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();

	}

	private void init() {
		initViews();
		initData();

	}

	/**
	 * Initialise View based on Id
	 * 
	 * @param null
	 * 
	 */
	private void initViews() {
		title = (TextView) view.findViewById(R.id.title);
		author = (TextView) view.findViewById(R.id.author);
		desc = (TextView) view.findViewById(R.id.desc);
	}

	/**
	 * set data in view
	 * 
	 * @param null
	 * 
	 */
	private void initData() {
		title.setText(data.getTitle());
		desc.setText("Description:\n" + data.getBody());
		author.setText("Author: " + data.getUser().getLogin());
	}

}
