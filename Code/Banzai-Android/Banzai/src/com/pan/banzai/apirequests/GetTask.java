package com.pan.banzai.apirequests;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import com.pan.banzai.Storage;

import android.os.AsyncTask;
import android.util.Log;

public class GetTask extends AsyncTask<Void, Void, JSONArray> implements
		IApiTask {

	public static final String WIDGET_METRIC = "WidgetMetric";
	public static final String DIM_METRIC = "DimMetric";
	public static final String APPLICATION = "BanzaiApplication";
	public static final String ENVIRONMENT = "BanzaiEnvironment";

	private String request;
	private String id;
	private IGetTaskCallback callback;

	public GetTask() {
		this(DIM_METRIC);
	}

	public GetTask(String request) {
		this(request, "");
	}

	public GetTask(String request, IGetTaskCallback callback) {
		this(request, null, callback);
	}

	public GetTask(String request, String id) {
		this(request, id, null);
	}

	public GetTask(String request, String id, IGetTaskCallback callback) {
		this.request = request;
		this.id = id;
		this.callback = callback;
	}

	public String getUrl() {
		String url = URL_BASE + "/" + this.request;
		if (!id.isEmpty()) {
			url += "/" + id;
		}

		return url;
	}

	@Override
	protected JSONArray doInBackground(Void... arg0) {
		DefaultHttpClient httpclient = new DefaultHttpClient(
				new BasicHttpParams());
		// setup get request
		HttpGet httpget = new HttpGet(getUrl());
		ResponseHandler<String> handler = new BasicResponseHandler();

		// TODO replace with actual authorization
		httpget.setHeader("Authorization", "Basic " + Storage.getAuthentication());
		httpget.setHeader("Content-type", "application/json");

		JSONArray json = new JSONArray();
		try {
			String response = httpclient.execute(httpget, handler);
			// Log.d("DDD", response);
			json = new JSONArray(response);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("ERROR-MSG", e.getMessage());
		}

		return json;
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);

		for (int i = 0; i < result.length(); i++) {
			try {
			} catch (Exception e) {
				Log.e("ERROR", "Position " + i);
				e.printStackTrace();
			}
		}

		if (callback != null) {
			callback.execute(result);
		}
	}

}
