package com.example.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.setup.R;

import android.R.string;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FragmentTwo extends Fragment {

	private Button submit;
	private Button cancel;
	private String nameval, toleval, commentval,hiddentextval;
	private int distval, vdcval, wardval, userid;
	private EditText name, tole, comment,hidden;
	private Spinner district, vdc, ward;
	String[] items = new String[] { "Select ward", "1", "2", "3", "4", "5",
			"6", "7", "8", "9" };
	private static final String TABLE_NAME = "district";
	private static final String TABLE_COMMENT = "comment";
	private static final String TABLE_NAME_VDC = "vdclist";
	private static final String USER_ID = "use_id";
	private SQLiteDatabase dataBase;
	private int distsel;
	private int vdcsel;
	private int editid;
	DatabaseHandler myDbHelper;
	List<String> districtSpinner = new ArrayList<String>();
	List<String> vdcSpinner = new ArrayList<String>();
	private String editdataname = null;
	FormData editformdata = new FormData();

	public FragmentTwo() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle bundle = this.getArguments();
		Log.d("arg", Integer.toString(bundle.getInt("posid", 0)));
		int editid = bundle.getInt("posid", 0);
		View view = inflater.inflate(R.layout.fragment_layout_two, container,
				false);

		SharedPreferences userdata = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		userid = userdata.getInt(USER_ID, 0);
		// System.out.println(userid);
		submit = (Button) view.findViewById(R.id.submit);
		cancel = (Button) view.findViewById(R.id.cancel);
		name = (EditText) view.findViewById(R.id.name);
		hidden = (EditText) view.findViewById(R.id.hidden);
		tole = (EditText) view.findViewById(R.id.tole);
		comment = (EditText) view.findViewById(R.id.comment);
		district = (Spinner) view.findViewById(R.id.district);
		vdc = (Spinner) view.findViewById(R.id.vdcmun);
		ward = (Spinner) view.findViewById(R.id.ward);

		// name.setText(editdataname);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, items);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		ward.setAdapter(dataAdapter);
		List<District> distlist = new ArrayList<District>();
		distlist.add(new District(0, "Select District"));
		myDbHelper = new DatabaseHandler(getActivity());
		dataBase = myDbHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM  district ORDER BY district_name desc", null);
		if (mCursor.moveToFirst()) {
			do {
				distlist.add(new District(mCursor.getInt(mCursor
						.getColumnIndex("district_id")), mCursor
						.getString(mCursor.getColumnIndex("district_name"))));
				// districtSpinner.add(mCursor.getString(mCursor.getColumnIndex("district_name")));
				// Log.d("districtID", mCursor.getString(mCursor
				// .getColumnIndex("district_id")));
				// Log.d("districtName", mCursor.getString(mCursor
				// .getColumnIndex("district_name")));

			} while (mCursor.moveToNext());
		}
		ArrayAdapter<District> dataAdapterdist = new ArrayAdapter<District>(
				getActivity(), android.R.layout.simple_spinner_item, distlist);
		// Creating adapter for spinner
		// ArrayAdapter<String> dataAdapterdist = new ArrayAdapter<String>(
		// getActivity(), android.R.layout.simple_spinner_item,
		// districtSpinner);

		// Drop down layout style - list view with radio button
		dataAdapterdist
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		district.setAdapter(dataAdapterdist);

		List<Vdc> vdclist = new ArrayList<Vdc>();
		vdclist.add(new Vdc(0, "Select VDC", 0));
		Cursor mCursorvdc = dataBase.rawQuery(
				"SELECT * FROM " + TABLE_NAME_VDC, null);
		// System.out.println(mCursorvdc);
		if (mCursorvdc.moveToFirst()) {
			do {
				vdclist.add(new Vdc(mCursorvdc.getInt(mCursorvdc
						.getColumnIndex("vdc_id")), mCursorvdc
						.getString(mCursorvdc.getColumnIndex("vdc_name")),
						mCursorvdc.getInt(mCursorvdc
								.getColumnIndex("district_id"))));

				// vdcSpinner.add(mCursorvdc.getString(mCursorvdc
				// .getColumnIndex("vdc_name")));
				// Log.d("vdcname", mCursorvdc.getString(mCursorvdc
				// .getColumnIndex("vdc_name")));

			} while (mCursorvdc.moveToNext());
		}

		ArrayAdapter<Vdc> dataAdaptervdc = new ArrayAdapter<Vdc>(getActivity(),
				android.R.layout.simple_spinner_item, vdclist);

		// Creating adapter for spinner
		// ArrayAdapter<String> dataAdaptervdc = new ArrayAdapter<String>(
		// getActivity(), android.R.layout.simple_spinner_item, vdcSpinner);

		// Drop down layout style - list view with radio button
		dataAdaptervdc
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		vdc.setAdapter(dataAdaptervdc);

		if (editid != 0) {

			myDbHelper = new DatabaseHandler(getActivity());
			dataBase = myDbHelper.getWritableDatabase();
			Cursor commentDetail = dataBase.rawQuery("SELECT * FROM "
					+ TABLE_COMMENT + " WHERE comment_id = " + editid, null);

			System.out.println(commentDetail);
			if (commentDetail.moveToFirst()){
				do {
					name.setText(commentDetail.getString(commentDetail
							.getColumnIndex("first_name")));
					hidden.setText(String.valueOf(editid));
					tole.setText(commentDetail.getString(commentDetail
							.getColumnIndex("tole")));
					ward.setSelection(commentDetail.getInt(commentDetail
							.getColumnIndex("ward_no")) - 1);
					comment.setText(commentDetail.getString(commentDetail
							.getColumnIndex("comment")));
					 int distid = commentDetail.getInt(commentDetail
					 .getColumnIndex("district_id"));
					 
					 List<Vdc> vdclistnew = new ArrayList<Vdc>();
					 
					 int vdcid = commentDetail.getInt(commentDetail
							 .getColumnIndex("vdc_id"));
					
					
					for (District a : distlist) {
						if(distid == a.getDistrict_id()){
							district.setSelection(distlist.indexOf(a));
							break;
						}
					}
					for (Vdc a : vdclistnew) {
						if(vdcid == a.getVdc_id()){
							vdc.setSelection(2);
							System.out.println(vdclistnew.indexOf(a)+1);
							break;
						}
					}
					
					
				} while (commentDetail.moveToNext());
			}
		}else{
			hidden.setText(String.valueOf(0));
		}

		district.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				District dist = (District) parentView
						.getItemAtPosition(position);

				int dist_id = dist.getDistrict_id();
				distsel = dist_id;
				// System.out.println(distsel);
				List<Vdc> vdclist = new ArrayList<Vdc>();
				vdclist.add(new Vdc(0, "Select VDC", 0));
				if (dist_id != 0) {
					Cursor cursordistsel = dataBase.rawQuery(
							"SELECT * FROM " + TABLE_NAME_VDC
									+ " WHERE district_id =" + dist_id, null);
					if (cursordistsel.moveToFirst()) {
						do {
							vdclist.add(new Vdc(cursordistsel
									.getInt(cursordistsel
											.getColumnIndex("vdc_id")),
									cursordistsel.getString(cursordistsel
											.getColumnIndex("vdc_name")),
									cursordistsel.getInt(cursordistsel
											.getColumnIndex("district_id"))));

						} while (cursordistsel.moveToNext());
					}

				} else {
					vdclist.clear();
					vdclist.add(new Vdc(0, "Select VDC", 0));
				}
				ArrayAdapter<Vdc> dataAdaptervdc = new ArrayAdapter<Vdc>(
						getActivity(), android.R.layout.simple_spinner_item,
						vdclist);
				dataAdaptervdc
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				vdc.setAdapter(dataAdaptervdc);

			}

			public void onNothingSelected(AdapterView<?> arg0) {// do nothing
			}

		});

		vdc.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				Vdc vdcitem = (Vdc) parentView.getItemAtPosition(position);
				vdcsel = vdcitem.getVdc_id();
			}

			public void onNothingSelected(AdapterView<?> arg0) {// do nothing
			}

		});

		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hiddentextval = hidden.getText().toString();
				// System.out.println(Integer.parseInt(hiddentextval));
				// Log.d("dd","aa");
				 nameval = name.getText().toString();
				 toleval = tole.getText().toString();
				 wardval = Integer.parseInt(ward.getSelectedItem().toString());
				 commentval = comment.getText().toString();
				 FormData frmdata = new FormData(userid, nameval, distsel,
				 vdcsel, toleval, wardval, commentval);
				 DatabaseHandler db = new DatabaseHandler(getActivity());
				 String msg = "";
				if(Integer.parseInt(hiddentextval)>0){
					 db.updateComment(frmdata,Integer.parseInt(hiddentextval));
					 msg = "Data has been Updated";
				}else{
					db.addComment(frmdata);
					 msg = "Data Saved Successfully!!";
				}
				 Fragment fragment = null;
				 fragment = new FragmentThree();
				 FragmentManager frgManager = getFragmentManager();
				 frgManager.beginTransaction()
				 .replace(R.id.content_frame, fragment).commit();
				Toast.makeText(getActivity(), msg,
						Toast.LENGTH_SHORT).show();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(getActivity());
				List<FormData> commentdata = db.getAllComments();
				Log.d("comment", commentdata.toString());
				Toast.makeText(getActivity(), "You Click cancel",
						Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}
}
