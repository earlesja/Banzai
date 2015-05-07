package com.pan.banzai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pan.banzai.UtilizationFragment.UtilizationType;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
	
	private static final Map<Integer, Float> APP_CPU_METRICS;
	private static final Map<Integer, Float> APP_RAM_METRICS;
	private static final Map<Integer, Float> APP_DISK_METRICS;
	
	private static final Map<Integer, Float> WEB_CPU_METRICS;
	private static final Map<Integer, Float> WEB_RAM_METRICS;
	private static final Map<Integer, Float> WEB_DISK_METRICS;
	
	private static final Map<Integer, Float> DB_CPU_METRICS;
	private static final Map<Integer, Float> DB_RAM_METRICS;
	private static final Map<Integer, Float> DB_DISK_METRICS;
    static
    {
        APP_CPU_METRICS = new HashMap<Integer, Float>();
        APP_RAM_METRICS = new HashMap<Integer, Float>();
        APP_DISK_METRICS = new HashMap<Integer, Float>();
        
        WEB_CPU_METRICS = new HashMap<Integer, Float>();
        WEB_RAM_METRICS = new HashMap<Integer, Float>();
        WEB_DISK_METRICS = new HashMap<Integer, Float>();
        
        DB_CPU_METRICS = new HashMap<Integer, Float>();
        DB_RAM_METRICS = new HashMap<Integer, Float>();
        DB_DISK_METRICS = new HashMap<Integer, Float>();
        
        for(int i=0; i<APP_CPU_METRICIDS.length;i++){
        	APP_CPU_METRICS.put(APP_CPU_METRICIDS[i], 0f);
        }
        for(int i=0; i<APP_RAM_METRICIDS.length;i++){
        	APP_RAM_METRICS.put(APP_RAM_METRICIDS[i], 0f);
        }
        for(int i=0; i<APP_DISK_METRICIDS.length;i++){
        	APP_DISK_METRICS.put(APP_DISK_METRICIDS[i], 0f);
        }
        
        for(int i=0; i<WEB_CPU_METRICIDS.length;i++){
        	WEB_CPU_METRICS.put(WEB_CPU_METRICIDS[i], 0f);
        }
        for(int i=0; i<WEB_RAM_METRICIDS.length;i++){
        	WEB_RAM_METRICS.put(WEB_RAM_METRICIDS[i], 0f);
        }
        for(int i=0; i<WEB_DISK_METRICIDS.length;i++){
        	WEB_DISK_METRICS.put(WEB_DISK_METRICIDS[i], 0f);
        }
        
        for(int i=0; i<DB_CPU_METRICIDS.length;i++){
        	DB_CPU_METRICS.put(DB_CPU_METRICIDS[i], 0f);
        }
        for(int i=0; i<DB_RAM_METRICIDS.length;i++){
        	DB_RAM_METRICS.put(DB_RAM_METRICIDS[i], 0f);
        }
        for(int i=0; i<DB_DISK_METRICIDS.length;i++){
        	DB_DISK_METRICS.put(DB_DISK_METRICIDS[i], 0f);
        }

    }
    
    

	ExpandableListAdapter adapter;

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
		adapter = new ServerStatusExpandableListAdapter(
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
	
	public void updateContent(ArrayList<String> data){
		int metricId = Integer.parseInt(data.get(0)); 
		float value = Float.parseFloat(data.get(1));
		
		if(adapter != null){
			ServerTier tier;
			Boolean updated = false;
			
			if(DashboardFragment.APP_CPU_METRICS.containsKey(metricId)){
				DashboardFragment.APP_CPU_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.APP_CPU_METRICS);
				tier = (ServerTier) adapter.getGroup(0);
				tier.setCpuStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.APP_RAM_METRICS.containsKey(metricId)){
				DashboardFragment.APP_RAM_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.APP_RAM_METRICS);
				tier = (ServerTier) adapter.getGroup(0);
				tier.setRamStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.APP_DISK_METRICS.containsKey(metricId)){
				DashboardFragment.APP_DISK_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.APP_DISK_METRICS);
				tier = (ServerTier) adapter.getGroup(0);
				tier.setDiskStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.WEB_CPU_METRICS.containsKey(metricId)){
				DashboardFragment.WEB_CPU_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.WEB_CPU_METRICS);
				tier = (ServerTier) adapter.getGroup(1);
				tier.setCpuStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.WEB_RAM_METRICS.containsKey(metricId)){
				DashboardFragment.WEB_RAM_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.WEB_RAM_METRICS);
				tier = (ServerTier) adapter.getGroup(1);
				tier.setRamStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.WEB_DISK_METRICS.containsKey(metricId)){
				DashboardFragment.WEB_DISK_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.WEB_DISK_METRICS);
				tier = (ServerTier) adapter.getGroup(1);
				tier.setDiskStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.DB_CPU_METRICS.containsKey(metricId)){
				DashboardFragment.DB_CPU_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.DB_CPU_METRICS);
				tier = (ServerTier) adapter.getGroup(2);
				tier.setCpuStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.DB_RAM_METRICS.containsKey(metricId)){
				DashboardFragment.DB_RAM_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.DB_RAM_METRICS);
				tier = (ServerTier) adapter.getGroup(2);
				tier.setRamStatusPercent(average);
				updated = true;
			}else if(DashboardFragment.DB_DISK_METRICS.containsKey(metricId)){
				DashboardFragment.DB_DISK_METRICS.put(metricId, value);
				float average = averageMetrics(DashboardFragment.DB_DISK_METRICS);
				tier = (ServerTier) adapter.getGroup(2);
				tier.setDiskStatusPercent(average);
				updated = true;
			}
			
			if(updated){
				((BaseExpandableListAdapter) adapter).notifyDataSetChanged();
			}
		}
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
	
	private float averageMetrics(Map<Integer, Float> metrics){
		float total = 0;
		
		for(Integer key : metrics.keySet()){
			total += metrics.get(key);
		}
		
		return total/metrics.size();
	}
}
