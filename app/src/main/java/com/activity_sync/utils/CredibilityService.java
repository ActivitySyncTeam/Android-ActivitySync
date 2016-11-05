package com.activity_sync.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.activity_sync.R;

public class CredibilityService
{
    public int colorSource;
    public int descriptionSource;

    private Context context;

    public CredibilityService(Context context, int credibilityScore)
    {
        calculateResourcesValues(credibilityScore);
        this.context = context;
    }

    public void calculateResourcesValues(int credibilityScore)
    {
        if (credibilityScore >= 90)
        {
            descriptionSource = R.string.txt_very_good;
            colorSource = R.color.user_details_very_good;
        }
        else if (credibilityScore >= 80 && credibilityScore < 90)
        {
            descriptionSource = R.string.txt_good;
            colorSource = R.color.user_details_good;
        }
        else if (credibilityScore >= 65 && credibilityScore < 80)
        {
            descriptionSource = R.string.txt_ok;
            colorSource = R.color.user_details_ok;
        }
        else if (credibilityScore >= 50 && credibilityScore < 65)
        {
            descriptionSource = R.string.txt_poor;
            colorSource = R.color.user_details_poor;
        }
        else if (credibilityScore >= 30 && credibilityScore < 50)
        {
            descriptionSource = R.string.txt_bad;
            colorSource = R.color.user_details_bad;
        }
        else
        {
            descriptionSource = R.string.txt_terrible;
            colorSource = R.color.user_details_terrible;
        }
    }

    public int getColor()
    {
        return ContextCompat.getColor(context, colorSource);
    }

    public String getDescription()
    {
        return context.getResources().getString(descriptionSource);
    }
}

