package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoundUsersCollection implements Serializable
{
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    @SerializedName("results")
    private List<FindUsersResponse> users;

    public FoundUsersCollection(int count, String next, String previous, List<FindUsersResponse> users)
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

    public List<FindUsersResponse> getUsers()
    {
        return users;
    }
}
