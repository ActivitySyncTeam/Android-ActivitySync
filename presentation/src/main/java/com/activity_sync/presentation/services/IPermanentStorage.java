package com.activity_sync.presentation.services;

public interface IPermanentStorage
{
    String ACCESS_TOKEN = "access_token";
    String CURRENT_USER_ID = "current_user_id";
    String CURRENT_USER_NAME = "current_user_name";
    String CURRENT_USER_LAST_NAME = "current_user_lastName";
    String CLIENT_SECRET = "client_secret";
    String CLIENT_ID = "client_id";
    String IS_APP_OPENED_BEFORE = "is_app_opened_before";
    String IS_LOCATION_ENABLED = "is_location_enabled";
    String IS_NOTIFICATION_ENABLED = "is_notification_enabled";
    String IS_NOTIFICATION_SOUND_ENABLED = "is_notification_sound_enabled";
    String IS_NOTIFICATION_VIBRATION_ENABLED = "is_notification_vibration_enabled";
    String SEARCH_DAYS_AHEAD = "search_days_ahead";
    String SEARCH_RANGE = "search_range";
    String LAST_LATITUDE = "last_latitude";
    String LAST_LONGITUDE = "last_longitude";

    boolean IS_APP_OPENED_BEFORE_DEFAULT = false;
    boolean IS_LOCATION_ENABLED_DEFAULT = false;
    boolean IS_NOTIFICATION_ENABLED_DEFAULT = true;
    boolean IS_NOTIFICATION_SOUND_ENABLED_DEFAULT = true;
    boolean IS_NOTIFICATION_VIBRATION_ENABLED_DEFAULT = true;
    int CURRENT_USER_ID_DEFAULT = -1;
    int SEARCH_DAYS_AHEAD_DEFAULT = 21;
    int SEARCH_RANGE_DEFAULT = 5;
    float LAST_COORDINATION_DEFAULT = (float) -1000.00;

    void saveBoolean(String key, boolean value);

    void saveInteger(String key, int value);

    void saveString(String key, String value);

    void saveFloat(String key, float value);

    boolean retrieveBoolean(String key, boolean defaultValue);

    int retrieveInteger(String key, int defaultValue);

    String retrieveString(String key, String defaultValue);

    float retrieveFloat(String key, float defaultValue);

    void clear();
}
