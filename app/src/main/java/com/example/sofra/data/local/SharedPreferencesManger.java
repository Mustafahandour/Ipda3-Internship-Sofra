package com.example.sofra.data.local;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.sofra.data.model.register.UserDataRestaurant;
import com.google.gson.Gson;

public class SharedPreferencesManger {

    public static SharedPreferences sharedPreferences = null;
    public static String USER_DATA = "USER_DATA";
    public static String USER_PASSWORD = "USER_PASSWORD";
    public static String REMEMBER = "REMEMBER";
    public static String USER_TYPE = "USER_TYPE";
    public static String USER_TYPE_CLIENT = "client";
    public static String USER_TYPE_RESTAURANT = "restaurant";
    public static String CLIENT_DATA = "CLIENT_DATA";
    public static String RESTAURANT_DATA = "RESTAURANT_DATA";


    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", activity.MODE_PRIVATE);
        }
    }

    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static String LoadData(Activity activity, String data_Key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getString(data_Key, null);
    }

    public static boolean LoadBoolean(Activity activity, String data_Key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }

    public static void SaveData(Activity activity, String data_Key, Object data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String StringData = gson.toJson(data_Value);
            editor.putString(data_Key, StringData);
            editor.commit();
        }
    }
    public static void saveClientData(Activity activity, UserDataRestaurant userType) {
        SaveData(activity, CLIENT_DATA, userType);
    }
    public static void saveRestaurantData(Activity activity, UserDataRestaurant userType) {
        SaveData(activity, RESTAURANT_DATA, userType);
    }

    public static void saveUserType(Activity activity, String userType) {
        SaveData(activity, USER_TYPE, userType);
    }

    public static UserDataRestaurant loadUserData(Activity activity,String data_Key) {
        UserDataRestaurant userData = null;

        Gson gson = new Gson();
        userData = gson.fromJson(LoadData(activity, data_Key), UserDataRestaurant.class);

        return userData;
    }

    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

}
