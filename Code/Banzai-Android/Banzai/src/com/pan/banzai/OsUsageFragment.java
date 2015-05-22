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
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;

import com.pan.banzai.TimeFrameChooser.Callback;
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
	
	private HashMap<String, List<Integer>> metricIds = new HashMap<String, List<Integer>>();
	private HashMap<Integer, Float> currentMetricValues = new HashMap<Integer, Float>();

	private ProgressBar mLineProgress;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int[] allMetrics = OsUsageFragment.getAllMetricIds();
		for(int i=0; i<allMetrics.length; i++){
			this.currentMetricValues.put(allMetrics[i], 0f);
		}
		
		metricIds.put("Windows Vista", WINDOWS_VISTA_METRIC_IDS);
		metricIds.put("Windows 7", WINDOWS_VII_METRIC_IDS);
		metricIds.put("Windows 8", WINDOWS_IIX_METRIC_IDS);
		metricIds.put("Windows 8.1", WINDOWS_IIX_I_METRIC_IDS);
		metricIds.put("Windows 10", WINDOWS_X_METRIC_IDS);
		metricIds.put("OSX", OSX_METRIC_IDS);
		metricIds.put("Linux", LINUX_METRIC_IDS);
		metricIds.put("iOS", IOS_METRIC_IDS);
		metricIds.put("Android", ANDROID_METRIC_IDS);
		metricIds.put("Other OS", OTHER_OS_METRIC_IDS);

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
		
		
		
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity().getActionBar().getThemedContext(), R.array.time_frames, android.R.layout.simple_spinner_dropdown_item);
		TimeFrameChooser mSpinnerCallback = new TimeFrameChooser(new Callback() {			
			@Override
			public void process(int timeframe, String groupBy, DateFormat formatter) {
				setSpinnerVisibility(false);
				OsUsageFragment.this.getAndSetChartData(timeframe, groupBy, formatter);						
			}
		});
		
		ActionBar actions = getActivity().getActionBar();
		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setListNavigationCallbacks(mSpinnerAdapter, mSpinnerCallback);
		
		return view;
	}

	private void getAndSetChartData(final int timeframe, String groupBy, final DateFormat dateFormatter) {
		final long now = new Date().getTime();
		new HistoricalDataTask(timeframe, groupBy, getAllMetricIds(), new IGetTaskCallback() {
			@Override
			public void execute(JSONArray json) {
				HashMap<String, ArrayList<Float>> map = new HashMap<String, ArrayList<Float>>();

				ArrayList<Date> times = new ArrayList<Date>();
				DateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");

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

						Date date = formatter.parse(j
								.getString("DateCapturedUtc"));
						if((now - date.getTime()) > timeframe * 1000){
							continue;
						}
						
						if (!times.contains(date)) {
							times.add(date);
						}

						if (map.get(dataSetTitle) == null) {
							map.put(dataSetTitle, new ArrayList<Float>());
						}
						map.get(dataSetTitle).add(value);

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
				mLineChart.setData(map, times.toArray(new Date[times.size()]), dateFormatter);
				
				setSpinnerVisibility(true);
			}
		}).execute();

	}
	
	public void setSpinnerVisibility(boolean isHidden){
		mPieProgress.setVisibility(isHidden ? View.GONE : View.VISIBLE);
		mPieChart.setVisibility(isHidden ? View.VISIBLE : View.GONE);

		mLineProgress.setVisibility(isHidden ? View.GONE : View.VISIBLE);
		mLineChart.setVisibility(isHidden ? View.VISIBLE : View.GONE);
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

	public void updateContent(ArrayList<String> data) {	
		
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
		
		
//		for(String group : metricAverages.keySet()){
//			this.mPieChart.getDataSetByLabel(group).getEntryForXIndex(0).setVal(metricAverages.get(group)/total);
//		}
		
		/* TODO: Make setting values generic instead of being done manually.
		 * For some reason these are the indexes used by the graph
		 * 
		 * index 0 = linux
		 * index 1 = windows 10
		 * index 2 = android
		 * index 3 = windows vista
		 * index 4 = other os
		 * index 5 = windows 8
		 * index 6 = osx
		 * index 7 = windows 7
		 * index 8 = ios
		 * index 9 = windows 8.1
		 */

		this.mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal(metricAverages.get("Linux")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal(metricAverages.get("Windows 10")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(2).setVal(metricAverages.get("Android")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(3).setVal(metricAverages.get("Windows Vista")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(4).setVal(metricAverages.get("Other OS")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(5).setVal(metricAverages.get("Windows 8")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(6).setVal(metricAverages.get("OSX")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(7).setVal(metricAverages.get("Windows 7")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(8).setVal(metricAverages.get("iOS")/total*100);
		this.mPieChart.getData().getDataSet().getEntryForXIndex(9).setVal(metricAverages.get("Windows 8.1")/total*100);

		
		this.mPieChart.notifyDataSetChanged();
		this.mPieChart.invalidate();
	}

}
