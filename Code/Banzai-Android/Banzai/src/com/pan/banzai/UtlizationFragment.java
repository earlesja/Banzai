package com.pan.banzai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.utils.LimitLine;
import com.pan.banzai.apirequests.HistoricalDataTask;
import com.pan.banzai.apirequests.IGetTaskCallback;

public class UtlizationFragment extends Fragment {

	public static enum UtilizationType {
		CPU, RAM, DISK
	}

	private BanzaiLineGraph mLineChart;
	private ProgressBar mLineProgress;

	private ServerTier mTier;
	private UtilizationType mType;

	public UtlizationFragment(ServerTier tier, UtilizationType type) {
		mTier = tier;
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

		((TextView) view.findViewById(R.id.utilization_breakdown_title))
				.setText(getString(R.string.utilization_breakdown_title,
						getTitle()));

		mLineChart = (BanzaiLineGraph) view
				.findViewById(R.id.utilization_breakdown_graph);
		mLineProgress = (ProgressBar) view
				.findViewById(R.id.utilization_line_progress);

		getAndSetData();
		return view;
	}

	private void getAndSetData() {
		new HistoricalDataTask(getMetricIds(), new IGetTaskCallback() {
			@Override
			public void execute(JSONArray json) {
				HashMap<String, ArrayList<Float>> map = new HashMap<String, ArrayList<Float>>();

				ArrayList<Date> times = new ArrayList<Date>();
				DateFormat formatter = new SimpleDateFormat(
						"yyy-MM-DD'T'HH:mm:ss");

				for (int i = 0; i < json.length(); i++) {
					try {
						JSONObject j = (JSONObject) json.get(i);
						float value = (float) j.getDouble("Value");
						int metricId = j.getInt("MetricId");
						String dataSetTitle = getServerName(metricId);

						if (map.get(dataSetTitle) == null) {
							map.put(dataSetTitle, new ArrayList<Float>());
						}
						map.get(dataSetTitle).add(value);
						Date date = formatter.parse(j
								.getString("DateCapturedUtc"));
						if (!times.contains(date)) {
							times.add(date);
						}

					} catch (Exception e) {

					}
				}
				mLineChart.setData(map, times.toArray(new Date[times.size()]));
				setThresholdLines();
				mLineProgress.setVisibility(View.GONE);
				mLineChart.setVisibility(View.VISIBLE);
			}
		}).execute();
	}

	private void setThresholdLines() {
		LimitLine critical = null;
		LimitLine warning = null;
		switch (mType) {
		case CPU:
			critical = new LimitLine(DefaultValues.getCpuCriticalThreshold());
			warning = new LimitLine(DefaultValues.getCpuWarningThreshold());
			break;
		case RAM:
			critical = new LimitLine(DefaultValues.getRamCriticalThreshold());
			warning = new LimitLine(DefaultValues.getRamWarningThreshold());
			break;
		case DISK:
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

		mLineChart.getData().addLimitLine(critical);
		mLineChart.getData().addLimitLine(warning);
	}

	private String getServerName(int metricId) {
		return mTier.getServerNames()[Arrays.binarySearch(getMetricIds(),
				metricId)];
	}

	private int[] getMetricIds() {
		switch (mType) {
		case CPU:
			return mTier.getCpuMetricIds();
		case RAM:
			return mTier.getRamMetricIds();
		case DISK:
			return mTier.getDiskMetricIds();
		default:
			return new int[] {};
		}
	}

	private String getTitle() {
		switch (mType) {
		case CPU:
			return "CPU";
		case RAM:
			return "Memory";
		case DISK:
			return "Disk";
		default:
			return "";
		}
	}
}
