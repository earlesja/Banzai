package com.pan.banzai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.LimitLine;
import com.github.mikephil.charting.utils.MarkerView;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class BanzaiLineGraph extends LineChart {

	@SuppressLint("SimpleDateFormat")
	public static final DateFormat formatter = new SimpleDateFormat("HH:mm");
	private int[] mColors;

	public BanzaiLineGraph(Context context) {
		super(context);
	}

	public BanzaiLineGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BanzaiLineGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void init() {
		super.init();

		// setttings tweaks
		mColors = getResources().getIntArray(R.array.pie_chart_colors);
		setDrawGridBackground(false);

		// setStartAtZero(true);

		// disable the drawing of values into the chart
		setDrawYValues(false);
		setHighlightEnabled(true);
		setDrawGridBackground(false);
		setTouchEnabled(true);
		setDragEnabled(true);
		setScaleEnabled(true);
		setPinchZoom(true);
		setHighlightEnabled(false);
		setUnit("%");
		setDrawUnitsInChart(true);
		setMarkerView(new LineChartMarker(getContext(),
				R.layout.line_chart_marker, getUnit()));

	}

	@Override
	protected void drawDescription() {
		// do nothing
	}

	// assumes the float[] and date[] are the same size
	public void setData(HashMap<String, Float[]> data, Date[] times) {
		ArrayList<String> titles = new ArrayList<String>();
		titles.addAll(data.keySet());

		ArrayList<String> xAxisVals = new ArrayList<String>();
		for (Date time : times) {
			xAxisVals.add(formatter.format(time));
		}

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

		int pos = 0;
		for (String key : titles) {
			Float[] rawPoints = data.get(key);
			ArrayList<Entry> values = new ArrayList<Entry>();

			for (int i = 0; i < rawPoints.length; i++) {
				values.add(new Entry(rawPoints[i], i));
			}

			LineDataSet d = new LineDataSet(values, key);
			d.setLineWidth(2f);
			d.setCircleSize(3f);
			int color = mColors[pos % mColors.length];
			d.setColor(color);
			d.setCircleColor(color);
			// dash line if positions than colors
			if (pos >= mColors.length) {
				d.enableDashedLine(5f, 5f, 0f);
			}

			dataSets.add(d);
			pos++;
		}

		LineData lData = new LineData(xAxisVals, dataSets);
		setData(lData);
		invalidate();

		getLegend().setPosition(LegendPosition.BELOW_CHART_CENTER);
	}
}
