package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public User(int id, String name)
    {
        this.id = id;
        this.name = name;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
