package com.activity_sync.utils;


import com.activity_sync.presentation.utils.StringUtils;

public class StringBasedColorGenerator
{
    private static final String DEFAULT_PLACEHOLDER_COLOR = "#3F51B5";
    private static final String COLOR_FORMAT = "#FF%06X";

    private StringBasedColorGenerator()
    {
    }

    public static String convertStringToColor(String text)
    {
        return StringUtils.isNullOrEmpty(text) ? DEFAULT_PLACEHOLDER_COLOR : String.format(COLOR_FORMAT, (0xFFFFFF & text.hashCode()));
    }

}
