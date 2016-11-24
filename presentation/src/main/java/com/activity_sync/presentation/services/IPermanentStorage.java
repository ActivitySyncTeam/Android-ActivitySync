package com.activity_sync.presentation.services;

public interface IPermanentStorage
{
    public static final String IS_APP_OPENED_BEFORE = "is_app_opened_before";

    void save(String key, boolean value);

    boolean retrieveBoolean(String key, boolean defaultValue);

    void clear();
}
