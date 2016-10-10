package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("user")
    private UserDetails userDetails;

    @SerializedName("creditability")
    private String creditability;

    public User(int id, UserDetails userDetails, String creditability)
    {
        this.id = id;
        this.userDetails = userDetails;
        this.creditability = creditability;
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

    public String getCreditability()
    {
        return creditability;
    }

    public void setCreditability(String creditability)
    {
        this.creditability = creditability;
    }
}