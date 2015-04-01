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
import android.widget.Toast;

public class OsUsageFragment extends Fragment {
	private UsagePieGraph mPieChart;
	private BanzaiLineGraph mLineChart;
	private static float LINUX_USAGE=10f;
	private static float MAC_USAGE=30f;
	private static float WINDOWS_USAGE=55f;
	private static float OTHER_OS_USAGE=5f;

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
		map.put("Mac", 30f);
		map.put("Linux", 10f);
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
		
//		 Toast.makeText(this.getActivity(),
//		 mPieChart.getData().getDataSet().getEntryForXIndex(0).getVal() +"\n"+
//		 mPieChart.getData().getDataSet().getEntryForXIndex(1).getVal() +"\n"+
//		 mPieChart.getData().getDataSet().getEntryForXIndex(2).getVal() +"\n"+
//		 mPieChart.getData().getDataSet().getEntryForXIndex(3).getVal() +"\n",
//		 Toast.LENGTH_LONG).show();
		
		
		int metricId = Integer.parseInt(data.get(0)); 
		float value = Float.parseFloat(data.get(1));
		
		if(metricId == 54){
			//windows
			this.WINDOWS_USAGE = value;
		}else if(metricId == 66){
			//mac
			this.MAC_USAGE = value;
		}else if(metricId == 69){
			//linux
			this.LINUX_USAGE = value;
		}else if(metricId == 78){
			//other
			this.LINUX_USAGE = value;
		}
		
		float total = this.WINDOWS_USAGE + this.MAC_USAGE + this.LINUX_USAGE + this.OTHER_OS_USAGE;

		mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal((this.OTHER_OS_USAGE/total)*100);
		mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal((this.LINUX_USAGE/total)*100);
		mPieChart.getData().getDataSet().getEntryForXIndex(2).setVal((this.MAC_USAGE/total)*100);
		mPieChart.getData().getDataSet().getEntryForXIndex(3).setVal((this.WINDOWS_USAGE/total)*100);
		
		mPieChart.notifyDataSetChanged();
		mPieChart.invalidate();
	}

}
