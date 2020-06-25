package com.news.onlineprakasamapp.app;


import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.news.onlineprakasamapp.BuildConfig;
import com.news.onlineprakasamapp.firebase.ForceUpdateChecker;

import java.util.HashMap;
import java.util.Map;


public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();

	private RequestQueue mRequestQueue;
	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

		// set in-app defaults
		Map<String, Object> remoteConfigDefaults = new HashMap();
		remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
		remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, BuildConfig.VERSION_NAME );
		remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
				"https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);

		firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
		firebaseRemoteConfig.fetch(60) // fetch every minutes
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "remote config is fetched.");
							firebaseRemoteConfig.activateFetched();
						}
					}
				});



	}






	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}



	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
