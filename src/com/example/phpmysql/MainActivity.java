package com.example.phpmysql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
	// Progress Dialog
	private ProgressDialog pDialog;
	Button Login;
	Button Signup;
	EditText inputName;
	EditText inputPassword;
	JSONParser jsonParser = new JSONParser();
	String servermessage = "";
	private SQLiteDatabase dataBase;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MSG = "msg";
	private static final String TAG_USERDATA = "userdata";
	private static final String FRIST_NAME = "first_Name";
	private static final String LAST_NAME = "last_Name";
	private static final String EMAIL = "email";
	private static final String TABLE_NAME = "district";

	// flag for Internet connection status
	Boolean isInternetPresent = false;

	ConnectionDetector cd;

	ArrayList<HashMap<String, String>> namepassword;
	JSONArray userinfo = null;
	SharedPreferences pref;
	Editor editor;
	String name;
	String password;
	private static String login_page = "http://192.168.40.80:81/loginapi/index.php/login";
	DataBaseHelper myDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}

		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());

		// get Internet status
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			Toast.makeText(getApplicationContext(),
					"You dont have Internet Connection!", Toast.LENGTH_LONG)
					.show();
		}

		Intent i = new Intent(this, MainActivity.class);
		String extra = i.getStringExtra("successmsg");
		// Log.d("extra",extra);
		if (extra == null) {

		} else {
			Toast.makeText(getApplicationContext(), extra, Toast.LENGTH_LONG)
					.show();
		}
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();

		// edittext;
		inputName = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);

		// Buttons
		Login = (Button) findViewById(R.id.btnLogin);
		Signup = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		// view products click event
		Login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				isInternetPresent = cd.isConnectingToInternet();
				if (isInternetPresent) {
					name = inputName.getText().toString();
					password = inputPassword.getText().toString();
					if (!name.isEmpty() && !password.isEmpty()) {
						new Userlogin().execute();
					} else {
						Toast.makeText(getApplicationContext(),
								"Please enter username Password!",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"You dont have Internet Connection!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		Signup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				isInternetPresent = cd.isConnectingToInternet();
				if (isInternetPresent) {
					Intent i = new Intent(getApplicationContext(),
							RegisterActivity.class);
					startActivity(i);
				} else {
					Toast.makeText(getApplicationContext(),
							"You dont have Internet Connection!",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class Userlogin extends AsyncTask<String, String, String> {
		private int jsonstatus = 0;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Login. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("password", password));
			// Log.d("state", "beforehttp");
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(login_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				// check log cat fro response
				Log.d("Create Response", json.toString());
				try {
					int success = json.getInt(TAG_SUCCESS);
					servermessage = json.getString(TAG_MSG);
					if (success == 1) {
						String userdatainfo = json.getString(TAG_USERDATA);
						JSONObject userdata = new JSONObject(userdatainfo);
						// String firstname = userval.getString(FRIST_NAME);
						// Log.d("Response",firstname);

						editor.putString(FRIST_NAME,
								userdata.getString(FRIST_NAME));
						editor.putString(LAST_NAME,
								userdata.getString(LAST_NAME));
						editor.putString(EMAIL, userdata.getString(EMAIL));
						editor.commit();
						// username password match
						Intent i = new Intent(getApplicationContext(),
								Homepage.class);
						startActivity(i);
						finish();
					} else {
						Log.d("msg", "Fail to login");
					}
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
				Toast.makeText(getApplicationContext(),
						"Server Error!! Please Try Again Later.",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), servermessage,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do you want to exit ?")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// do finish
									MainActivity.this.finish();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// do nothing
									return;
								}
							});
			AlertDialog alert = builder.create();
			alert.show();

		}
		return super.onKeyDown(keyCode, event);
	}
}
