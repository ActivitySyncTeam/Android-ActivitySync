package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
    @SerializedName("userID")
    private int userId;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("registerDate")
    private String registerDate;

    @SerializedName("signature")
    private String signature;

    @SerializedName("events")
    private int events;

    @SerializedName("user")
    private AdditionalInfo additionalInfo;

    @SerializedName("credibility")          //please add credibility in api call
    private int credibility;

    public User(int userId, String name, String surname, String username, String email, String registerDate, String signature, int events, AdditionalInfo additionalInfo, int credibility)
    {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.registerDate = registerDate;
        this.signature = signature;
        this.events = events;
        this.additionalInfo = additionalInfo;
        this.credibility = credibility;
    }

    public User()
    {

    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRegisterDate()
    {
        return registerDate;
    }

    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public int getEvents()
    {
        return events;
    }

    public void setEvents(int events)
    {
        this.events = events;
    }

    public AdditionalInfo getAdditionalInfo()
    {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo)
    {
        this.additionalInfo = additionalInfo;
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