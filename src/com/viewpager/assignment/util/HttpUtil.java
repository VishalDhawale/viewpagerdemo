package com.viewpager.assignment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class HttpUtil {

	private static final String DEFAULT_ERROR_MSG = "Server not responding";

	public HttpUtil() {
		init();
	}

	private void init() {
	}

	/**
	 * Method will send httpGet request to server
	 * 
	 * @param <T>
	 * 
	 * @param url
	 * @param post
	 * @return
	 */
	public String doGet(String url) {

		// Log.d("URL", url);
		BasicHttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 60000);
		HttpConnectionParams.setSoTimeout(httpParameters, 60000);
		InputStream inputStream = null;
		// make GET request to the given URL
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Connection", "Keep-Alive");
		httpget.setHeader("Accept", "application/json");
		httpget.setHeader("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
		try {
			HttpClient httpClient = connectToServer(url);
			HttpResponse httpResponse = httpClient.execute(httpget);
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String json = getStringFromInputStream(inputStream);
				String errMsg = "";
				JSONObject errJson;
				try {
					errJson = new JSONObject(json);
					errMsg = errJson.getString("errMsg");
				} catch (JSONException e) {
					e.printStackTrace();

				}

				if (TextUtils.isEmpty(errMsg)
						|| errMsg.trim().equalsIgnoreCase("null"))
					errMsg = DEFAULT_ERROR_MSG;

			}
		} catch (IOException e) {
			e.printStackTrace();

		}

		// convert inputstream to string
		if (inputStream != null) {
			try {
				String result = getStringFromInputStream(inputStream);
				return result;
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		return null;
	}

	/**
	 * This Method will get the String data from Input Stream
	 * 
	 * @param is
	 * @return String
	 * @throws IOException
	 */
	private String getStringFromInputStream(InputStream is) throws IOException {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	private HttpClient connectToServer(String url) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
