package com.pan.banzai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
		map.put("IE", 5f);
		map.put("Firefox", 40f);
		map.put("Chrome", 55f);
		//map.put("Other", 5f);

		mPieChart.setData(map);
	}

	// TODO get actual data
	private void setHistoricData() {
		// TODO http request?

		HashMap<String, Float[]> map = new HashMap<String, Float[]>();
		map.put("IE", new Float[] { 10f, 10f, 5f });
		map.put("Firefox", new Float[] { 30f, 32f, 40f });
		map.put("Chrome", new Float[] { 60f, 58f, 55f });

		Date[] times = new Date[] { new Date(2015, 1, 10, 0, 30, 30),
				new Date(2015, 1, 10, 0, 35, 30),
				new Date(2015, 1, 10, 0, 40, 30) };

		mLineChart.setData(map, times);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	public void updateContent(ArrayList<String> data){
		int[] array = new int[3];
		
		array[0] = Integer.parseInt(data.get(0));
		array[1] = Integer.parseInt(data.get(1));
		array[2] = Integer.parseInt(data.get(2));
		//array[3] = 100 - array[0] - array[1] - array[2];
		//array[3] = array[3] < 0? 0: array[3];
		//boolean sumCorrectly = array[0]+array[1]+array[2]+array[3] == 100 ? true: false;
		
		Arrays.sort(array);
		
		for(int i = 0; i<array.length; i++){
			if(array[i] == 0){
				array[2] = array[2] - 5;
				array[i] = array[i] + 5;
			}
		}
		
//		Toast.makeText(this.getActivity(),
//				mPieChart.getData().getDataSet().getEntryCount()+"",
//			    Toast.LENGTH_LONG).show();
		
		mPieChart.getData().getDataSet().getEntryForXIndex(0).setVal(array[0]);
		mPieChart.getData().getDataSet().getEntryForXIndex(1).setVal(array[1]);
		mPieChart.getData().getDataSet().getEntryForXIndex(2).setVal(array[2]);
		//mPieChart.getData().getDataSet().getEntryForXIndex(3).setVal(array[3]);
		

		
		
		mPieChart.notifyDataSetChanged();
		mPieChart.invalidate();
	}

}
