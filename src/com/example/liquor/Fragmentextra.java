package com.example.liquor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.phpmysql.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Fragmentextra extends Fragment {
	JSONParser jsonParser = new JSONParser();

	private ProgressDialog pDialog;
	private int jsonstatus = 0;
	private static String login_page = "http://192.168.40.80:81/loginapi/index.php/loadimage";
	String TAG_CATEGORY = "category";
	String category = "";
	ArrayList<Category> categoryListval = new ArrayList<Category>();

	public Fragmentextra() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.category_loader, container, false);
		new FetchCategory().execute();
		return view;
	}

	class FetchCategory extends AsyncTask<String, String, String> {
		JSONArray catdatalist;

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
			params.add(new BasicNameValuePair("name", "ravindra"));

			Log.d("state", "beforehttp");
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(login_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				try {
					category = json.getString(TAG_CATEGORY);
					catdatalist = new JSONArray(category);

					for (int i = 0; i < catdatalist.length(); i++) {
						Category catdata = new Category();
						catdata.setCat_id(i);
						catdata.setCat_name(catdatalist.getString(i));
						categoryListval.add(catdata);
					}
					Log.d("catdatalist", catdatalist.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (jsonstatus == 1) {
				Log.d("status", String.valueOf(jsonstatus));
				Toast.makeText(getActivity(),
						"Server Error!! Please Try Again Later.",
						Toast.LENGTH_LONG).show();
			} else {
				final ListView listView = (ListView) getView().findViewById(
						R.id.category_list);
				ArrayAdapter<Category> adapter;
				adapter = new ArrayAdapter<Category>(getActivity(),
						R.layout.category_item, categoryListval);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> a, View v,
							int position, long id) {
						Category mycategory = (Category) listView
								.getItemAtPosition(position);
						Bundle bundle = new Bundle();
						Fragment fragment = null;
						bundle.putInt("posid", position);
						fragment = new FragmentProdCategory();
						fragment.setArguments(bundle);
						FragmentManager frgManager = getFragmentManager();
						frgManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();

						// Toast.makeText(getActivity(),
						// "Selected :" + mycategory, Toast.LENGTH_LONG)
						// .show();

					}
				});

			}
		}
	}

}
