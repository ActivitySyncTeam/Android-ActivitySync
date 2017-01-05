package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

public class UserID
{
    @SerializedName("userID")
    private int userID;

    public UserID(int userID)
    {
        this.userID = userID;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }
}
