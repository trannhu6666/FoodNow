package com.example.foodnow;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "FoodNowSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PHONE = "userPhone";
    private static final String KEY_USER_POINTS = "userPoints";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_PHONE, user.getPhone());
        editor.putInt(KEY_USER_POINTS, user.getPoints());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public User getLoggedInUser() {
        if (!isLoggedIn()) {
            return new User("1", "Ngọc Như", "0901234567", 1200);
        }
        String id = pref.getString(KEY_USER_ID, "1");
        String name = pref.getString(KEY_USER_NAME, "Ngọc Như");
        String phone = pref.getString(KEY_USER_PHONE, "0901234567");
        int points = pref.getInt(KEY_USER_POINTS, 1200);
        return new User(id, name, phone, points);
    }

    public String getUserId() {
        return pref.getString(KEY_USER_ID, "1");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
