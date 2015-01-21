package com.pan.banzai;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class DashboardFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_dashboard, container,
				false);

		// add the adapter to the expandable list view
		ExpandableListView serverStatusList = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		serverStatusList.setAdapter(new ServerStatusExpandableListAdapter(
				getActivity(), getData()));

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private ArrayList<ServerTierStatus> getData() {
		ArrayList<ServerTierStatus> statuses = new ArrayList<ServerTierStatus>();

		ArrayList<Integer> values = new ArrayList<Integer>();
		values.add(420);
		values.add(3384);
		values.add(21);
		values.add(35896);
		ArrayList<Integer> values2 = new ArrayList<Integer>();
		values2.add(9);
		values2.add(99);
		values2.add(999);
		values2.add(9999);
		ArrayList<Integer> values3 = new ArrayList<Integer>();
		values3.add(1);
		values3.add(11);
		values3.add(111);
		values3.add(1111);
		statuses.add(new ServerTierStatus("App Tier", 95, 80, 50, values));
		statuses.add(new ServerTierStatus("Web Tier", 50, 80, 95, values2));
		statuses.add(new ServerTierStatus("Data Tier", 50, 50, 50, values3));

		return statuses;
	}
}
