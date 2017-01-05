package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UsersCollection implements Serializable
{
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    @SerializedName("results")
    private List<User> users;

    public UsersCollection(int count, String next, String previous, List<User> users)
    {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.users = users;
    }

    public int getCount()
    {
        return count;
    }

    public String getNext()
    {
        return next;
    }

    public String getPrevious()
    {
        return previous;
    }

    public List<User> getUsers()
    {
        return users;
    }
}
