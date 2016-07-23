package  com.viewpager.assignment.service;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpager.assignment.bean.ResultDetails;
import com.viewpager.assignment.util.HttpUtil;

public class DataServiceImp implements DataService {

	@Override
	public ArrayList<ResultDetails> getResponseFromServer(String url)
			throws Exception {
		try {
			String result = new HttpUtil().doGet(url);
			Type classType = new TypeToken<ArrayList<ResultDetails>>() {
			}.getType();
			ArrayList<ResultDetails> response = new ArrayList<ResultDetails>();
			response = new Gson().fromJson(result.trim(), classType);
			return response;
		} catch (Exception e) {
			return null;
		}
	}
}
