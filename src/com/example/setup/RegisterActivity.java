package com.example.setup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.setup.R;
import com.example.setup.MainActivity.Userlogin;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity {
	private Button btnRegister;
	private Button btnLinkToLogin;
	private EditText userName;
	private EditText inputFullName;
	private EditText inputlName;
	private EditText inputEmail;
	private EditText inputPassword;
	private ProgressDialog pDialog;
	private static String register_page = "http://192.168.40.80:81/loginapi/index.php/register";
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static final String MSG = "msg";

	ConnectionDetector cd;

	String username;
	String name;
	String lname;
	String email;
	String password;

	// flag for Internet connection status
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		userName = (EditText) findViewById(R.id.username);
		inputFullName = (EditText) findViewById(R.id.name);
		inputlName = (EditText) findViewById(R.id.lname);
		inputEmail = (EditText) findViewById(R.id.email);
		inputPassword = (EditText) findViewById(R.id.password);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());

		// get Internet status
		isInternetPresent = cd.isConnectingToInternet();

		if (!isInternetPresent) {
			Toast.makeText(getApplicationContext(),
					"You dont have Internet Connection!", Toast.LENGTH_LONG)
					.show();
		}

		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				if (isInternetPresent) {
					username = userName.getText().toString().trim();
					name = inputFullName.getText().toString().trim();
					lname = inputlName.getText().toString().trim();
					email = inputEmail.getText().toString().trim();
					password = inputPassword.getText().toString().trim();
					if (!username.isEmpty() && !email.isEmpty()
							&& !password.isEmpty() && !name.isEmpty()
							&& !lname.isEmpty()) {
						new RegisterUser().execute();
					} else {
						Log.d("fill", "fill all");
						Toast.makeText(getApplicationContext(),
								"Please enter your details!", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"You dont have Internet Connection!",
							Toast.LENGTH_LONG).show();
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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

	class RegisterUser extends AsyncTask<String, String, String> {
		private int jsonstatus = 0;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("Registering. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("firstname", name));
			params.add(new BasicNameValuePair("lastname", lname));
			params.add(new BasicNameValuePair("email", name));
			params.add(new BasicNameValuePair("password", password));
			JSONObject json = jsonParser.makeHttpRequest(register_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
			} else {

				try {
					int success = json.getInt(TAG_SUCCESS);
					String message = json.getString(MSG);
					if (success == 1) {
						Intent i = new Intent(getApplicationContext(),
								Homepage.class);
						i.putExtra("successmsg", message);
						startActivity(i);
					} else {
						// Toast.makeText(getApplicationContext(), message,
						// Toast.LENGTH_LONG).show();
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
			// dismiss the dialog once done
			pDialog.dismiss();
			if (jsonstatus == 1) {
				Log.d("status", String.valueOf(jsonstatus));
				Toast.makeText(getApplicationContext(),
						"Server Error!! Please Try Again Later.",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
