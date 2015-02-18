package com.pan.banzai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BrowserUsageFragment extends Fragment {
	private UsagePieGraph mPieChart;
	private BanzaiLineGraph mLineChart;

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
		setCurrentValues();

		mLineChart = (BanzaiLineGraph) view
				.findViewById(R.id.browser_historic_line_chart);
		setHistoricData();

		return view;
	}

	// TODO get actual data
	private void setCurrentValues() {
		// TODO http request?

		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("Chrome", 5f);
		map.put("Firefox", 40f);
		map.put("IE", 55f);
		// map.put("Other", 5f);

		mPieChart.setData(map);
	}

	// TODO get actual data
	private void setHistoricData() {
		// TODO http request?

		Random r = new Random();

		HashMap<String, Float[]> map = new HashMap<String, Float[]>();
		int halfHours = SharedPreferenceHelper.getIntPreference(
				DefaultValues.sGraphTimeFrameKey, 2) * 2;
		Float[] chromeTimes = new Float[halfHours];
		Float[] firefoxTimes = new Float[halfHours];
		Float[] ieTimes = new Float[halfHours];
		for (int i = 0; i < halfHours; i++) {
			chromeTimes[i] = showRandomFloat(50, 60, r);
			firefoxTimes[i] = showRandomFloat(25, 30, r);
			ieTimes[i] = showRandomFloat(10, 20, r);
		}
		map.put("Chrome", chromeTimes);
		map.put("Firefox", firefoxTimes);
		map.put("Linux", ieTimes);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		Date[] times = new Date[halfHours];
		for (int i = halfHours - 1; i >= 0; i--) {
			times[i] = cal.getTime();
			cal.add(Calendar.MINUTE, -30);
		}
		mLineChart.setData(map, times);
	}

	private float showRandomFloat(int aStart, int aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		float randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void updateContent(ArrayList<String> data) {
		int[] array = new int[3];

		array[0] = Integer.parseInt(data.get(0));
		array[1] = Integer.parseInt(data.get(1));
		array[2] = Integer.parseInt(data.get(2));
		// array[3] = 100 - array[0] - array[1] - array[2];
		// array[3] = array[3] < 0? 0: array[3];
		// boolean sumCorrectly = array[0]+array[1]+array[2]+array[3] == 100 ?
		// true: false;

		Arrays.sort(array);

		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				array[2] = array[2] - 5;
				array[i] = array[i] + 5;
			}
		}

		// Toast.makeText(this.getActivity(),
		// mPieChart.getData().getDataSet().getEntryCount()+"",
		// Toast.LENGTH_LONG).show();

		mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal(array[0]);
		mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal(array[1]);
		mPieChart.getData().getDataSet().getEntryForXIndex(2)
				.setVal(100 - array[0] - array[1]);
		// mPieChart.getData().getDataSet().getEntryForXIndex(3).setVal(array[3]);

		mPieChart.notifyDataSetChanged();
		mPieChart.invalidate();
	}

}
