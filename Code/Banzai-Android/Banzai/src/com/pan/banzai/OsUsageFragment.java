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

public class OsUsageFragment extends Fragment {
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
		View view = inflater.inflate(R.layout.fragment_osusage, container,
				false);

		mPieChart = (UsagePieGraph) view
				.findViewById(R.id.os_current_pie_chart);
		setCurrentValues();

		mLineChart = (BanzaiLineGraph) view
				.findViewById(R.id.os_historic_line_chart);
		setHistoricData();

		return view;
	}

	// TODO get actual data
	private void setCurrentValues() {
		// TODO http request?

		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("Windows", 55f);
		map.put("Mac", 35f);
		map.put("Linux", 5f);
		map.put("Other", 5f);

		mPieChart.setData(map);
	}

	// TODO get actual data
	private void setHistoricData() {
		// TODO http request?
		Random r = new Random();

		HashMap<String, Float[]> map = new HashMap<String, Float[]>();
		int halfHours = SharedPreferenceHelper.getIntPreference(
				DefaultValues.sGraphTimeFrameKey, 2) * 2;
		Float[] windowsTimes = new Float[halfHours];
		Float[] macTimes = new Float[halfHours];
		Float[] linuxTimes = new Float[halfHours];
		Float[] otherTimes = new Float[halfHours];
		for (int i = 0; i < halfHours; i++) {
			windowsTimes[i] = showRandomFloat(40, 60, r);
			macTimes[i] = showRandomFloat(20, 30, r);
			linuxTimes[i] = showRandomFloat(5, 15, r);
			otherTimes[i] = showRandomFloat(1, 5, r);
		}
		map.put("Windows", windowsTimes);
		map.put("Mac", macTimes);
		map.put("Linux", linuxTimes);
		map.put("Other", otherTimes);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		Date[] times = new Date[halfHours];
		for(int i = halfHours - 1; i >= 0; i --){
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
		// int datapoint = Integer.parseInt(data.get(0)) * 10;
		// int index = mLineChart.getDataSetByIndex(2).getEntryCount();
		// Entry newEntry = new Entry(datapoint, index);
		// //mLineChart.getData().addEntry(newEntry, index);
		// mLineChart.getData().getDataSetByIndex(2).addEntry(newEntry);
		// LineData chartData = mLineChart.getData();
		// ArrayList<String> xvals = chartData.getXVals();
		// int lastIndex = xvals.size() - 1;
		// int lastVal = Integer.parseInt(xvals.get(lastIndex).substring(3));
		// int newVal = lastVal + 5;
		// mLineChart.getData().getXVals().add("00:" + newVal);
		// mLineChart.postInvalidate();
		// String toastMessage = "Test: ";
		//
		// for(int i=0; i<mLineChart.getDataSetByIndex(2).getEntryCount(); i++){
		// toastMessage = toastMessage +
		// mLineChart.getData().getDataSetByIndex(2).getEntryForXIndex(i).getVal()
		// + "\n";
		//
		// }
		//
		//
		// Toast.makeText(this.getActivity(),
		// toastMessage + "XVALUES: " + mLineChart.getData().getXVals(),
		// Toast.LENGTH_LONG).show();

		// -----------------------------

		// int GRAPH_WIDTH = 10;
		//
		// LineData lineData = mLineChart.getData();
		// LineDataSet lineDataSet = lineData.getDataSetByIndex(0);
		// int count = lineDataSet.getEntryCount();
		//
		// // Make rolling window
		// if (lineData.getXValCount() <= count) {
		// // Remove/Add XVal
		// lineData.getXVals().add("" + count);
		// lineData.getXVals().remove(0);
		//
		// // Move all entries 1 to the left..
		// for (int i=0; i < count; i++) {
		// Entry e = lineDataSet.getEntryForXIndex(i);
		// if (e==null) continue;
		//
		// e.setXIndex(e.getXIndex() - 1);
		// }
		//
		// // Set correct index to add value
		// count = GRAPH_WIDTH;
		// }
		//
		// // Add new value
		// lineData.addEntry(new Entry(datapoint, count), 0);
		//
		// // Make sure to draw
		// mLineChart.notifyDataSetChanged();
		// mLineChart.invalidate();

		// -------------------------------------
		Float[] array = new Float[4];

		array[0] = Float.parseFloat(data.get(0));
		array[1] = Float.parseFloat(data.get(1));
		array[2] = Float.parseFloat(data.get(2));
		array[3] = 100 - array[0] - array[1] - array[2];
		array[3] = array[3] < 0 ? 0 : array[3];
		boolean sumCorrectly = array[0] + array[1] + array[2] + array[3] == 100 ? true
				: false;

		Arrays.sort(array);

		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				array[3] = array[3] - 5;
				array[i] = array[i] + 5;
			}
		}

		// Toast.makeText(this.getActivity(),
		// sumCorrectly +"\n"+
		// data0 +"\n"+
		// data1 +"\n"+
		// data2 +"\n"+
		// data3 +"\n",
		// Toast.LENGTH_LONG).show();

		mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal(array[0]);
		mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal(array[1]);
		mPieChart.getData().getDataSet().getEntryForXIndex(2).setVal(array[2]);
		mPieChart.getData().getDataSet().getEntryForXIndex(3).setVal(array[3]);

		mPieChart.notifyDataSetChanged();
		mPieChart.invalidate();
	}

}
