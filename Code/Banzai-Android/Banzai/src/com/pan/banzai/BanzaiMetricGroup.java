package com.pan.banzai;

public class BanzaiMetricGroup {
	private String name;
	private int[] metricIds;

	public BanzaiMetricGroup(String name, int[] metricIds) {
		this.name = name;
		this.metricIds = metricIds;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int[] getMetricIds(){
		return this.metricIds;
	}
}
