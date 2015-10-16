package com.example.liquor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.phpmysql.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class FragmentProdCategory extends Fragment {
	int categoryid;
	JSONParser jsonParser = new JSONParser();
	String TAG_URL = "imageurl";
	String urllist = "";
	String headline = "";
	String productid = "";
	String TAG_HEADLINES = "imageheading";
	String TAG_ID = "prod_id";
	private int jsonstatus = 0;
	private static String login_page = "http://192.168.40.80:81/loginapi/index.php/loadimage";
	private ProgressDialog pDialog;
	private SwipeRefreshLayout swipeRefreshLayout;
	// ListItemCustom newsData = new ListItemCustom();
	ArrayList<ListItemCustom> listMockData = new ArrayList<ListItemCustom>();

	public FragmentProdCategory() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.product_loader, container, false);
		Bundle bundle = this.getArguments();
		int cat_id = bundle.getInt("id", 999);
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

					@Override
					public void onRefresh() {
						// TODO Auto-generated method stub
						Log.d("refresh", "ok");
						new FetchData().execute();
						// swipeRefreshLayout.setRefreshing(false);
					}

				});
		new FetchData().execute();

		return view;
	}

	class FetchData extends AsyncTask<String, String, String> {

		JSONArray imageurl;
		JSONArray imageheadline;
		JSONArray productidarry;
		private Context mContext;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("cat_id", String
					.valueOf(categoryid)));

			// Log.d("state", "beforehttp");
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(login_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				try {
					urllist = json.getString(TAG_URL);
					headline = json.getString(TAG_HEADLINES);
					productid = json.getString(TAG_ID);
					imageurl = new JSONArray(urllist);
					imageheadline = new JSONArray(headline);
					productidarry = new JSONArray(productid);
					Log.d("productid", productidarry.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			if (jsonstatus == 1) {
				Log.d("status", String.valueOf(jsonstatus));
				Toast.makeText(getActivity(),
						"Server Error!! Please Try Again Later.",
						Toast.LENGTH_LONG).show();
			} else {
				listMockData.clear();
				for (int i = 0; i < imageurl.length(); i++) {
					ListItemCustom newsData = new ListItemCustom();
					try {

						// Log.d("url",imageurl.getString(i));
						// Log.d("headline",imageheadline.getString(i));
						newsData.setProductID(productidarry.getInt(i));
						newsData.setUrl(imageurl.getString(i));
						newsData.setHeadline(imageheadline.getString(i));
						// newsData.setReporterName("Ravindra ");
						newsData.setDate("May 26, 2013, 13:35");

					} catch (JSONException e) {
						e.printStackTrace();
					}
					listMockData.add(newsData);
				}
				final ListView listView = (ListView) getView().findViewById(
						R.id.custom_list);

				listView.setAdapter(new CustomListAdapter(getActivity(),
						listMockData));
				swipeRefreshLayout.setRefreshing(false);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> a, View v,
							int position, long id) {
						ListItemCustom newsData = (ListItemCustom) listView
								.getItemAtPosition(position);
						Bundle bundle = new Bundle();
						Fragment fragment = null;
						bundle.putInt("prod_id", newsData.getProductID());
						bundle.putInt("category_id", categoryid);
						bundle.putInt("whichpage", 2);
						fragment = new FragmentProdDetail();
						fragment.setArguments(bundle);
						FragmentManager frgManager = getFragmentManager();
						frgManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();

						// Toast.makeText(getActivity(),
						// "Selected :" + " " + newsData,
						// Toast.LENGTH_LONG).show();
					}
				});
				// Log.d("headline", listMockData.toString());
				// Toast.makeText(getApplicationContext(), "sss",
				// Toast.LENGTH_LONG).show();
			}
		}

	}
}
