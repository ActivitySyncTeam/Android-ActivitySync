package com.activity_sync.presentation.services;

import com.activity_sync.presentation.utils.StringUtils;

public interface IPermanentStorage
{
    String IS_APP_OPENED_BEFORE = "is_app_opened_before";
    boolean IS_APP_OPENED_BEFORE_DEFAULT = false;
    String IS_LOCATION_ENABLED = "is_location_enabled";
    boolean IS_LOCATION_ENABLED_DEFAULT = false;
    String IS_NOTIFICATION_ENABLED = "is_notification_enabled";
    boolean IS_NOTIFICATION_ENABLED_DEFAULT = true;
    String IS_NOTIFICATION_SOUND_ENABLED = "is_notification_sound_enabled";
    boolean IS_NOTIFICATION_SOUND_ENABLED_DEFAULT = true;
    String IS_NOTIFICATION_VIBRATION_ENABLED = "is_notification_vibration_enabled";
    boolean IS_NOTIFICATION_VIBRATION_ENABLED_DEFAULT = true;
    String SEARCH_DAYS_AHEAD = "search_days_ahead";
    int SEARCH_DAYS_AHEAD_DEFAULT = 21;
    String SEARCH_RANGE = "search_range";
    int SEARCH_RANGE_DEFAULT = 5;
    String AUTH_TOKEN = "auth_token";
    String AUTH_TOKEN_DEFAULT = StringUtils.EMPTY;
    String CURRENT_USER_ID = "current_user_id";
    int CURRENT_USER_ID_DEFAULT = 0;

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
