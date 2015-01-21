package com.pan.banzai;

import java.util.ArrayList;

public class ServerTierStatus {

	private String tierName;
	private float cpuStatusPercent;
	private float ramStatusPercent;
	private float storageStatusPercent;
	private ArrayList<Integer> averageDiskQueueLengths;

	public ServerTierStatus(String tierName, float cpuStatusPercent,
			float ramStatusPercent, float storageStatusPercent,
			ArrayList<Integer> averageDiskQueueLengths) {
		this.tierName = tierName;
		this.cpuStatusPercent = cpuStatusPercent;
		this.ramStatusPercent = ramStatusPercent;
		this.storageStatusPercent = storageStatusPercent;
		this.averageDiskQueueLengths = averageDiskQueueLengths;
	}

	public String getTierName() {
		return tierName;
	}

	public float getCpuStatusPercent() {
		return cpuStatusPercent;
	}

	public void setCpuStatusPercent(float cpuStatusPercent) {
		this.cpuStatusPercent = cpuStatusPercent;
	}

	public float getRamStatusPercent() {
		return ramStatusPercent;
	}

	public void setRamStatusPercent(float ramStatusPercent) {
		this.ramStatusPercent = ramStatusPercent;
	}

	public float getStorageStatusPercent() {
		return storageStatusPercent;
	}

	public void setStorageStatusPercent(float storageStatusPercent) {
		this.storageStatusPercent = storageStatusPercent;
	}

	public ArrayList<Integer> getAverageDiskQueueLengths() {
		return this.averageDiskQueueLengths;
	}
}
