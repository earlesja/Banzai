package com.pan.banzai;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pan.banzai.UtilizationFragment.UtilizationType;

public class ServerStatusExpandableListAdapter extends
		BaseExpandableListAdapter {
	public static final int sNumChildren = 1;
	public static final int sDonutInnerCircleRatio = 175;

	private ArrayList<ServerTier> mTierStatuses;
	private Context mContext;

	public ServerStatusExpandableListAdapter(Context context,
			ArrayList<ServerTier> tierStatuses) {
		mTierStatuses = tierStatuses;
		mContext = context;
	}

	@Override
	public int getGroupCount() {
		return mTierStatuses.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return sNumChildren;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mTierStatuses.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mTierStatuses.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// inflate the layout if the view doesn't exist already
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.list_group_server_status, parent, false);
		}
		// get the tier status
		ServerTier tierStatus = (ServerTier) getGroup(groupPosition);
		// set the header text to the tier name
		((TextView) convertView.findViewById(R.id.listGroupTextView))
				.setText(tierStatus.getTierName());

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// inflate the layout if the view doesn't exist yet
		View itemView = null;
		itemView = LayoutInflater.from(mContext).inflate(
				R.layout.list_item_server_status, parent, false);

		// get the tier status
		ServerTier tier = (ServerTier) getChild(groupPosition, childPosition);

		// setup the donut graphs
		DonutGraph cpuGraph = (DonutGraph) itemView.findViewById(R.id.cpuPieGraph);
		setUpDonut(cpuGraph, tier.getCpuStatusPercent(), "CPU");
		setUtilizationClickListener(cpuGraph, tier,	UtilizationType.CPU);


		DonutGraph ramGraph = (DonutGraph) itemView.findViewById(R.id.ramPieGraph);
		setUpDonut(ramGraph,tier.getRamStatusPercent(), "Memory");
		setUtilizationClickListener(ramGraph, tier, UtilizationType.RAM);

		DonutGraph storageGraph = (DonutGraph) itemView.findViewById(R.id.storagePieGraph);
		setUpDonut(storageGraph, tier.getStorageStatusPercent(), "Disk");
		setUtilizationClickListener(storageGraph, tier, UtilizationType.DISK);

		return itemView;
	}

	/**
	 * Initializes a donut graph with only 2 values
	 * 
	 * @param pieGraph
	 *            The pieGraph on the layout
	 * @param value
	 *            Percentage out of 100 to show on graph
	 */
	private void setUpDonut(DonutGraph donut, float value, final String title) {
		// remove any existing slices
		donut.setPercentage(value);
	}

	private void setUtilizationClickListener(View container, final ServerTier tier,
			final UtilizationType type) {
		container.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = ((Activity) mContext).getFragmentManager();
				UtilizationFragment uFrag = new UtilizationFragment(tier, type);
				fm.beginTransaction().replace(R.id.container, uFrag)
						.addToBackStack("frag").commit();
			}
		});
	}

}
