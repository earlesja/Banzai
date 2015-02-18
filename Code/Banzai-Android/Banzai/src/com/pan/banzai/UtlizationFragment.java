package com.pan.banzai;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.utils.LimitLine;

public class UtlizationFragment extends Fragment {
	private BanzaiLineGraph mAgregateChart;
	private BanzaiLineGraph mBreakdownChart;
	private String mTitle;
	private int mType;

	public UtlizationFragment(String title, int type) {
		mTitle = title;
		mType = type;
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

	private void setThresholdLines(BanzaiLineGraph chart) {
		LimitLine critical = null;
		LimitLine warning = null;
		switch (mType) {
		case 0:
			critical = new LimitLine(DefaultValues.getCpuCriticalThreshold());
			warning = new LimitLine(DefaultValues.getCpuWarningThreshold());
			break;
		case 1:
			critical = new LimitLine(DefaultValues.getRamCriticalThreshold());
			warning = new LimitLine(DefaultValues.getRamWarningThreshold());
			break;
		case 2:
			critical = new LimitLine(
					DefaultValues.getStorageCriticalThreshold());
			warning = new LimitLine(DefaultValues.getStorageWarningThreshold());
			break;
		default:
			critical = new LimitLine(DefaultValues.getCpuCriticalThreshold());
			warning = new LimitLine(DefaultValues.getCpuWarningThreshold());
			break;

		}
		critical.setLineColor(getResources().getColor(R.color.critical));
		warning.setLineColor(getResources().getColor(R.color.warning));

		chart.getData().addLimitLine(critical);
		chart.getData().addLimitLine(warning);
	}
}
