package com.pan.banzai;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pan.banzai.apirequests.IApiTask;

public class LoginActivity extends Activity {

	private EditText mUsernameInput;
	private EditText mPasswordInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if(!DefaultValues.getAuthentication().equals("")){
			goToDashboard();
		}
		
		mUsernameInput = (EditText) findViewById(R.id.signinEmail);
		mPasswordInput = (EditText) findViewById(R.id.signinPassword);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.dashboard, menu);


		Button button = (Button) findViewById(R.id.signinSubmitButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = mUsernameInput.getText().toString();
				String password = mPasswordInput.getText().toString();
				if (username.isEmpty()){
					mUsernameInput.setError("Required");
					return;
				} 
				if (password.isEmpty()){
					mPasswordInput.setError("Required");
					return;
				}
				
				String toEncode = username + ":"	+ password;
				String encoded = Base64.encodeToString(toEncode.getBytes(), Base64.DEFAULT);
				
				//for some reason it adds an extra character at the end, so remove it
				new LoginTask(encoded.substring(0, encoded.length()-1)).execute();
				
			
				 
			}
		});

		EditText e1 = (EditText) findViewById(R.id.signinEmail);
		e1.requestFocus();

		return true;
	}

	public void goToDashboard(){
		  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		  startActivity(intent);
	}
	
	public class LoginTask extends AsyncTask<String, Void, Boolean> implements
			IApiTask {
		public static final String DIM_METRIC = "DimMetric";

		private String authentication;

		public LoginTask(String authentication) {
			this.authentication = authentication;
		}

		@Override
		protected Boolean doInBackground(String... args) {
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			// setup get request
			HttpGet httpget = new HttpGet(getUrl());
			ResponseHandler<String> handler = new BasicResponseHandler();

			//Basic dXNlck5hbWU6cGFzc3dvcmQ=
			httpget.setHeader("Authorization", "Basic " + authentication);
			
			boolean valid = false;
			try {
				httpclient.execute(httpget, handler);
				valid = true;
			} catch (Exception e) {
				Log.e("ERROR-MSG", e.getMessage());
			}

			return valid;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result){
				  DefaultValues.putAuthentication(authentication);
				  LoginActivity.this.goToDashboard();
			}else{
				mUsernameInput.setError("Invalid");
				mPasswordInput.setError("Invalid");
			}
			
		}

		@Override
		public String getUrl() {
			return URL_BASE + DIM_METRIC;
		}
	}
}
