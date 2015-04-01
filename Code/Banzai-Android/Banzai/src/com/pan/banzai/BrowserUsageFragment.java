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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import android.widget.ProgressBar;

import com.pan.banzai.apirequests.HistoricalDataTask;
import com.pan.banzai.apirequests.IGetTaskCallback;


public class BrowserUsageFragment extends Fragment {
	private static final List<Integer> IE_METRIC_IDS = Arrays
			.asList(new Integer[] { 6, 16, 36 });
	private static final List<Integer> FIREFOX_METRIC_IDS = Arrays
			.asList(new Integer[] { 7, 17, 37 });
	private static final List<Integer> CHROME_METRIC_IDS = Arrays
			.asList(new Integer[] { 8, 18, 37 });
	private static final List<Integer> MOBILE_CHROME_METRIC_IDS = Arrays
			.asList(new Integer[] { 29, 36, 45 });
	private static final List<Integer> SAFARI_METRIC_IDS = Arrays
			.asList(new Integer[] { 27, 34, 43 });
	private static final List<Integer> MOBILE_SAFARI_METRIC_IDS = Arrays
			.asList(new Integer[] { 28, 35, 44 });
	private static final List<Integer> OTHER_BROWSER_METRIC_IDS = Arrays
			.asList(new Integer[] { 46, 47, 48 });
	private static final List<Integer> IE_IIX_METRIC_IDS = Arrays
			.asList(new Integer[] { 23, 30, 39 });
	private static final List<Integer> IE_IX_METRIC_IDS = Arrays
			.asList(new Integer[] { 24, 31, 40 });
	private static final List<Integer> IE_X_METRIC_IDS = Arrays
			.asList(new Integer[] { 25, 32, 41 });
	private static final List<Integer> IE_XI_METRIC_IDS = Arrays
			.asList(new Integer[] { 26, 33, 42 });

	private UsagePieGraph mPieChart;
	private ProgressBar mPieProgress;
	private BanzaiLineGraph mLineChart;

	private static float FIREFOX_USAGE=15f;
	private static float CHROME_USAGE=30f;
	private static float IE_USAGE=55f;

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
		View view = inflater.inflate(R.layout.fragment_browserusage, container,
				false);

		mPieChart = (UsagePieGraph) view
				.findViewById(R.id.browser_current_pie_chart);
		mPieProgress = (ProgressBar) view
				.findViewById(R.id.browser_pie_progress);

		mLineChart = (BanzaiLineGraph) view
				.findViewById(R.id.browser_historic_line_chart);
		mLineProgress = (ProgressBar) view
				.findViewById(R.id.browser_line_progress);
		getAndSetChartData();

		return view;
	}

	// TODO get actual data
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
						// change to
						if (CHROME_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Chrome";
						} else if (MOBILE_CHROME_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Mobile Chrome";
						} else if (FIREFOX_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Firefox";
						} else if (IE_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "IE";
						} else if (IE_IIX_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "IE 8";
						} else if (IE_IX_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "IE 9";
						} else if (IE_X_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "IE 10";
						} else if (IE_XI_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "IE 11";
						} else if (SAFARI_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Safari";
						} else if (MOBILE_SAFARI_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Mobile Safari";
						} else if (OTHER_BROWSER_METRIC_IDS.contains(metricId)) {
							dataSetTitle = "Other Browser";
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
		tmp.addAll(CHROME_METRIC_IDS);
		tmp.addAll(FIREFOX_METRIC_IDS);
		tmp.addAll(IE_METRIC_IDS);
		tmp.addAll(IE_IIX_METRIC_IDS);
		tmp.addAll(IE_X_METRIC_IDS);
		tmp.addAll(IE_IX_METRIC_IDS);
		tmp.addAll(IE_XI_METRIC_IDS);
		tmp.addAll(MOBILE_CHROME_METRIC_IDS);
		tmp.addAll(MOBILE_SAFARI_METRIC_IDS);
		tmp.addAll(OTHER_BROWSER_METRIC_IDS);
		tmp.addAll(SAFARI_METRIC_IDS);

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

	public void updateContent(ArrayList<String> data) {
		
//		Toast.makeText(this.getActivity(),
//			 mPieChart.getData().getDataSet().getEntryForXIndex(0).getVal() +"\n"+
//			 mPieChart.getData().getDataSet().getEntryForXIndex(1).getVal() +"\n"+
//			 mPieChart.getData().getDataSet().getEntryForXIndex(2).getVal() +"\n",
//			 Toast.LENGTH_LONG).show();
//		
		
		int metricId = Integer.parseInt(data.get(0)); 
		float value = Float.parseFloat(data.get(1));
		
		if(metricId == 25){
			//IE
			this.IE_USAGE = value;
		}else if(metricId == 17){
			//firefox
			this.FIREFOX_USAGE = value;
		}else if(metricId == 18){
			//chrome
			this.CHROME_USAGE = value;
		}
		
		
		float total = this.IE_USAGE + this.FIREFOX_USAGE + this.CHROME_USAGE;

		mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal((this.CHROME_USAGE/total)*100);
		mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal((this.IE_USAGE/total)*100);
		mPieChart.getData().getDataSet().getEntryForXIndex(2).setVal((this.FIREFOX_USAGE/total)*100);
		
		mPieChart.notifyDataSetChanged();
		mPieChart.invalidate();
	}

}
