package com.pan.banzai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class DonutGraph extends PieGraph {

	public static final int sDonutInnerCircleRatio = 175;

	public enum DonutType {
		CPU {
			@Override
			public int getWarning() {
				return Storage.getCpuWarningThreshold();
			}

			@Override
			public int getCritical() {
				return Storage.getCpuCriticalThreshold();
			}
		},
		RAM {
			@Override
			public int getWarning() {
				return Storage.getRamWarningThreshold();
			}

			@Override
			public int getCritical() {
				return Storage.getRamCriticalThreshold();
			}
		},
		STORAGE {
			@Override
			public int getWarning() {
				return Storage.getStorageWarningThreshold();
			}

			@Override
			public int getCritical() {
				return Storage.getStorageCriticalThreshold();
			}
		};

		public abstract int getWarning();

		public abstract int getCritical();
	}

	private static DonutType[] sDonutTypes = DonutType.values();

	private PieSlice coloredSlice;
	private PieSlice bufferSlice;
	private float percentage;
	private DonutType mType;

	private Rect mBounds = new Rect();

	public DonutGraph(Context context) {
		super(context);
	}

	public DonutGraph(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DonutGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

		TypedArray styledAttrs = context.obtainStyledAttributes(attrs,
				R.styleable.DonutGraph, 0, 0);
		int type = 0;
		try {
			type = styledAttrs.getInt(R.styleable.DonutGraph_type, type);
		} finally {
			styledAttrs.recycle();
		}
		mType = sDonutTypes[type];
		this.setInnerCircleRatio(sDonutInnerCircleRatio);

		coloredSlice = new PieSlice();
		bufferSlice = new PieSlice();
		bufferSlice.setColor(getResources().getColor(R.color.transparent));
		this.addSlice(coloredSlice);
		this.addSlice(bufferSlice);
		setClickable(true);
	}

	public void setPercentage(float percent) {
		percentage = percent;
		coloredSlice.setColor(determineDonutColor(percentage,
				mType.getWarning(), mType.getCritical()));
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

	private int determineDonutColor(float value, int warningThreshold,
			int criticalThreshold) {
		if (value >= criticalThreshold) {
			return getResources().getColor(R.color.critical);
		} else if (value >= warningThreshold) {
			return getResources().getColor(R.color.warning);
		} else {
			return getResources().getColor(R.color.safe);
		}
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		
		setOnSliceClickedListener(new OnSliceClickedListener() {			
			@Override
			public void onClick(int index) {
				DonutGraph.this.performClick();				
			}
		});
	}
	
}