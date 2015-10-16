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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.phpmysql.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FragmentProdDetail extends Fragment {
	int productid;
	int categoryid;
	int whichpage;
	private ProgressDialog pDialog;
	private int jsonstatus = 0;
	private int jsonstatussubmit = 0;
	String TAG_IMGURL = "imageurl";
	String TAG_NAME = "name";
	String TAG_DESC = "description";
	String TAG_TYPES = "types";
	String TAG_PRICE = "price";
	String imageurl = "";
	String name = "";
	String description = "";
	String types = "";
	String price = "";
	ImageView productImg;
	TextView nameTxt, desTxt;
	Spinner spinnertypes;
	LinearLayout cangone;
	Button submit;
	EditText qty;
	String jonstring = "";
	JSONParser jsonParser = new JSONParser();
	Submitdata submitdata = new Submitdata();;
	private static String product_page = "http://192.168.40.80:81/loginapi/index.php/loadimage";
	private static String order_page = "http://192.168.40.80:81/loginapi/index.php/upload";
	ArrayList<String> listspinner = new ArrayList<String>();
	private static final String USER_ID = "use_id";
	private int userid;

	public FragmentProdDetail() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.product_detail, container, false);

		SharedPreferences userdata = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		userid = userdata.getInt(USER_ID, 0);

		submit = (Button) view.findViewById(R.id.btnSubmit);
		qty = (EditText) view.findViewById(R.id.quantity);
		spinnertypes = (Spinner) view.findViewById(R.id.spType);
		Bundle bundle = this.getArguments();
		categoryid = bundle.getInt("category_id", 999);
		productid = bundle.getInt("prod_id", 0);
		whichpage = bundle.getInt("whichpage",1);
		

		if (productid == 0) {
			Toast.makeText(getActivity(), "Some Problem Occured",
					Toast.LENGTH_LONG).show();
		} else {
			new GetSpecificProduct().execute();
		}

		submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (qty.getText().toString().length() == 0) {
					qty.setError("Quantity Required");
				} else {
					submitdata.setQty(Integer
							.parseInt(qty.getText().toString()));
					submitdata.setType(spinnertypes.getSelectedItem()
							.toString());
					GsonBuilder builder = new GsonBuilder();
					Gson gson = builder.create();
					jonstring = gson.toJson(submitdata);
					new Submitorder().execute();
					Log.d("submitdta", jonstring);
				}
			}
		});

		return view;
	}

	class Submitorder extends AsyncTask<String, String, String> {
		String successmsg;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Order Submitting...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("odrer", jonstring));
			JSONObject json = jsonParser.makeHttpRequest(order_page, "POST",
					params);
			if (json == null) {
				jsonstatussubmit = 1;
				Log.d("error", "connection error");
			} else {
				try {
					successmsg = json.getString("success");
					String data = json.getString("data");
					Log.d("msg", successmsg);
					Log.d("data", data);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			Bundle bundle = new Bundle();
			Fragment fragment = null;
			bundle.putInt("id", categoryid);
			if(whichpage ==1){
				fragment = new FragmentTopSelling();
			}else{
				fragment = new FragmentProdCategory();
			}
			
			fragment.setArguments(bundle);
			FragmentManager frgManager = getFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
			Toast.makeText(getActivity(), successmsg, Toast.LENGTH_LONG).show();

		}

	}

	class GetSpecificProduct extends AsyncTask<String, String, String> {
		JSONArray typearray;
		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("prod_id", String
					.valueOf(productid)));
			JSONObject json = jsonParser.makeHttpRequest(product_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				try {
					Product myproduct = new Product();
					imageurl = json.getString(TAG_IMGURL);
					name = json.getString(TAG_NAME);
					myproduct.setProductname(name);
					description = json.getString(TAG_DESC);
					myproduct.setProductID(productid);
					types = json.getString(TAG_TYPES);
					typearray = new JSONArray(types);

					if (typearray != null) {
						int len = typearray.length();
						for (int i = 0; i < len; i++) {
							listspinner.add(typearray.get(i).toString());
						}
					}
					// new ImageDownloaderTask(holder.imageView)
					// .execute(imageurl);
					Log.d("types", listspinner.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			//pDialog.dismiss();
			if (jsonstatus == 0) {
				productImg = (ImageView) getView()
						.findViewById(R.id.thumbImage);
				nameTxt = (TextView) getView().findViewById(R.id.setname);
				desTxt = (TextView) getView().findViewById(R.id.setdes);
				spinnertypes = (Spinner) getView().findViewById(R.id.spType);
				cangone = (LinearLayout) getView().findViewById(R.id.goneDiv);
				submitdata.setUserid(userid);
				submitdata.setProdid(productid);
				if (!listspinner.isEmpty()) {
					ArrayAdapter<String> type_sp_adaptor = new ArrayAdapter<String>(
							getActivity(),
							android.R.layout.simple_spinner_item, listspinner);
					spinnertypes.setAdapter(type_sp_adaptor);
					Log.d("elsegone", "no");
				} else {
					Log.d("elsegone", "yes");
					cangone.setVisibility(View.GONE);
				}

				nameTxt.setText(name);
				desTxt.setText(description);
				new ImageDownloaderTask(productImg).execute(imageurl);
			}
		}

	}

}
