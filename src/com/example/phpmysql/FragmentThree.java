package com.example.phpmysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentThree extends Fragment {
	private ArrayList<HashMap<String, String>> list;
	ImageView ivIcon;
	TextView tvItemName;
	ListView listView;

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
		for (FormData frmdta : commentdata) {
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("commentid", Integer.toString(frmdta.getComment_id()));
			temp.put("name", frmdta.getName());
			temp.put("district", Integer.toString(frmdta.getDistrict_id()));
			temp.put("vdc", Integer.toString(frmdta.getVdcid()));
			temp.put("ward", Integer.toString(frmdta.getWardno()));
			temp.put("tole", frmdta.getTole());
			temp.put("comment", frmdta.getComment());
			list.add(temp);
		}

		ListViewAdaptor adapter = new ListViewAdaptor(getActivity(), list);
		listView.setAdapter(adapter);
		//Log.d("listdata", list.toString());
		return view;
	}
}
