package com.pan.banzai;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NavListViewOnItemClickListener implements OnItemClickListener {

	private Activity mContext;
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private int selectedNavItem = 0;

	public NavListViewOnItemClickListener(Activity context,
			ListView drawerList, DrawerLayout drawerLayout) {
		mContext = context;
		mDrawerList = drawerList;
		mDrawerLayout = drawerLayout;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectItem(position);
	}

	private void selectItem(int position) {

		Log.d("XX", "" + mDrawerList.getSelectedItemPosition());
		if (position == selectedNavItem) {
			return;
		}

		// replace fragment container with new page
		FragmentManager fm = mContext.getFragmentManager();
		Fragment frag = MainActivity.sDashboardFragment;

		switch (position) {
		case 0:
			frag = MainActivity.sDashboardFragment;
			break;
		case 1:
			// OS usage
			break;
		case 2:
			// browser usage
			break;
		case 3:
			// system errors
			break;
		case 4:
			// users logged in
			break;
		case 5:
			// settings
			frag = MainActivity.sSettingsFragment;
			break;
		default:
			break;
		}
		selectedNavItem = position;
		fm.beginTransaction().replace(R.id.container, frag)
				.addToBackStack("frag").commit();

		mContext.setTitle((String) mDrawerList.getItemAtPosition(position));
		mDrawerLayout.closeDrawer(mDrawerList);

	}
}
