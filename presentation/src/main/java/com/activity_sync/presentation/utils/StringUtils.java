package com.activity_sync.presentation.utils;

public class StringUtils
{
    public static final String EMPTY = "";

    private StringUtils()
    {
    }

    public static boolean isNullOrEmpty(String text)
    {
        return text == null || text.equals(EMPTY);
    }
}
