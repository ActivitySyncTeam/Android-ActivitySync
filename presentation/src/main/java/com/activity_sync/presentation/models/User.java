package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("user")
    private UserDetails userDetails;

    @SerializedName("credibility")
    private int credibility;

    public User(int id, UserDetails userDetails, int credibility)
    {
        this.id = id;
        this.userDetails = userDetails;
        this.credibility = credibility;
    }

    public User()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public UserDetails getUserDetails()
    {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails)
    {
        this.userDetails = userDetails;
    }

    public int getCredibility()
    {
        return credibility;
    }

    public void setCredibility(int credibility)
    {
        this.credibility = credibility;
    }
}