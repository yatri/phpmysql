package com.example.phpmysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentThree extends Fragment {
	private ArrayList<HashMap<String, String>> list;
	private ArrayList<Integer> commentid = new ArrayList<Integer>();
	ImageView ivIcon;
	TextView tvItemName;
	ListView listView;
	int deleteid;
	private String comment_id;
	private AlertDialog.Builder build;
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	public FragmentThree() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<HashMap<String, String>>();
		View view = inflater.inflate(R.layout.fragment_layout_three, container,
				false);
		listView = (ListView) view.findViewById(R.id.listView1);
		DatabaseHandler db = new DatabaseHandler(getActivity());
		List<FormData> commentdata = db.getAllComments();
		int i = 1;
		commentid.clear();
		for (FormData frmdta : commentdata) {
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("commentid", Integer.toString(frmdta.getComment_id()));
			temp.put("snkey", Integer.toString(i++));
			temp.put("name", frmdta.getName());
			temp.put("district", frmdta.getDistrict_name());
			temp.put("vdc", frmdta.getVdc_name());
			temp.put("ward", Integer.toString(frmdta.getWardno()));
			temp.put("tole", frmdta.getTole());
			temp.put("comment", frmdta.getComment());
			commentid.add(frmdta.getComment_id());
			list.add(temp);
		}

		ListViewAdaptor adapter = new ListViewAdaptor(getActivity(), list);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				Fragment fragment = null;
				bundle.putInt("posid", commentid.get(position));
				fragment = new FragmentTwo();
				fragment.setArguments(bundle);
				FragmentManager frgManager = getFragmentManager();
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();

			}

		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteid = commentid.get(position);
				build = new AlertDialog.Builder(getActivity());
				build.setTitle("Delete");
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DatabaseHandler db = new DatabaseHandler(
										getActivity());
								if (db.deleteData(deleteid) > 0) {
									Toast.makeText(getActivity(),
											"Successfully deleted Comment!!",
											Toast.LENGTH_SHORT).show();
									Fragment fragment = null;
									fragment = new FragmentThree();
									FragmentManager frgManager = getFragmentManager();
									frgManager
											.beginTransaction()
											.replace(R.id.content_frame,
													fragment).commit();
								}
							}

						});
				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});

				AlertDialog alert = build.create();
				alert.show();
				return true;
			}

		});

		return view;
	}
}
