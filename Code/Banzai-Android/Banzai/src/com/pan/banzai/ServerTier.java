package com.pan.banzai;

import java.util.List;
import java.util.Random;

public class ServerTier {

	private String tierName;
	private int[] cpuMetricIds;
	private int[] ramMetricIds;
	private int[] diskMetricIds;
	private int[] serverIds;
	private String[] serverNames;
	
	private float cpuPercent = 0;
	private float ramPercent = 0;
	private float diskPercent = 0;

	private Random r;

	public ServerTier(String tierName, int[] aPP_CPU_METRICIDS, int[] aPP_RAM_METRICIDS,
			int[] aPP_DISK_METRICIDS, int[] aPP_SERVERIDS, String[] aPP_SERVER_NAMES) {
		this.tierName = tierName;
		this.cpuMetricIds = aPP_CPU_METRICIDS;
		this.ramMetricIds = aPP_RAM_METRICIDS;
		this.diskMetricIds = aPP_DISK_METRICIDS;
		this.serverIds = aPP_SERVERIDS;
		this.serverNames = aPP_SERVER_NAMES;
		

		r = new Random();
	}

	public String getTierName() {
		return tierName + " Tier";
	}

	public float getCpuStatusPercent() {
		return cpuPercent;
	}

	public float getRamStatusPercent() {
		return ramPercent;
	}

	public float getStorageStatusPercent() {
		return diskPercent;
	}
	
	public void setCpuStatusPercent(float value){
		this.cpuPercent = value;
	}
	
	public void setRamStatusPercent(float value){
		this.ramPercent = value;
	}
	
	public void setDiskStatusPercent(float value){
		this.diskPercent = value;
	}

	public int[] getRamMetricIds() {
		return ramMetricIds;
	}

	public int[] getServerIds() {
		return serverIds;
	}

	public int[] getDiskMetricIds() {
		return diskMetricIds;
	}

	public int[] getCpuMetricIds() {
		return cpuMetricIds;
	}

	public String[] getServerNames() {
		return this.serverNames;
	}

	private int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * r.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}
}
