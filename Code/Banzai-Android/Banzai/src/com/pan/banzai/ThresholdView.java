package com.pan.banzai;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThresholdView extends LinearLayout {

	public static final int sThresholdMax = 100;
	public static final int sThresholdMin = 100;

	private RangeSeekBar<Integer> mRangeSeekBar;
	private TextView mDescTextView;

	public ThresholdView(Context context) {
		this(context, null);
	}

	public ThresholdView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ThresholdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ThresholdView, 0, 0);
		String desc = "";
		float descTextSize = 20;
		try {
			desc = a.getString(R.styleable.ThresholdView_descText);
			descTextSize = a.getFloat(R.styleable.ThresholdView_descTextSize,
					descTextSize);
		} finally {
			a.recycle();
		}

		setOrientation(LinearLayout.VERTICAL);
		mDescTextView = new TextView(context);
		mDescTextView.setTextSize(descTextSize);
		mDescTextView.setText(desc);
		mDescTextView.setPadding(0, 0, 0, 5);
		addView(mDescTextView);
		mRangeSeekBar = new RangeSeekBar<Integer>(sThresholdMin, sThresholdMax,
				context);
		addView(mRangeSeekBar);
	}

	public void setText(String text) {
		mDescTextView.setText(text);
	}

}
