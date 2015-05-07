package com.pan.banzai;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MarkerView;

import android.content.Context;
import android.widget.TextView;


public class LineChartMarker extends MarkerView {

	private TextView mTextView;
	private String mDataModifier;

	public LineChartMarker(Context context, int layoutResource) {
		this(context, layoutResource, "");
	}

	public LineChartMarker(Context context, int layoutResource,
			String dataModifier) {
		super(context, layoutResource);
		mTextView = (TextView) findViewById(R.id.marker_text);
		mDataModifier = dataModifier;
	}

	@Override
	public void refreshContent(Entry e, int dataSetIndex) {
		mTextView.setText(e.getVal() + mDataModifier);

	}

	@Override
	public int getXOffset() {
		return -(getWidth() / 2);
	}

	@Override
	public int getYOffset() {
		return -getHeight();
	}

}
