package com.dharammaniar.androidexamples.gcm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dharammaniar.androidexamples.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicationMain extends Activity implements OnClickListener {

	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static final String TAG = "GCM Sample Demo";
	TextView mDisplay;
	EditText mMessage;
	GoogleCloudMessaging gcm;
	Context context;
	String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gcm);
		mDisplay = (TextView) findViewById(R.id.communication_display);
		mMessage = (EditText) findViewById(R.id.communication_edit_message);
		gcm = GoogleCloudMessaging.getInstance(this);
		context = getApplicationContext();
	}

	@Override
	public void onClick(final View view) {
		if (view == findViewById(R.id.communication_send)) {
			String message = ((EditText) findViewById(R.id.communication_edit_message)).getText().toString();
			if (message.equals("")) {
				Toast.makeText(context, "Sending Message Empty!",Toast.LENGTH_LONG).show();
				return;
			}
			sendMessage(message);
		}

		if (view == findViewById(R.id.communication_clear)) {
			mMessage.setText("");
		}

		if (view == findViewById(R.id.communication_unregistor_button)) {
			unregister();
		}

		if (view == findViewById(R.id.communication_registor_button)) {
			if (checkPlayServices()) {
				regid = getRegistrationId(context);
				if (TextUtils.isEmpty(regid)) {
					registerInBackground();
				}
			}
		}
	}

	private void sendMessage(final String message) {
		if (regid == null || regid.equals("")) {
			Toast.makeText(this, "You must register first", Toast.LENGTH_LONG).show();
			return;
		}
		if (message.isEmpty()) {
			Toast.makeText(this, "Empty Message", Toast.LENGTH_LONG).show();
			return;
		}

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				List<String> regIds = new ArrayList<String>();
				String reg_device = regid;
				Map<String, String> msgParams;
				msgParams = new HashMap<>();
				msgParams.put("data.message", message);
				GcmNotification gcmNotification = new GcmNotification();
				regIds.clear();
				regIds.add(reg_device);
				gcmNotification.sendNotification(msgParams, regIds,CommunicationMain.this);
				return "Message Sent - " + message;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		}.execute(null, null, null);
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		Log.i(TAG, String.valueOf(registeredVersion));
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		return getSharedPreferences(CommunicationMain.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(CommunicationConstants.GCM_SENDER_ID);

                    // implementation to store and keep track of registered devices here
                    msg = "Device registered, registration ID=" + regid;
					sendRegistrationIdToBackend();
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Log.d(TAG, msg);
				mDisplay.append(msg + "\n");
			}
		}.execute(null, null, null);
	}

	private void sendRegistrationIdToBackend() {
		// Your implementation here.
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private void unregister() {
		Log.d(CommunicationConstants.TAG, "UNREGISTER USERID: " + regid);
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					msg = "Sent unregistration";
					gcm.unregister();
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				removeRegistrationId(getApplicationContext());
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				((TextView) findViewById(R.id.communication_display))
						.setText(regid);
			}
		}.execute();
	}

	private void removeRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(CommunicationConstants.TAG, "Removig regId on app version "
				+ appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(PROPERTY_REG_ID);
		editor.commit();
		regid = null;
	}


}
