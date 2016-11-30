package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Discipline implements Serializable
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Discipline(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Discipline()
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

    @Override
    public String toString()
    {
        return name;
    }
}