package com.pan.banzai;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class UsagePieGraph extends PieChart {

	private int[] mColors;

	public UsagePieGraph(Context context) {
		super(context);
		init();
	}

	public UsagePieGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public UsagePieGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@SuppressLint("ClickableViewAccessibility")
	protected void init() {
		super.init();
		// set default values setDrawHoleEnabled(false);
		setDrawCenterText(false);
		setDrawYValues(false);
		setRotationEnabled(true);
		setUsePercentValues(true);
		setDrawCenterText(false);
		setRotationAngle(0);
		setDrawXValues(false);
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});

		mColors = getContext().getResources().getIntArray(
				R.array.pie_chart_colors);

	}

	@Override
	protected void drawDescription() {
		// do nothing. don't want to display
	}

	public void setData(HashMap<String, Float> data) {

		ArrayList<Entry> values = new ArrayList<Entry>();
		ArrayList<String> titles = new ArrayList<String>();

		Iterator<String> keysIterator = data.keySet().iterator();
		int pos = 0;
		while (keysIterator.hasNext()) {
			String title = keysIterator.next();
			
			values.add(new Entry(data.get(title), pos));
			
			StringBuilder newTitle = new StringBuilder();
			new Formatter(newTitle, Locale.US).format("%1$.2f%%", data.get(title)).close();
			
			newTitle.append(" - " + title);
			titles.add(newTitle.toString());

			pos++;
		}
		PieDataSet dataValues = new PieDataSet(values, getContext().getString(
				R.string.operating_systems));
		dataValues.setSliceSpace(2f);
		dataValues.setColors(mColors);

		PieData newData = new PieData(titles, dataValues);
		setData(newData);

		// redraw
		highlightValues(null);
		invalidate();

		// set legend (must be called after data is set)
		Legend legend = getLegend();
		legend.setPosition(LegendPosition.RIGHT_OF_CHART);
	}

}
