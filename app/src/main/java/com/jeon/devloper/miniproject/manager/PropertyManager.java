package com.jeon.devloper.miniproject.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jeon.devloper.miniproject.MyApplication;

/**
 * Created by Administrator on 2016-08-10.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REGISTRATION_ID = "regid";

    private PropertyManager() {
        Context context = MyApplication.getContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }

    public void setEmail(String email) {
        mEditor.putString(KEY_EMAIL, email);
        mEditor.commit();
    }

    public String getEmail() {
        return mPrefs.getString(KEY_EMAIL, "");
    }

    public void setPassword(String password) {
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPrefs.getString(KEY_PASSWORD, "");
    }

    public void setRegistrationId(String regid) {
        mEditor.putString(KEY_REGISTRATION_ID, regid);
        mEditor.commit();
    }

    public String getRegistrationId() {
        return mPrefs.getString(KEY_REGISTRATION_ID, "");
    }

}
