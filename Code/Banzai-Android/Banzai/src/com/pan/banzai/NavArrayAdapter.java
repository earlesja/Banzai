package com.pan.banzai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NavArrayAdapter extends ArrayAdapter<String> {

	public NavArrayAdapter(Context context, String[] objects) {
		super(context, R.layout.nav_row_layout, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View navItem = null;
		if (convertView == null) {
			navItem = LayoutInflater.from(getContext()).inflate(
					R.layout.nav_row_layout, null);
		} else {
			navItem = convertView;
		}

		((TextView) navItem.findViewById(R.id.navItemTitle))
				.setText(getItem(position));

		return navItem;
	}
}
