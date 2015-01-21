package com.pan.banzai;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OsUsageFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_osusage, container,
				false);

		PieGraph pieGraph = (PieGraph) view.findViewById(R.id.osusagePieChart);
		PieSlice s1 = new PieSlice();
		s1.setColor(Color.BLACK);
		s1.setValue(25);
		pieGraph.addSlice(s1);

		PieSlice s2 = new PieSlice();
		s2.setColor(Color.RED);
		s2.setValue(25);
		pieGraph.addSlice(s2);

		PieSlice s3 = new PieSlice();
		s3.setColor(Color.YELLOW);
		s3.setValue(25);
		pieGraph.addSlice(s3);

		PieSlice s4 = new PieSlice();
		s4.setColor(Color.BLUE);
		s4.setValue(25);
		pieGraph.addSlice(s4);

		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
