package com.pan.banzai;

public class DefaultValues {

	public static final String sCpuWarningThresholdKey = "CPU_WARN";
	public static final String sCpuCriticalThresholdKey = "CPU_CRIT";
	public static final String sRamWarningThresholdKey = "RAM_WARN";
	public static final String sRamCriticalThresholdKey = "RAM_CRIT";
	public static final String sStorageWarningThresholdKey = "STOR_WARN";
	public static final String sStorageCriticalThresholdKey = "STOR_CRIT";

	public static final int sDefaultWarningThreshold = 75;
	public static final int sDefaultCriticalThreshold = 90;

	public static void storeDefaultInSharedPref() {
		if (SharedPreferenceHelper.isInit()) {
			// cpu defaults

			storeValueIfNotSet(sCpuWarningThresholdKey,
					sDefaultWarningThreshold);
			storeValueIfNotSet(sCpuCriticalThresholdKey,
					sDefaultCriticalThreshold);

			// ram defaults
			storeValueIfNotSet(sRamWarningThresholdKey,
					sDefaultWarningThreshold);
			storeValueIfNotSet(sRamCriticalThresholdKey,
					sDefaultCriticalThreshold);

			// storage defaults
			storeValueIfNotSet(sStorageWarningThresholdKey,
					sDefaultWarningThreshold);
			storeValueIfNotSet(sStorageCriticalThresholdKey,
					sDefaultCriticalThreshold);
		}
	}

	private static void storeValueIfNotSet(String key, int value) {
		if (SharedPreferenceHelper.getIntPreference(key, Integer.MAX_VALUE) == Integer.MAX_VALUE) {
			SharedPreferenceHelper.putIntPreference(key, value);
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

}
