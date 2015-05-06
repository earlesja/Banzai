package com.pan.banzai;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class DashboardFragment extends Fragment {
	private static int[] APP_CPU_METRICIDS = new int[] { 79, 80, 81 };
	private static int[] APP_RAM_METRICIDS = new int[] { 85, 86, 87 };
	private static int[] APP_DISK_METRICIDS = new int[] { 93, 94, 95 };
	private static int[] APP_SERVERIDS = new int[] { 23, 24, 25 };
	private static String[] APP_SERVER_NAMES = new String[] { "Prod1App1",
			"Prod1App2", "Prod1App3" };
	private static int[] WEB_CPU_METRICIDS = new int[] { 82, 83, 84 };
	private static int[] WEB_RAM_METRICIDS = new int[] { 88, 89, 90 };
	private static int[] WEB_DISK_METRICIDS = new int[] { 96, 97, 98 };
	private static int[] WEB_SERVERIDS = new int[] { 26, 27, 28 };
	private static String[] WEB_SERVER_NAMES = new String[] { "Prod1Web1",
			"Prod1Web2", "Prod1Web3" };
	private static int[] DB_CPU_METRICIDS = new int[] { 91 };
	private static int[] DB_RAM_METRICIDS = new int[] { 92 };
	private static int[] DB_DISK_METRICIDS = new int[] { 99 };
	private static int[] DB_SERVERIDS = new int[] { 37 };
	private static String[] DB_SERVER_NAMES = new String[] { "ProdDB1" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// inflate the view
		View view = inflater.inflate(R.layout.fragment_dashboard, container,
				false);

		// add the adapter to the expandable list view
		ExpandableListView serverStatusList = (ExpandableListView) view
				.findViewById(R.id.expandableListView);
		ExpandableListAdapter adapter = new ServerStatusExpandableListAdapter(
					getActivity(), getData());
		serverStatusList.setAdapter(adapter);
		
		for(int i=0; i<adapter.getGroupCount(); i++){
			serverStatusList.expandGroup(i);
		}

		
		ActionBar bar = getActivity().getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		bar.setListNavigationCallbacks(null, null);
		
		return view;
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

	private ArrayList<ServerTier> getData() {
		ArrayList<ServerTier> statuses = new ArrayList<ServerTier>();

		statuses.add(new ServerTier("App", APP_CPU_METRICIDS,
				APP_RAM_METRICIDS, APP_DISK_METRICIDS, APP_SERVERIDS,
				APP_SERVER_NAMES));

		statuses.add(new ServerTier("Web", WEB_CPU_METRICIDS,
				WEB_RAM_METRICIDS, WEB_DISK_METRICIDS, WEB_SERVERIDS,
				WEB_SERVER_NAMES));

		statuses.add(new ServerTier("DB", DB_CPU_METRICIDS, DB_RAM_METRICIDS,
				DB_DISK_METRICIDS, DB_SERVERIDS, DB_SERVER_NAMES));
		return statuses;
	}
}
