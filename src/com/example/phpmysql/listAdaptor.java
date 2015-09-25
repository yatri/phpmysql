package com.example.phpmysql;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listAdaptor extends ArrayAdapter {
	private List list = new ArrayList();

	public listAdaptor(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	static class ImgHolder {
		ImageView IMG;
		TextView NAME;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row;
		row = convertView;
		ImgHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.drawer_list_item, parent, false);
			holder = new ImgHolder();
			holder.IMG = (ImageView) row.findViewById(R.id.icon);
			holder.NAME = (TextView) row.findViewById(R.id.title);
			
			row.setTag(holder);
		} else {
			holder = (ImgHolder) row.getTag();

		}
		Customlist cList = (Customlist) getItem(position);
		holder.IMG.setImageResource(cList.getFruit_resource());
		holder.NAME.setText(cList.getFruit_name());
		
		return row;
	}
}
