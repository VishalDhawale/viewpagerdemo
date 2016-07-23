package com.viewpager.assignment.service;

import java.util.ArrayList;

import com.viewpager.assignment.bean.ResultDetails;

public interface DataService {
	public ArrayList<ResultDetails> getResponseFromServer(String url)
			throws Exception;
}
