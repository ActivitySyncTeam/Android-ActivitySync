package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
    @SerializedName("userID")
    private int userId;

    @SerializedName("id")
    private int id;

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

    @SerializedName("isAdmin")
    private boolean isAdmin;

    @SerializedName("isCandidate")
    private boolean isCandidate;

    @SerializedName("isInvited")
    private boolean isInvited;

    @SerializedName("isFriend")
    private boolean isFriend;

    @SerializedName("rate")
    private int rate;

    @SerializedName("credibility")
    private int credibility;

    @SerializedName("ratesNumber")
    private int ratesNumber;

    @SerializedName("friends")
    private int friends;

    @SerializedName("likes")
    private int likes;

    public User(int userId, int id, String name, String surname, String username, String email, String registerDate, String signature, int events, boolean isAdmin, boolean isCandidate, boolean isInvited, boolean isFriend, int rate, int credibility, int ratesNumber, int friends, int likes)
    {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.registerDate = registerDate;
        this.signature = signature;
        this.events = events;
        this.isAdmin = isAdmin;
        this.isCandidate = isCandidate;
        this.isFriend = isFriend;
        this.rate = rate;
        this.credibility = credibility;
        this.ratesNumber = ratesNumber;
        this.friends = friends;
        this.likes = likes;
        this.isInvited = isInvited;
    }

    public User()
    {

    }

    public int getUserId()
    {
        if (userId == 0)
        {
            return id;
        }
        else
        {
            return userId;
        }
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getRegisterDate()
    {
        return registerDate;
    }

    public String getSignature()
    {
        return signature;
    }

    public int getEvents()
    {
        return events;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public boolean isCandidate()
    {
        return isCandidate;
    }

    public boolean isFriend()
    {
        return isFriend;
    }

    public int getRate()
    {
        return rate;
    }

    public int getCredibility()
    {
        return credibility;
    }

    public int getRatesNumber()
    {
        return ratesNumber;
    }

    public void setCandidate(boolean candidate)
    {
        isCandidate = candidate;
    }

    public void setFriend(boolean friend)
    {
        isFriend = friend;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public int getFriends()
    {
        return friends;
    }

    public int getLikes()
    {
        return likes;
    }

    public boolean isInvited()
    {
        return isInvited;
    }

    public void setInvited(boolean invited)
    {
        isInvited = invited;
    }
}