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
//	private static final List<Integer> IE_METRIC_IDS = Arrays
//			.asList(new Integer[] { 6, 16});
	private static final List<Integer> FIREFOX_METRIC_IDS = Arrays
			.asList(new Integer[] { 7, 17, 37 });
	private static final List<Integer> CHROME_METRIC_IDS = Arrays
			.asList(new Integer[] { 8, 18, 38 });
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

	private HashMap<String, List<Integer>> metricIds = new HashMap<String, List<Integer>>();
	private HashMap<Integer, Float> currentMetricValues = new HashMap<Integer, Float>();

	private ProgressBar mLineProgress;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		int[] allMetrics = getAllMetricIds();
		for(int i=0; i<allMetrics.length; i++){
			this.currentMetricValues.put(allMetrics[i], 0f);
		}
		
		metricIds.put("Firefox", this.FIREFOX_METRIC_IDS);
		metricIds.put("Chrome", this.CHROME_METRIC_IDS);
		metricIds.put("Mobile Chrome", this.MOBILE_CHROME_METRIC_IDS);
		metricIds.put("Safari", this.SAFARI_METRIC_IDS);
		metricIds.put("Mobile Safari", this.MOBILE_SAFARI_METRIC_IDS);
		metricIds.put("Other Browser", this.OTHER_BROWSER_METRIC_IDS);
		metricIds.put("IE 8", this.IE_IIX_METRIC_IDS);
		metricIds.put("IE 9", this.IE_IX_METRIC_IDS);
		metricIds.put("IE 10", this.IE_X_METRIC_IDS);
		metricIds.put("IE 11", this.IE_XI_METRIC_IDS);
		

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
//						} else if (IE_METRIC_IDS.contains(metricId)) {
//							dataSetTitle = "IE";
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
//		tmp.addAll(IE_METRIC_IDS);
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
		
//		 Toast.makeText(this.getActivity(),
//		 mPieChart.getData().getDataSet().getEntryForXIndex(0).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(1).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(2).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(3).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(4).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(5).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(6).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(7).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(8).getVal() +"\n"
//		 +mPieChart.getData().getDataSet().getEntryForXIndex(9).getVal() +"\n"
//		 ,Toast.LENGTH_LONG).show();
		
		
		int metricId = Integer.parseInt(data.get(0)); 
		float value = Float.parseFloat(data.get(1));
		
		if(!this.currentMetricValues.containsKey(metricId)){
			return;
		}
		
		this.currentMetricValues.put(metricId, value);
		
		HashMap<String, Float> metricAverages = new HashMap<String, Float>();
		Float total = 0f;
		
		for(String group : this.metricIds.keySet()){
			Float average = 0f;
			List<Integer> currentGroup = this.metricIds.get(group);
			for(Integer id: currentGroup){
				average += this.currentMetricValues.get(id);
			}
			average /= currentGroup.size();
			total += average;
			metricAverages.put(group, average);
		}
		
		/* TODO: Make setting values generic instead of being done manually.
		 * For some reason these are the indexes used by the graph
		 * 
		 * index 0 = mobile chrome
		 * index 1 = firefox
		 * index 2 = mobile safari
		 * index 3 = other browser
		 * index 4 = ie 10
		 * index 5 = ie 8
		 * index 6 = safari
		 * index 7 = chrome
		 * index 8 = ie 9
		 * index 9 = ie 11
		 */
		
		this.mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal(metricAverages.get("Mobile Chrome")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal(metricAverages.get("Firefox")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(2).setVal(metricAverages.get("Mobile Safari")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(3).setVal(metricAverages.get("Other Browser")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(4).setVal(metricAverages.get("IE 10")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(5).setVal(metricAverages.get("IE 8")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(6).setVal(metricAverages.get("Safari")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(7).setVal(metricAverages.get("Chrome")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(8).setVal(metricAverages.get("IE 9")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(9).setVal(metricAverages.get("IE 11")/total*100);
		
		mPieChart.notifyDataSetChanged();
		mPieChart.invalidate();
	}

}
