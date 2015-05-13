package com.pan.banzai;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private ThresholdView mCpuThresholdView;
	private ThresholdView mRamThresholdView;
	private ThresholdView mStorageThresholdView;
	private Switch mNotificationsSwitch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_settings, container,
				false);

		// set threshold values
		mCpuThresholdView = (ThresholdView) view.findViewById(R.id.cpuThreshold);
		mCpuThresholdView.setThresholds(Storage.getCpuWarningThreshold(), Storage.getCpuCriticalThreshold());

		mRamThresholdView = (ThresholdView) view.findViewById(R.id.ramThreshold);
		mRamThresholdView.setThresholds(Storage.getRamWarningThreshold(),	Storage.getRamCriticalThreshold());

		mStorageThresholdView = (ThresholdView) view.findViewById(R.id.storageThreshold);
		mStorageThresholdView.setThresholds(Storage.getStorageWarningThreshold(),	Storage.getStorageCriticalThreshold());
		
		mNotificationsSwitch = (Switch) view.findViewById(R.id.notificationsSwitch);

		
		
		
		// attach listeners to buttons
		((Button) view.findViewById(R.id.saveSettingsButton))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						saveSettings();
						Toast.makeText(getActivity(), getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();
					}
				});
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void saveSettings() {

		Storage.putCpuWarningThreshold(mCpuThresholdView.getWarningThreshold());
		Storage.putCpuCriticalThreshold(mCpuThresholdView.getCriticalThreshold());

		Storage.putRamWarningThreshold(mRamThresholdView.getWarningThreshold());
		Storage.putRamCriticalThreshold(mRamThresholdView.getCriticalThreshold());

		Storage.putStorageWarningThreshold(mStorageThresholdView.getWarningThreshold());
		Storage.putStorageCriticalThreshold(mStorageThresholdView.getCriticalThreshold());
		
		Storage.putNotificationsEnabled(mNotificationsSwitch.isChecked());
	}
}
