package com.solution.app.justpay4u.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//===
public class AppPreferences {

    private SharedPreferences _preferences, _preferencesNonRemoval;
    private Editor _editor, _editorNonRemoval;
    private String prefName = "dreamsellerDataPref";
    private String prefNonRemoval = "dreamsellerNonRemovalPref";

    //=====
    public AppPreferences(Context context) {
        _preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        _editor = _preferences.edit();
        _preferencesNonRemoval = context.getSharedPreferences(prefNonRemoval, Context.MODE_PRIVATE);
        _editorNonRemoval = _preferencesNonRemoval.edit();
    }


    //===== Set Normal Pref =====//

    public void set(String key, String value) {
        _editor.putString(key, value);
        _editor.commit();
    }

    public void set(String key, int value) {
        _editor.putInt(key, value);
        _editor.commit();
    }

    public void set(String key, long value) {
        _editor.putLong(key, value);
        _editor.commit();
    }

    public void set(String key, boolean value) {
        _editor.putBoolean(key, value);
        _editor.commit();
    }


    //===== Set Normal Pref =====//


    //===== Get Normal Pref =====//

    public String getString(String key) {
        return _preferences.getString(key, "");
    }

    public int getInt(String key) {
        return _preferences.getInt(key, 0);
    }

    public long getLong(String key) {

        return _preferences.getLong(key, 0);
    }

    public boolean getBoolean(String key) {
        return _preferences.getBoolean(key, false);
    }

    //===== Get Normal Pref =====//

    //=====


    //===== Set Non Removal Pref =====//

    public void setNonRemoval(String key, String value) {
        _editorNonRemoval.putString(key, value);
        _editorNonRemoval.commit();
    }

    public void setNonRemoval(String key, int value) {
        _editorNonRemoval.putInt(key, value);
        _editorNonRemoval.commit();
    }

    public void setNonRemoval(String key, long value) {
        _editorNonRemoval.putLong(key, value);
        _editorNonRemoval.commit();
    }

    public void setNonRemoval(String key, boolean value) {
        _editorNonRemoval.putBoolean(key, value);
        _editorNonRemoval.commit();
    }

    //===== Set Non Removal Pref =====//


    //===== Get Non Removal Pref =====//

    public String getNonRemovalString(String key) {
        return _preferencesNonRemoval.getString(key, "");
    }

    public int getNonRemovalInt(String key) {
        return _preferencesNonRemoval.getInt(key, 0);
    }

    public long getNonRemovalLong(String key) {

        return _preferencesNonRemoval.getLong(key, 0);
    }

    public boolean getNonRemovalBoolean(String key) {
        return _preferencesNonRemoval.getBoolean(key, false);
    }

    //===== Get Non Removal Pref =====//

    //=====

    public void clear() {

        _editor.clear();
        _editor.commit();
    }
}