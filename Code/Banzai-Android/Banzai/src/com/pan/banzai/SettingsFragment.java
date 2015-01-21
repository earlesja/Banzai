package com.pan.banzai;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private ThresholdView mCpuThresholdView;
	private ThresholdView mRamThresholdView;
	private ThresholdView mStorageThresholdView;
	private EditText mQueueLengthInput;
	private EditText mGraphTimeFrameInput;

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
		mCpuThresholdView = (ThresholdView) view
				.findViewById(R.id.cpuThreshold);
		mCpuThresholdView.setThresholds(DefaultValues.getCpuWarningThreshold(),
				DefaultValues.getCpuCriticalThreshold());

		mRamThresholdView = (ThresholdView) view
				.findViewById(R.id.ramThreshold);
		mRamThresholdView.setThresholds(DefaultValues.getRamWarningThreshold(),
				DefaultValues.getRamCriticalThreshold());

		mStorageThresholdView = (ThresholdView) view
				.findViewById(R.id.storageThreshold);
		mStorageThresholdView.setThresholds(
				DefaultValues.getStorageWarningThreshold(),
				DefaultValues.getStorageCriticalThreshold());

		// set inputs
		mGraphTimeFrameInput = (EditText) view
				.findViewById(R.id.graphTimeFrameInput);
		mGraphTimeFrameInput.setText(DefaultValues.sDefaultGraphTimeFrame + "");

		mQueueLengthInput = (EditText) view
				.findViewById(R.id.diskQueWaitLengthInput);
		mQueueLengthInput.setText(DefaultValues.sDefaultDiskQueueWaitLength
				+ "");

		// attach listeners to buttons
		((Button) view.findViewById(R.id.saveSettingsButton))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						saveSettings();
						Toast.makeText(getActivity(),
								getString(R.string.settings_saved),
								Toast.LENGTH_SHORT).show();
					}
				});
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void saveSettings() {

		DefaultValues.putCpuWarningThreshold(mCpuThresholdView
				.getWarningThreshold());
		DefaultValues.putCpuCriticalThreshold(mCpuThresholdView
				.getCriticalThreshold());

		DefaultValues.putRamWarningThreshold(mRamThresholdView
				.getWarningThreshold());
		DefaultValues.putRamCriticalThreshold(mRamThresholdView
				.getCriticalThreshold());

		DefaultValues.putStorageWarningThreshold(mStorageThresholdView
				.getWarningThreshold());
		DefaultValues.putStorageCriticalThreshold(mStorageThresholdView
				.getCriticalThreshold());

		DefaultValues.putQueueWaitLength(Integer.parseInt(mQueueLengthInput
				.getText().toString()));

		DefaultValues.putGraphTimeFrame(Integer.parseInt(mGraphTimeFrameInput
				.getText().toString()));
	}
}
