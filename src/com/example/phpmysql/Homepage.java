package com.example.phpmysql;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Homepage extends ActionBarActivity {
	private static final String FRIST_NAME = "first_Name";
	private static final String LAST_NAME = "last_Name";
	private static final String EMAIL = "email";
	String[] menu;
	int[] navMenuIcons;
	DrawerLayout dLayout;
	ListView dList, dListr;
	ArrayAdapter<String> adapter;
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		listview = (ListView) findViewById(R.id.left_drawer);
		menu = getResources().getStringArray(R.array.nav_drawer_items);
		int[] img_resource = { R.drawable.ic_home, R.drawable.ic_people,

		R.drawable.ic_photos, R.drawable.ic_communities, R.drawable.ic_pages,
				R.drawable.ic_whats_hot };

		listAdaptor adapter = new listAdaptor(getApplicationContext(),
				R.layout.drawer_list_item);

		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		dList = (ListView) findViewById(R.id.left_drawer);
		dListr = (ListView) findViewById(R.id.right_drawer);

		// listAdaptor adapter = new
		// listAdaptor(getApplicationContext(),R.layout.drawer_list_item);
		listview.setAdapter(adapter);
		int i = 0;
		for (String Name : menu) {
			Customlist obj = new Customlist(img_resource[i], Name);
			adapter.add(obj);
			i++;
		}
		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, menu);
		// dList.setAdapter(adapter);
		// dListr.setAdapter(adapter);
		listview.setSelector(android.R.color.holo_blue_dark);
		// dListr.setSelector(android.R.color.holo_blue_dark);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dLayout.closeDrawers();
				Bundle args = new Bundle();
				args.putString("Menu", menu[position]);
				Fragment detail = new DetailFragment();
				detail.setArguments(args);
				android.app.FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, detail).commit();

			}

		});

		// dListr.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// dLayout.closeDrawers();
		// Bundle args = new Bundle();
		// args.putString("Menu", menu[position]);
		// Fragment detail = new DetailFragment();
		// detail.setArguments(args);
		// android.app.FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.content_frame, detail).commit();
		//
		// }
		//
		// });
		// TextView fname = (TextView) findViewById(R.id.firstname);
		// TextView lname = (TextView) findViewById(R.id.lastname);
		// TextView email = (TextView) findViewById(R.id.email);
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", 0); // 0 - for private mode
		Editor editor = pref.edit();
		// fname.setText(pref.getString(FRIST_NAME, "No First Name"));
		// lname.setText(pref.getString(LAST_NAME, "No First Name"));
		// email.setText(pref.getString(EMAIL, "No First Name"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homepage, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
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
