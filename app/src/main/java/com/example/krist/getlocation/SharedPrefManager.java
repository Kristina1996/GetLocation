package com.example.krist.getlocation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by krist on 03.08.2018.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_ID = "iduser";
    private static final String KEY_USERLOGIN = "login";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_EMAIL = "email";

    private SharedPrefManager(Context context) {

        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance (Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin (int id, String login, String name, String surname, String password) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USERLOGIN, login);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_SURNAME, surname);
        //editor.putString(KEY_EMAIL, email);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
            return true;
        }
        return false;
    }

    public boolean logout () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public int getIdUser () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 0);
    }

    public String getLogin () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERLOGIN, null);
    }

    public String getUsername () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getSurname () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SURNAME, null);
    }

    public String getEmail () {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

}
