package com.pan.banzai.apirequests;

import java.util.List;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class HistoricalDataTask extends AsyncTask<Void, Void, JSONArray>
		implements IApiTask {

	public static final String HISTORICAL_DATA = "HistoricalData";
	private int timeframe;
	private String groupBy;
	private int[] metricIds;
	private IGetTaskCallback callback;


	public HistoricalDataTask(int timeframe, int[] metricIds) {
		this(timeframe, "Hour", metricIds, null);
	}

	public HistoricalDataTask(int timeframe, String groupBy, int[] is,
			IGetTaskCallback callback) {
		this.timeframe = timeframe;
		this.groupBy = groupBy;
		this.metricIds = is;
		this.callback = callback;
		Log.d("HHH", getJSONData());
	}

	public String getUrl() {
		return URL_BASE + "/" + HISTORICAL_DATA;
	}

	@Override
	protected JSONArray doInBackground(Void... arg0) {
		JSONArray json = new JSONArray();
		
		DefaultHttpClient httpclient = new DefaultHttpClient(
				new BasicHttpParams());
		// setup get request
		HttpPost httppost = new HttpPost(getUrl());
		ResponseHandler<String> handler = new BasicResponseHandler();

		// TODO replace with actual authorization
		httppost.setHeader("Authorization", "Basic dXNlck5hbWU6cGFzc3dvcmQ=");
		httppost.setHeader("Content-type", "application/json");
		try {
			httppost.setEntity(new StringEntity(getJSONData()));
			String response = httpclient.execute(httppost, handler);
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

		if (callback != null) {
			this.callback.execute(result);
		}
	}

	private String getJSONData() {
		JSONObject json = new JSONObject();
		try {
			json.put("GroupBy", this.groupBy);
			json.put("TimeFrame", this.timeframe);
			JSONArray widgetMetrics = new JSONArray();
			for (int metricId : this.metricIds) {
				widgetMetrics
						.put(new JSONObject("{MetricId:" + metricId + "}"));
			}
			json.put("WidgetMetrics", widgetMetrics);
		} catch (Exception e) {
			json = new JSONObject();
		}

		return json.toString();
	}

}
