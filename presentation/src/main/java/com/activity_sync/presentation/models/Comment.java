package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable
{
    @SerializedName("date")
    private String date;

    @SerializedName("comment")
    private String comment;

    @SerializedName("name")
    private String name;

    public Comment(String name, String date, String comment)
    {
        this.name = name;
        this.date = date;
        this.comment = comment;
    }

    public Comment()
    {

    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
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