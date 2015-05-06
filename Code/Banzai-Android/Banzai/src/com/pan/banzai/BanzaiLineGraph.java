package com.pan.banzai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

@SuppressLint("SimpleDateFormat")
public class BanzaiLineGraph extends LineChart {

	@SuppressLint("SimpleDateFormat")
	public static final DateFormat hourFormatter = new SimpleDateFormat("hh:mm aa");
	public static final DateFormat dayFormatter = new SimpleDateFormat("hh aa");
	public static final DateFormat weekFormatter = new SimpleDateFormat("MM/dd");
	public static final DateFormat monthFormatter = new SimpleDateFormat("MM/dd");
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
		setMarkerView(new BanzaiLineChartMarker(getContext(),
				R.layout.line_chart_marker));

	}

	@Override
	protected void drawDescription() {
		// do nothing
	}

	public void addToData(HashMap<String, Float> data, Date time, SimpleDateFormat formatter) {
		ArrayList<String> titles = new ArrayList<String>();
		titles.addAll(data.keySet());

		ArrayList<String> xAxis = getData().getXVals();
		xAxis.add(formatter.format(time));

		LineData lData = getData();
		int datasetSize = lData.getXValCount();

		for (int i = 0; i < titles.size(); i++) {
			Entry newPoint = new Entry(data.get(titles.get(i)), datasetSize);
			lData.addEntry(newPoint, i);
		}

		setData(new LineData(xAxis, lData.getDataSets()));

		invalidate();
	}

	public void setData(HashMap<String, ArrayList<Float>> data, Date[] times, DateFormat formatter) {
		ArrayList<String> titles = new ArrayList<String>();
		titles.addAll(data.keySet());

		ArrayList<String> xAxisVals = new ArrayList<String>();
		for (Date time : times) {
			xAxisVals.add(formatter.format(time));
		}

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

		int pos = 0;
		for (String key : titles) {
			List<Float> rawPoints = data.get(key);
			ArrayList<Entry> values = new ArrayList<Entry>();

			for (int i = 0; i < rawPoints.size(); i++) {
				values.add(new Entry(rawPoints.get(i), i));
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

	public class BanzaiLineChartMarker extends LineChartMarker {

		private TextView mTextView;

		public BanzaiLineChartMarker(Context context, int layoutResource) {
			super(context, layoutResource);
			mTextView = (TextView) findViewById(R.id.marker_text);
		}

		@Override
		public void refreshContent(Entry e, int dataSetIndex) {
			StringBuilder toDisplay = new StringBuilder();
			Formatter formatter = new Formatter(toDisplay, Locale.US);
			formatter.format("%1$.2f%%", e.getVal());
			formatter.close();
			mTextView.setText(toDisplay.toString());
		}

	}
}
