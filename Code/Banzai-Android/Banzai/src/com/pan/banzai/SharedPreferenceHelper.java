package com.pan.banzai;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

	private static SharedPreferences mSharedPrefs;
	public static SharedPreferenceHelper mHelper;

	public static void setSharedPreferences(Context context) {
		mSharedPrefs = context.getSharedPreferences(
				context.getString(R.string.preference_file_key),
				Context.MODE_PRIVATE);
	}

	public static SharedPreferences getSharedPrefs() {
		return mSharedPrefs;
	}

	public static boolean isInit() {
		return mSharedPrefs != null;
	}

	public static SharedPreferences.Editor getEditor() {
		if (isInit()) {
			return mSharedPrefs.edit();
		}
		return null;
	}

	public static int getIntPreference(String key, int defValue) {
		if (isInit()) {
			return mSharedPrefs.getInt(key, defValue);
		}
		return defValue;
	}

	public static boolean putIntPreference(String key, int value) {
		if (!isInit()) {
			return false;
		}

		SharedPreferences.Editor editor = mSharedPrefs.edit();
		editor.putInt(key, value);
		editor.apply();
		return true;
	}

	public static String getStringPreference(String key, String defValue) {
		if (isInit()) {
			return mSharedPrefs.getString(key, defValue);
		}
		return defValue;
	}

	public static boolean putStringPreference(String key, String value) {
		if (!isInit()) {
			return false;
		}

		SharedPreferences.Editor editor = mSharedPrefs.edit();
		editor.putString(key, value);
		editor.commit();
		return true;
	}

	public static boolean getBoolPreference(String key, boolean defValue) {
		if(isInit()){
			return mSharedPrefs.getBoolean(key, defValue);
		} 
		return defValue;
	}

	public static boolean putBoolPreference(String key, boolean value) {
		if(!isInit()){
			return false;
		}
		SharedPreferences.Editor editor = mSharedPrefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
		return true;
	}
}
