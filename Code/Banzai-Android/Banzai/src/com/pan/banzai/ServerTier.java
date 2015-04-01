package com.pan.banzai;

import java.util.Random;

public class ServerTier {

	private String tierName;
	private int[] cpuMetricIds;
	private int[] ramMetricIds;
	private int[] diskMetricIds;
	private int[] serverIds;
	private String[] serverNames;

	private Random r;

	public ServerTier(String tierName, int[] cpuMetricIds, int[] ramMetricIds,
			int[] diskMetricIds, int[] serverIds, String[] serverNames) {
		this.tierName = tierName;
		this.cpuMetricIds = cpuMetricIds;
		this.ramMetricIds = ramMetricIds;
		this.diskMetricIds = diskMetricIds;
		this.serverIds = serverIds;
		this.serverNames = serverNames;
		

		r = new Random();
	}

	public String getTierName() {
		return tierName + " Teir";
	}

	public float getCpuStatusPercent() {
		return getRandomInteger(90, 100);
	}

	public float getRamStatusPercent() {
		return getRandomInteger(70, 80);
	}

	public float getStorageStatusPercent() {
		return getRandomInteger(40, 60);
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
