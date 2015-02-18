package com.pan.banzai;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AsyncGetRequest extends AsyncTask<String, Void, JSONObject> {

	public static final String sUrlBase = "http://pan-banzai.cloudapp.net/banzai/api/data/";
	private String mCall;
	private String mSearchId;

	public AsyncGetRequest(String call) {
		this(call, "");
	}

	public AsyncGetRequest(String call, String searchId) {
		mCall = call;
		mSearchId = searchId;
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		DefaultHttpClient httpclient = new DefaultHttpClient(
				new BasicHttpParams());
		HttpPost httppost = new HttpPost(sUrlBase + mCall + "/" + mSearchId);
		// Depends on your web service
		httppost.setHeader("Content-type", "application/json");

		InputStream inputStream = null;
		JSONObject json = null;
		try {

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			inputStream = entity.getContent();
			// json is UTF-8 by default
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			String result = sb.toString();
			json = new JSONObject(result);
		} catch (Exception e) {
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e) {
			}
		}
		return json;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
