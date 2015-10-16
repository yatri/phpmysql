package com.example.liquor;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdaptor extends BaseAdapter{
	private ArrayList<Category> catlist;
    private LayoutInflater layoutInflater;
    public CategoryAdaptor(Context context, ArrayList<Category> catlist) {
        this.catlist = catlist;
        layoutInflater = LayoutInflater.from(context);
    }
	@Override
	public int getCount() {
		 return catlist.size();
	}

	@Override
	public Object getItem(int position) {
		return catlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		return null;
	}
	static class ViewHolder {
        TextView headlineView;
        
    }

}
