package com.activity_sync.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.activity_sync.R;

public class CreditabilityService
{
    public int colorSource;
    public int descriptionSource;

    private Context context;

    public CreditabilityService(Context context, int creditabilityScore)
    {
        calculateResourcesValues(creditabilityScore);
        this.context = context;
    }

    public void calculateResourcesValues(int creditabilityScore)
    {
        if (creditabilityScore >= 90)
        {
            descriptionSource = R.string.txt_very_good;
            colorSource = R.color.user_details_very_good;
        }
        else if (creditabilityScore >= 80 && creditabilityScore < 90)
        {
            descriptionSource = R.string.txt_good;
            colorSource = R.color.user_details_good;
        }
        else if (creditabilityScore >= 65 && creditabilityScore < 80)
        {
            descriptionSource = R.string.txt_ok;
            colorSource = R.color.user_details_ok;
        }
        else if (creditabilityScore >= 50 && creditabilityScore < 65)
        {
            descriptionSource = R.string.txt_poor;
            colorSource = R.color.user_details_poor;
        }
        else if (creditabilityScore >= 30 && creditabilityScore < 50)
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

