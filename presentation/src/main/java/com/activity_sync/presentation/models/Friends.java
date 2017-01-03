package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Friends implements Serializable
{
    @SerializedName("friends")
    private List<User> friends;

    @SerializedName("candidates")
    private List<User> candidates;

    public Friends(List<User> friends, List<User> candidates)
    {
        this.friends = friends;
        this.candidates = candidates;
    }

    public Friends()
    {

    }

    public List<User> getFriends()
    {
        return friends;
    }

    public List<User> getCandidates()
    {
        return candidates;
    }
}
