package com.pan.banzai;

import java.text.DateFormat;

import android.app.ActionBar.OnNavigationListener;

public class TimeFrameChooser implements OnNavigationListener {
	private Callback callback;
	
	public TimeFrameChooser(Callback callback){
		this.callback = callback;
	}
	
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		int seconds = 0;
		String groupBy = "Hour";
		DateFormat formatter = BanzaiLineGraph.hourFormatter;
		
		switch(itemPosition){
		case 0:
			//one hour
			seconds = 3600;
			groupBy = "Minute";
			formatter = BanzaiLineGraph.hourFormatter;
			break;
		case 1:
			//one day
			seconds = 3600 * 24;
			groupBy = "Hour";
			formatter = BanzaiLineGraph.dayFormatter;
			break;
			
		case 2:
			//one week
			seconds = 3600 * 24 * 7;
			groupBy="Day";
			formatter = BanzaiLineGraph.weekFormatter;
			break;
			
		case 3:
			//one month
			seconds = 3600 * 24 * 4;
			groupBy="Day";
			formatter = BanzaiLineGraph.monthFormatter;
			break;
		}
		this.callback.process(seconds, groupBy, formatter);		
		return true;
	}
	
	public interface Callback{
		public void process(int timeframe, String groupBy, DateFormat formatter);
	}

}
