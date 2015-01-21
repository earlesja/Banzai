package com.pan.banzai;

public class DefaultValues {

	public static final String sCpuWarningThresholdKey = "CPU_WARN";
	public static final String sCpuCriticalThresholdKey = "CPU_CRIT";
	public static final String sRamWarningThresholdKey = "RAM_WARN";
	public static final String sRamCriticalThresholdKey = "RAM_CRIT";
	public static final String sStorageWarningThresholdKey = "STOR_WARN";
	public static final String sStorageCriticalThresholdKey = "STOR_CRIT";
	public static final String sDiskQueueWaitLengthKey = "DISK_QUEUE";
	public static final String sGraphTimeFrameKey = "TIME_FRAME";

	public static final int sDefaultWarningThreshold = 75;
	public static final int sDefaultCriticalThreshold = 90;
	public static final int sDefaultDiskQueueWaitLength = 1000;
	public static final int sDefaultGraphTimeFrame = 24;

	public static void storeDefaultInSharedPref() {
		if (SharedPreferenceHelper.isInit()) {
			// cpu defaults
			SharedPreferenceHelper.putIntPreference(sCpuWarningThresholdKey,
					sDefaultWarningThreshold);
			SharedPreferenceHelper.putIntPreference(sCpuCriticalThresholdKey,
					sDefaultCriticalThreshold);

			// ram defaults
			SharedPreferenceHelper.putIntPreference(sRamWarningThresholdKey,
					sDefaultWarningThreshold);
			SharedPreferenceHelper.putIntPreference(sRamCriticalThresholdKey,
					sDefaultCriticalThreshold);

			// storage defaults
			SharedPreferenceHelper.putIntPreference(
					sStorageWarningThresholdKey, sDefaultWarningThreshold);
			SharedPreferenceHelper.putIntPreference(
					sStorageCriticalThresholdKey, sDefaultCriticalThreshold);

			// disk wait length defaults
			SharedPreferenceHelper.putIntPreference(sDiskQueueWaitLengthKey,
					sDefaultDiskQueueWaitLength);

			// graph defaults
			SharedPreferenceHelper.putIntPreference(sGraphTimeFrameKey,
					sDefaultGraphTimeFrame);
		}
	}

	// CPU warning
	public static void putCpuWarningThreshold(int value) {
		SharedPreferenceHelper.putIntPreference(sCpuWarningThresholdKey, value);
	}

	public static int getCpuWarningThreshold() {
		return SharedPreferenceHelper.getIntPreference(sCpuWarningThresholdKey,
				sDefaultWarningThreshold);
	}

	// CPU critical
	public static void putCpuCriticalThreshold(int value) {
		SharedPreferenceHelper
				.putIntPreference(sCpuCriticalThresholdKey, value);
	}

	public static int getCpuCriticalThreshold() {
		return SharedPreferenceHelper.getIntPreference(
				sCpuCriticalThresholdKey, sDefaultCriticalThreshold);
	}

	// RAM warning
	public static void putRamWarningThreshold(int value) {
		SharedPreferenceHelper.putIntPreference(sRamWarningThresholdKey, value);
	}

	public static int getRamWarningThreshold() {
		return SharedPreferenceHelper.getIntPreference(sRamWarningThresholdKey,
				sDefaultWarningThreshold);
	}

	// RAM critical
	public static void putRamCriticalThreshold(int value) {
		SharedPreferenceHelper
				.putIntPreference(sRamCriticalThresholdKey, value);
	}

	public static int getRamCriticalThreshold() {
		return SharedPreferenceHelper.getIntPreference(
				sRamCriticalThresholdKey, sDefaultCriticalThreshold);
	}

	// Storage warning
	public static void putStorageWarningThreshold(int value) {
		SharedPreferenceHelper.putIntPreference(sStorageWarningThresholdKey,
				value);
	}

	public static int getStorageWarningThreshold() {
		return SharedPreferenceHelper.getIntPreference(
				sStorageWarningThresholdKey, sDefaultWarningThreshold);
	}

	// Storage critical
	public static void putStorageCriticalThreshold(int value) {
		SharedPreferenceHelper.putIntPreference(sStorageCriticalThresholdKey,
				value);
	}

	public static int getStorageCriticalThreshold() {
		return SharedPreferenceHelper.getIntPreference(
				sStorageCriticalThresholdKey, sDefaultCriticalThreshold);
	}

	// Queue wait length
	public static void putQueueWaitLength(int value) {
		SharedPreferenceHelper.putIntPreference(sDiskQueueWaitLengthKey, value);
	}

	public static int getQueueWaitLength() {
		return SharedPreferenceHelper.getIntPreference(sDiskQueueWaitLengthKey,
				sDefaultDiskQueueWaitLength);
	}

	// graph time frame
	public static void putGraphTimeFrame(int value) {
		SharedPreferenceHelper.putIntPreference(sGraphTimeFrameKey, value);
	}

	public static int getGraphTimeFrame() {
		return SharedPreferenceHelper.getIntPreference(sGraphTimeFrameKey,
				sDefaultGraphTimeFrame);
	}

}
