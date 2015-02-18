package com.pan.banzai;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UtlizationFragment extends Fragment {
	private BanzaiLineGraph mAgregateChart;
	private BanzaiLineGraph mBreakdownChart;
	private String mTitle;

	public UtlizationFragment(String title) {
		mTitle = title;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_utilization, container,
				false);

		((TextView) view.findViewById(R.id.utilization_aggregate_title))
				.setText(getString(R.string.utilization_aggregate_title, mTitle));
		((TextView) view.findViewById(R.id.utilization_breakdown_title))
				.setText(getString(R.string.utilization_breakdown_title, mTitle));

		mAgregateChart = (BanzaiLineGraph) view
				.findViewById(R.id.utilization_aggregate_graph);

		mBreakdownChart = (BanzaiLineGraph) view
				.findViewById(R.id.utilization_breakdown_graph);

		return view;
	}
}
