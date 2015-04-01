package com.pan.banzai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pan.banzai.apirequests.HistoricalDataTask;
import com.pan.banzai.apirequests.IGetTaskCallback;

public class OsUsageFragment extends Fragment {
	private static final List<Integer> WINDOWS_VISTA_METRIC_IDS = Arrays
			.asList(new Integer[] { 49, 50, 51 });
	private static final List<Integer> WINDOWS_VII_METRIC_IDS = Arrays
			.asList(new Integer[] { 52, 53, 54 });
	private static final List<Integer> WINDOWS_IIX_METRIC_IDS = Arrays
			.asList(new Integer[] { 55, 56, 57 });
	private static final List<Integer> WINDOWS_IIX_I_METRIC_IDS = Arrays
			.asList(new Integer[] { 58, 59, 60 });
	private static final List<Integer> WINDOWS_X_METRIC_IDS = Arrays
			.asList(new Integer[] { 61, 62, 63 });
	private static final List<Integer> OSX_METRIC_IDS = Arrays
			.asList(new Integer[] { 64, 65, 66 });
	private static final List<Integer> LINUX_METRIC_IDS = Arrays
			.asList(new Integer[] { 67, 68, 69 });
	private static final List<Integer> IOS_METRIC_IDS = Arrays
			.asList(new Integer[] { 70, 71, 72 });
	private static final List<Integer> ANDROID_METRIC_IDS = Arrays
			.asList(new Integer[] { 73, 74, 75 });
	private static final List<Integer> OTHER_OS_METRIC_IDS = Arrays
			.asList(new Integer[] { 76, 77, 78 });

	private UsagePieGraph mPieChart;
	private ProgressBar mPieProgress;
	private BanzaiLineGraph mLineChart;
	private ProgressBar mLineProgress;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_osusage, container,
				false);

		mPieChart = (UsagePieGraph) view
				.findViewById(R.id.os_current_pie_chart);
		mPieProgress = (ProgressBar) view.findViewById(R.id.os_pie_progress);

		mLineChart = (BanzaiLineGraph) view
				.findViewById(R.id.os_historic_line_chart);
		mLineProgress = (ProgressBar) view.findViewById(R.id.os_line_progress);
		
		getAndSetChartData();
		return view;
	}

	// TODO optimize. slower than turtle in molasses
	private void getAndSetChartData() {
		new HistoricalDataTask(getAllMetricIds(), new IGetTaskCallback() {
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
						String dataSetTitle = "";

						if (WINDOWS_VISTA_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Windows Vista";
						} else if (WINDOWS_VII_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Windows 7";
						} else if (WINDOWS_IIX_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Windows 8";
						} else if (WINDOWS_IIX_I_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Windows 8.1";
						} else if (WINDOWS_X_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Windows 10";
						} else if (OSX_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "OSX";
						} else if (LINUX_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Linux";
						} else if (IOS_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "iOS";
						} else if (ANDROID_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Android";
						} else if (OTHER_OS_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Other OS";
						}

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

				// average everything
				float[] totals = new float[times.size()];
				for (String key : map.keySet()) {
					ArrayList<Float> averaged = new ArrayList<Float>();
					ArrayList<Float> curr = map.get(key);
					for (int i = 0; i < curr.size(); i += 3) {
						averaged.add((curr.get(i + 0) + curr.get(i + 1) + curr
								.get(i + 2)) / 3);
						totals[i / 3] = totals[i / 3] + averaged.get(i / 3);
					}
					map.put(key, averaged);
				}

				// turn everything into a percent
				for (String key : map.keySet()) {
					for (int i = 0; i < map.get(key).size(); i++) {
						map.get(key).set(i,
								(map.get(key).get(i) / totals[i]) * 100);
					}
				}

				HashMap<String, Float> pieData = new HashMap<String, Float>();
				for (String key : map.keySet()) {
					pieData.put(key, map.get(key).get(map.get(key).size() - 1));
				}

				mPieChart.setData(pieData);
				mPieProgress.setVisibility(View.GONE);
				mPieChart.setVisibility(View.VISIBLE);

				mLineChart.setData(map, times.toArray(new Date[times.size()]));
				mLineProgress.setVisibility(View.GONE);
				mLineChart.setVisibility(View.VISIBLE);
			}
		}).execute();
	}

	private static int[] getAllMetricIds() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.addAll(WINDOWS_IIX_METRIC_IDS);
		tmp.addAll(WINDOWS_VII_METRIC_IDS);
		tmp.addAll(WINDOWS_VISTA_METRIC_IDS);
		tmp.addAll(IOS_METRIC_IDS);
		tmp.addAll(ANDROID_METRIC_IDS);
		tmp.addAll(OTHER_OS_METRIC_IDS);
		tmp.addAll(WINDOWS_IIX_I_METRIC_IDS);
		tmp.addAll(OSX_METRIC_IDS);
		tmp.addAll(LINUX_METRIC_IDS);
		tmp.addAll(WINDOWS_X_METRIC_IDS);

		int[] combined = new int[tmp.size()];
		for (int i = 0; i < combined.length; i++) {
			combined[i] = tmp.get(i);
		}
		return combined;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
