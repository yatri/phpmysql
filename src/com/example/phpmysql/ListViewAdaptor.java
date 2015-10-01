package com.example.phpmysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdaptor extends BaseAdapter {
	TextView sn, FullName, district, vdc, tole, ward, comment;
	public ArrayList<HashMap<String, String>> list;
	Activity activity;

	public ListViewAdaptor(Activity activity,
			ArrayList<HashMap<String, String>> list) {
		super();
		this.activity = activity;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.data_list, null);
			sn = (TextView) convertView.findViewById(R.id.sn);
			FullName = (TextView) convertView.findViewById(R.id.FullName);
			district = (TextView) convertView.findViewById(R.id.district);
			vdc = (TextView) convertView.findViewById(R.id.vdc);
			tole = (TextView) convertView.findViewById(R.id.tole);
			ward = (TextView) convertView.findViewById(R.id.ward);
			comment = (TextView) convertView.findViewById(R.id.comment);
		}
		HashMap<String, String> map = list.get(position);
		sn.setText(map.get("commentid"));
		FullName.setText(map.get("name"));
		district.setText(map.get("district"));
		vdc.setText(map.get("vdc"));
		tole.setText(map.get("tole"));
		ward.setText(map.get("ward"));
		comment.setText(map.get("comment"));
		return convertView;
	}

}
