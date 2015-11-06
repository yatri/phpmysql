package com.example.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.setup.RegisterActivity.RegisterUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FragmentFour extends Fragment {
	private ArrayList<HashMap<String, String>> list;
	private static String register_page = "http://192.168.40.80:81/loginapi/index.php/uploadComment";
	private ProgressDialog pDialog;
	private String jonstring;
	private static final String TAG_SUCCESS = "success";
	private static final String MSG = "msg";
	JSONParser jsonParser = new JSONParser();

	public FragmentFour() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = null;
		list = new ArrayList<HashMap<String, String>>();
		DatabaseHandler db = new DatabaseHandler(getActivity());
		List<FormData> commentdata = db.getAllComments();
		for (FormData frmdta : commentdata) {
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("commentid", Integer.toString(frmdta.getComment_id()));
			temp.put("name", frmdta.getName());
			temp.put("district", String.valueOf(frmdta.getDistrict_id()));
			temp.put("vdc", String.valueOf(frmdta.getVdcid()));
			temp.put("ward", Integer.toString(frmdta.getWardno()));
			temp.put("tole", frmdta.getTole());
			temp.put("comment", frmdta.getComment());
			list.add(temp);
		}
		JSONObject finalObject = new JSONObject();
		try {
			finalObject.put("table_data", list);
			finalObject.put("key_item", "this this");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String jonstring = gson.toJson(finalObject);
		jonstring = jonstring.substring(18, jonstring.length() - 1);
		System.out.println(jonstring);
		new UploadData().execute();
		return view;
	}

	class UploadData extends AsyncTask<String, String, String> {
		private int jsonstatus = 0;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Registering. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("comment_list", jonstring));
			JSONObject json = jsonParser.makeHttpRequest(register_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
			} else {
				try {
					int success = json.getInt(TAG_SUCCESS);
					String message = json.getString(MSG);
					if (success == 1) {
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	}

}
