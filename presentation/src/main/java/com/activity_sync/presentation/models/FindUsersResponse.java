package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FindUsersResponse implements Serializable
{
    @SerializedName("userID")
    private int userId;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    @SerializedName("username")
    private String username;

    @SerializedName("registerDate")
    private String registerDate;

    @SerializedName("events")
    private int events;

    @SerializedName("friends")
    private int friends;

    @SerializedName("likes")
    private int likes;

    public FindUsersResponse(int userId, String name, String surname, String username, String registerDate, int events, int friends, int likes)
    {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.registerDate = registerDate;
        this.events = events;
        this.friends = friends;
        this.likes = likes;
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

    public String getRegisterDate()
    {
        return registerDate;
    }

    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }

    public int getEvents()
    {
        return events;
    }

    public void setEvents(int events)
    {
        this.events = events;
    }

    public int getFriends()
    {
        return friends;
    }

    public void setFriends(int friends)
    {
        this.friends = friends;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }
}
