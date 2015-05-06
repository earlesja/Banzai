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
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.pan.banzai.TimeFrameChooser.Callback;
import com.pan.banzai.apirequests.HistoricalDataTask;
import com.pan.banzai.apirequests.IGetTaskCallback;

public class UtilizationFragment extends Fragment {

	public static enum UtilizationType {
		CPU, RAM, DISK
	}

	private BanzaiLineGraph mLineChart;
	private ProgressBar mLineProgress;

	private ServerTier mTier;
	private UtilizationType mType;

	public UtilizationFragment(ServerTier tier, UtilizationType type) {
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
		
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity().getActionBar().getThemedContext(), R.array.time_frames, android.R.layout.simple_spinner_dropdown_item);
		TimeFrameChooser mSpinnerCallback = new TimeFrameChooser(new Callback() {			
			@Override
			public void process(int timeframe, String groupBy, DateFormat formatter) {
				setSpinnerVisibility(false);
				UtilizationFragment.this.getAndSetData(timeframe, groupBy, formatter);						
			}
		});
		
		ActionBar actions = getActivity().getActionBar();
		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setListNavigationCallbacks(mSpinnerAdapter, mSpinnerCallback);
		actions.setTitle( getTitle() + " Utilization");
	
		return view;
	}

	private void getAndSetData(int timeframe, String groupBy, final DateFormat dateFormatter) {
		new HistoricalDataTask(timeframe, groupBy, getMetricIds(), new IGetTaskCallback() {
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
				mLineChart.setData(map, times.toArray(new Date[times.size()]), dateFormatter);
				setSpinnerVisibility(true);
			}
		}).execute();
	}


	public void setSpinnerVisibility(boolean isHidden){
		mLineProgress.setVisibility(isHidden ? View.GONE : View.VISIBLE);
		mLineChart.setVisibility(isHidden ? View.VISIBLE : View.GONE);
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
		String title = "";
		switch (mType) {
		case CPU:
			title = "CPU";
			break;
		case RAM:
			title = "Memory";
			break;
		case DISK:
			title = "Disk";
			break;
		}
		
		return title;
	}
}
