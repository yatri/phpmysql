package com.example.liquor;

import java.util.ArrayList;

import com.example.setup.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.example.customizedlist.R;

public class CustomListAdapter extends BaseAdapter {
	private ArrayList<ListItemCustom> listData;
	private LayoutInflater layoutInflater;
	Context ctx;
	
	public CustomListAdapter(Context context, ArrayList<ListItemCustom> listData) {
		 ctx = context;
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	ListItemCustom getSingleListItem(int position){
		return ((ListItemCustom) getItem(position));
	}
	int loader = R.drawable.loader;
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = layoutInflater
					.inflate(R.layout.list_row_layout, null);
			holder = new ViewHolder();
			holder.headlineView = (TextView) convertView
					.findViewById(R.id.title);
			holder.reportedDateView = (TextView) convertView
					.findViewById(R.id.date);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.thumbImage);
			holder.stars = (RatingBar) convertView.findViewById(R.id.ratingBar);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ListItemCustom singleitem  = getSingleListItem(position);
		String image_url =singleitem.getUrl();
		ListItemCustom newsItem = listData.get(position);
		holder.headlineView.setText(newsItem.getHeadline());
		// holder.reporterNameView.setText("By, " + newsItem.getReporterName());
		holder.reportedDateView.setText(newsItem.getDate());
		ImageLoader imgLoader = new ImageLoader(ctx);
		
		if (holder.imageView != null) {
			imgLoader.DisplayImage(image_url, loader, holder.imageView);
			// new ImageDownloaderTask(holder.imageView)
			// .execute(newsItem.getUrl());
			
		}

		return convertView;
	}

	static class ViewHolder {
		RatingBar stars;
		TextView headlineView;
		TextView reportedDateView;
		ImageView imageView;
	}
}
