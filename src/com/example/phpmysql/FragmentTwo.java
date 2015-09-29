package com.example.phpmysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTwo extends Fragment {

	private Button submit;
	private Button cancel;
	private EditText name, tole, comment;
	private Spinner district, vdc, ward;
	String[] items = new String[] { "Select ward", "1", "2", "3", "4", "5",
			"6", "7", "8", "9" };
	private static final String TABLE_NAME = "district";
	private static final String TABLE_NAME_VDC = "vdclist";
	private SQLiteDatabase dataBase;
	DataBaseHelper myDbHelper;
	List<String> districtSpinner = new ArrayList<String>();
	List<String> vdcSpinner = new ArrayList<String>();

	public FragmentTwo() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout_two, container,
				false);

		submit = (Button) view.findViewById(R.id.submit);
		cancel = (Button) view.findViewById(R.id.cancel);
		name = (EditText) view.findViewById(R.id.name);
		tole = (EditText) view.findViewById(R.id.tole);
		comment = (EditText) view.findViewById(R.id.comment);
		district = (Spinner) view.findViewById(R.id.district);
		vdc = (Spinner) view.findViewById(R.id.vdcmun);
		ward = (Spinner) view.findViewById(R.id.ward);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, items);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ward.setAdapter(dataAdapter);

		myDbHelper = new DataBaseHelper(getActivity());
		dataBase = myDbHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		if (mCursor.moveToFirst()) {
			do {
				districtSpinner.add(mCursor.getString(mCursor
						.getColumnIndex("district_name")));
				// Log.d("districtID", mCursor.getString(mCursor
				// .getColumnIndex("district_id")));
				// Log.d("districtName", mCursor.getString(mCursor
				// .getColumnIndex("district_name")));

			} while (mCursor.moveToNext());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapterdist = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, districtSpinner);

		// Drop down layout style - list view with radio button
		dataAdapterdist
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		district.setAdapter(dataAdapterdist);
		

		Cursor mCursorvdc = dataBase.rawQuery(
				"SELECT * FROM " + TABLE_NAME_VDC, null);
			System.out.println(mCursorvdc);
		if (mCursorvdc.moveToFirst()) {
			do {
				vdcSpinner.add(mCursorvdc.getString(mCursorvdc
						.getColumnIndex("vdc_name")));
				 Log.d("vdcname", mCursorvdc.getString(mCursorvdc
							.getColumnIndex("vdc_name")));

			} while (mCursorvdc.moveToNext());
		}

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdaptervdc = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, vdcSpinner);

		// Drop down layout style - list view with radio button
		dataAdaptervdc
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		vdc.setAdapter(dataAdaptervdc);

		district.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				Toast.makeText(getActivity(), "You Click Select",
						Toast.LENGTH_SHORT).show();

			}

			public void onNothingSelected(AdapterView<?> arg0) {// do nothing
			}

		});
		
		vdc.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				Toast.makeText(getActivity(), "You Click vdc Select",
						Toast.LENGTH_SHORT).show();

			}

			public void onNothingSelected(AdapterView<?> arg0) {// do nothing
			}

		});

		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "You Click Submit",
						Toast.LENGTH_SHORT).show();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "You Click cancel",
						Toast.LENGTH_SHORT).show();
			}
		});

		/*
		 * Spinner spinner = (Spinner) getView().findViewById(R.id.district); //
		 * Create an ArrayAdapter using the string array and a default spinner
		 * layout ArrayAdapter<CharSequence> adapter =
		 * ArrayAdapter.createFromResource(getActivity(), R.array.district,
		 * android.R.layout.simple_spinner_item); // Specify the layout to use
		 * when the list of choices appears
		 * adapter.setDropDownViewResource(android
		 * .R.layout.simple_spinner_dropdown_item); // Apply the adapter to the
		 * spinner spinner.setAdapter(adapter);
		 */

		// ivIcon=(ImageView)view.findViewById(R.id.frag2_icon);
		// tvItemName=(TextView)view.findViewById(R.id.frag2_text);
		//
		// tvItemName.setText(getArguments().getString(ITEM_NAME));
		// ivIcon.setImageDrawable(view.getResources().getDrawable(
		// getArguments().getInt(IMAGE_RESOURCE_ID)));
		return view;
	}

}
