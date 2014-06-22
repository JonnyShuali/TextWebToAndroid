package com.roy.compsms;

import register.RegisterGcm;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Main UI for the app.
 */
public class MainActivity extends Activity {

	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;//Used for Gcm request
	
	public static final String TAG = "Developer";//Tag used on log messages.
	public static Context context;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Check device for Play Services APK. If check succeeds, proceed with
		// GCM registration.
		if (checkPlayServices()) {
			if(this.CheckRegistred()){// Checks if the user was registered before
				//the user already registered
				this.AlreadyRegistered();
			} else { //the user didn't register
				EditText emailInput = (EditText) findViewById(R.id.email);
				emailInput.setText(getEmailGoogle.getEmail(this));
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
			 Toast.makeText(context,
			 "No valid Google Play Services APK found",
			 Toast.LENGTH_LONG).show();
			 finish();
		}
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				Dialog error = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST);
				error.setOnDismissListener(new OnDismissListener() {

					public void onDismiss(DialogInterface dialog) {
						finish();

					}
				});
				error.show();
			} else {
				Log.i(TAG, "This device is not supported.");
				Toast.makeText(this, "This device is not supported", Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * stores sharedprefences the fact if the user was registered
	 */
	public void SharedPrefencesWrite() {
		final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("Registered", true);
		editor.commit();
	}

	/**
	 * checks if the correct device is registered by checking sharedpreference value
	 */
	private boolean CheckRegistred() {
		context = getApplicationContext();
		final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
		boolean isRegistred=prefs.getBoolean("Registered", false);
		if (isRegistred) {
			return true;
		}
		return false;
	}

	public void RegisterClick(View v) {
		EditText pass = (EditText) findViewById(R.id.password);
		EditText email = (EditText) findViewById(R.id.email);
		RegisterGcm gcm = new RegisterGcm(email.getText().toString(), pass
				.getText().toString(),this);
		gcm.execute(null, null);
		SharedPrefencesWrite();
		this.AlreadyRegistered();

	}
/**
 * Arrange the layout for registered account
 */
	public void AlreadyRegistered() {
		TextView displayInfo = (TextView) findViewById(R.id.display_info);
		displayInfo.setText(R.string.registerd_info);
		TextView webLink= (TextView)findViewById(R.id.web);
		webLink.setVisibility(View.VISIBLE);
		LinearLayout layout = (LinearLayout) findViewById(R.id.RegForm);
		for (int i = 0; i < layout.getChildCount(); i++) {
			View child = layout.getChildAt(i);
			child.setEnabled(false);
			child.setVisibility(View.GONE);
		}
	}
}
