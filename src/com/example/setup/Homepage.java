package com.example.setup;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.liquor.*;
import com.example.setup.R;

public class Homepage extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;
	SharedPreferences pref;
	List<DrawerItem> dataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem("Home", R.drawable.ic_action_email));
		dataList.add(new DrawerItem("Cart", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Top Selling Product",
				R.drawable.ic_action_good));
		// dataList.add(new DrawerItem("List Comment",
		// R.drawable.ic_action_gamepad));

		// dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
		// dataList.add(new DrawerItem("Cloud", R.drawable.ic_action_cloud));
		dataList.add(new DrawerItem("Product Categories",
				R.drawable.ic_action_camera));
		// dataList.add(new DrawerItem("Video", R.drawable.ic_action_video));
		// dataList.add(new DrawerItem("Groups", R.drawable.ic_action_group));
		// dataList.add(new DrawerItem("Import & Export",
		// R.drawable.ic_action_import_export));
		dataList.add(new DrawerItem("Domestic Product",
				R.drawable.ic_action_about));
		dataList.add(new DrawerItem("About Us", R.drawable.ic_action_help));
		dataList.add(new DrawerItem("Logout", R.drawable.ic_action_settings));
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			SelectItem(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new FragmentOne();
			break;
		case 1:
			fragment = new FragCart();
			break;
		case 2:
			fragment = new FragmentTopSelling();
			break;
		case 3:
			fragment = new Fragmentextra();
			break;
		
		case 4:
			fragment = new FragAditional();
			break;
		case 5:
			fragment = new AboutUs();
			break;
		case 6:
			fragment = new FragmentOne();
			break;
		default:
			break;
		}

		args.putInt("listid", 0);
		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		// getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		//
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Log.d("click", "logout");
			return true;
		} else if (id == R.id.logout) {
			Log.d("click", "logout");
			pref = PreferenceManager.getDefaultSharedPreferences(this);
			pref.edit().clear().commit();
			Toast.makeText(getApplicationContext(), "you clicked logout",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;
		}
		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			SelectItem(position);

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do you want to exit ?")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// do finish
									Homepage.this.finish();
									// System.exit(0);
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// do nothing
									return;
								}
							});
			AlertDialog alert = builder.create();
			alert.show();

		}
		return super.onKeyDown(keyCode, event);
	}

}
