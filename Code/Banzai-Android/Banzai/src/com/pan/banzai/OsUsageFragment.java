package com.pan.banzai;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnGenericMotionListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class OsUsageFragment extends Fragment {
	private PieChart mChart;
	protected String[] mParties = new String[] { "Party A", "Party B",
			"Party C", "Party D", "Party E", "Party F", "Party G" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_osusage, container,
				false);

		// int[] colors = getResources().getIntArray(R.array.pie_chart_colors);
		//
		// PieGraph pieGraph = (PieGraph)
		// view.findViewById(R.id.osusagePieChart);
		// pieGraph.setPadding(2);
		// pieGraph.setInnerCircleRatio(150);
		//
		// for (int i = 0; i < colors.length; i++) {
		// PieSlice slice = new PieSlice();
		// Log.d("XX", colors[i] + "");
		// slice.setColor(colors[i]);
		// slice.setValue(10 + i);
		// pieGraph.addSlice(slice);
		// }
		// colors = getResources().getIntArray(R.array.pie_chart_text_colors);
		//
		// PieGraph pieGraphT = (PieGraph) view
		// .findViewById(R.id.osusagePieChart2);
		// pieGraphT.setPadding(2);
		// pieGraphT.setInnerCircleRatio(150);
		//
		// for (int i = 0; i < colors.length; i++) {
		// PieSlice slice = new PieSlice();
		// Log.d("XX", colors[i] + "");
		// slice.setColor(colors[i]);
		// slice.setValue(10 + i);
		// pieGraphT.addSlice(slice);
		// }

		mChart = (PieChart) view.findViewById(R.id.chart1);

		mChart.setHoleRadius(60f);

		mChart.setDescription("");

		mChart.setDrawYValues(true);
		mChart.setDrawCenterText(true);

		mChart.setDrawHoleEnabled(true);

		mChart.setRotationAngle(0);

		// draws the corresponding description value into the slice
		mChart.setDrawXValues(true);

		// enable rotation of the chart by touch
		mChart.setRotationEnabled(true);

		// display percentage values
		mChart.setUsePercentValues(true);

		setData(3, 100);

		// mChart.spin(2000, 0, 360);

		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(5f);
		mChart.setCenterText("75%");
		mChart.setCenterTextSize(20);
		mChart.setValueTextSize(0);
		mChart.setDrawLegend(false);
		mChart.setHoleColorTransparent(true);
		mChart.setHoleRadius(80);
		mChart.setDescriptionTextSize(0);
		mChart.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		mChart.setRotationEnabled(false);
		mChart.setTransparentCircleRadius(0);

		return view;
	}

	private void setData(int count, float range) {

		float mult = range;

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		// IMPORTANT: In a PieChart, no values (Entry) should have the same
		// xIndex (even if from different DataSets), since no values can be
		// drawn above each other.
		yVals1.add(new Entry(75, 0));
		yVals1.add(new Entry(25, 1));

		ArrayList<String> xVals = new ArrayList<String>();

		xVals.add("");
		xVals.add("");

		PieDataSet set1 = new PieDataSet(yVals1, "Election Results");
		set1.setSliceSpace(3f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();
		colors.add(getResources().getColor(R.color.pie_chart_purple));
		colors.add(getResources().getColor(R.color.transparent));

		set1.setColors(colors);

		PieData data = new PieData(xVals, set1);

		mChart.setData(data);

		// undo all highlights
		mChart.highlightValues(null);

		mChart.invalidate();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
