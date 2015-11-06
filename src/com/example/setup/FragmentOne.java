package com.example.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.setup.R;
import com.example.liquor.*;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentOne extends Fragment {

	ImageView ivIcon;
	TextView tvItemName;
	private GridviewAdapter mAdapter;

	private ArrayList<String> listCountry;
	private ArrayList<Integer> listFlag;

	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";

	JSONParser jsonParser = new JSONParser();
	String TAG_URL = "imageurl";
	String urllist = "";
	String headline = "";
	String productid = "";
	String TAG_HEADLINES = "imageheading";
	String TAG_ID = "prod_id";
	private int jsonstatus = 0;
	private static String login_page = "http://192.168.40.80:81/loginapi/index.php/loadtopproduct";
	private ProgressDialog pDialog;
	ArrayList<ListItemCustom> listMockData = new ArrayList<ListItemCustom>();
	private GridView gridView;

	public FragmentOne() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_one, container,
				false);		
		prepareList();
		mAdapter = new GridviewAdapter(this.getActivity(), listCountry, listFlag);	
		gridView = (GridView) view.findViewById(R.id.productCols);
		gridView.setAdapter(mAdapter);
		
		 gridView.setOnItemClickListener(new OnItemClickListener() 
	        {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					Toast.makeText(getActivity(), mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
				}
			});
		return view;
	}

	public void prepareList() {
		listCountry = new ArrayList<String>();

		listCountry.add("india");
		listCountry.add("Brazil");
		listCountry.add("Canada");
		listCountry.add("China");
		listCountry.add("France");
		listCountry.add("Germany");
		listCountry.add("Iran");
		listCountry.add("Italy");
		listCountry.add("Japan");
		listCountry.add("Korea");
		listCountry.add("Mexico");
		listCountry.add("Netherlands");
		listCountry.add("Portugal");
		listCountry.add("Russia");
		listCountry.add("Saudi Arabia");
		listCountry.add("Spain");
		listCountry.add("Turkey");
		listCountry.add("United Kingdom");
		//listCountry.add("United States");

		listFlag = new ArrayList<Integer>();
		listFlag.add(R.drawable.india);
		listFlag.add(R.drawable.brazil);
		listFlag.add(R.drawable.canada);
		listFlag.add(R.drawable.china);
		listFlag.add(R.drawable.france);
		listFlag.add(R.drawable.germany);
		listFlag.add(R.drawable.iran);
		listFlag.add(R.drawable.italy);
		listFlag.add(R.drawable.japan);
		listFlag.add(R.drawable.korea);
		listFlag.add(R.drawable.mexico);
		listFlag.add(R.drawable.netherlands);
		listFlag.add(R.drawable.portugal);
		listFlag.add(R.drawable.russia);
		listFlag.add(R.drawable.saudi_arabia);
		listFlag.add(R.drawable.spain);
		listFlag.add(R.drawable.turkey);
		listFlag.add(R.drawable.united_kingdom);
		//listFlag.add(R.drawable.united_states);
	}
}
