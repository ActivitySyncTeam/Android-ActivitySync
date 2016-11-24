package com.activity_sync.presentation.services;

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

    void saveBoolean(String key, boolean value);

    void saveInteger(String key, int value);

    boolean retrieveBoolean(String key, boolean defaultValue);

    int retrieveInteger(String key, int defaultValue);

    void clear();
}
