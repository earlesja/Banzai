package com.pan.banzai;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class DonutGraph extends PieGraph {

	public static final float sDonutCriticalThreshHold = 90;
	public static final float sDonutWarningThreshHold = 75;
	public static final int sDonutInnerCircleRatio = 175;

	private PieSlice coloredSlice;
	private PieSlice bufferSlice;
	private float percentage;
	private Rect mBounds = new Rect();

	public DonutGraph(Context context) {
		this(context, null);
	}

	public DonutGraph(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DonutGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

		this.setInnerCircleRatio(sDonutInnerCircleRatio);

		coloredSlice = new PieSlice();
		bufferSlice = new PieSlice();
		bufferSlice.setColor(getResources().getColor(R.color.transparent));
		this.addSlice(coloredSlice);
		this.addSlice(bufferSlice);
	}

	public void setPercentage(float percent) {
		percentage = percent;
		coloredSlice.setColor(determineDonutColor(percentage));
		coloredSlice.setValue(percentage);

		// invisible second part of donut that's 100%-value
		bufferSlice.setValue(100 - percentage);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		float midX = getWidth() / 2;
		float midY = getHeight() / 2;
		float radius;
		if (midX < midY) {
			radius = midX;
		} else {
			radius = midY;
		}
		radius -= mPadding;
		float innerRadius = radius * mInnerCircleRatio / 255;
		float fontHeight = innerRadius * 3 / 4;

		String toDisplay = getPercentageAsString();
		mPaint.setColor(Color.BLACK);
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(fontHeight);
		mPaint.getTextBounds(toDisplay, 0, toDisplay.length(), mBounds);

		float x = midX;
		float y = midY + (mBounds.bottom - mBounds.top) / 2;
		canvas.drawText(toDisplay, x, y, mPaint);
	}

	public String getPercentageAsString() {
		return (int) Math.round(percentage) + "%";
	}

	/**
	 * Determines the color of a donut graph based on the value and the set
	 * threshholds
	 * 
	 * @param value
	 *            Value to check
	 * @return Int representing color of the graph
	 */
	private int determineDonutColor(float value) {
		if (value >= sDonutCriticalThreshHold) {
			return getResources().getColor(R.color.critical);
		} else if (value >= sDonutWarningThreshHold) {
			return getResources().getColor(R.color.warning);
		} else {
			return getResources().getColor(R.color.safe);

		}
	}

}
