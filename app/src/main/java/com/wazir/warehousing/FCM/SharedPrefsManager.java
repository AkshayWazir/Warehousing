package com.wazir.warehousing.FCM;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wazir.warehousing.GloabalFunctions.Constants.USER_MANAGER;
import static com.wazir.warehousing.GloabalFunctions.Constants.USER_WORKER;

public class SharedPrefsManager {
    private static final String SHARED_PREFS_NAME = "fcmsharedpreftoken";
    private static final String KEY_ACCESS_TOKEN = "token";
    private static final String KEY_USER_TYPE = "cwcuserManagerAccessTokenGrant";
    private static final String WAREHOUSE_ID = "SomeWarehouseId";
    private static final String USER_NAME = "asauifbibfejb";


    private static Context mCtx;
    private static SharedPrefsManager mInstance;

    private SharedPrefsManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefsManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefsManager(context);
        }
        return mInstance;
    }

    public void storeToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
    }

    public void storeWarehouseId(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WAREHOUSE_ID, id);
        editor.apply();
    }

    public void saveUserName(String id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, id);
        editor.apply();
    }

    public String getWarehouseId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(WAREHOUSE_ID, null);
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);
    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public boolean setUserType(String userType) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (userType.equals(USER_MANAGER)) {
            editor.putString(KEY_USER_TYPE, USER_MANAGER);
        } else if (userType.equals(USER_WORKER)) {
            editor.putString(KEY_USER_TYPE, USER_WORKER);
        }
        editor.apply();
        return true;
    }

    public String returnUserType() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TYPE, null);
    }
}
