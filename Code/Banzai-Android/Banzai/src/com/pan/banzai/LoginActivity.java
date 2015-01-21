package com.pan.banzai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.dashboard, menu);

		final Context context = this;

		Button button = (Button) findViewById(R.id.signinSubmitButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
			}
		});

		EditText e1 = (EditText) findViewById(R.id.signinEmail);
		e1.requestFocus();

		return true;
	}
}
