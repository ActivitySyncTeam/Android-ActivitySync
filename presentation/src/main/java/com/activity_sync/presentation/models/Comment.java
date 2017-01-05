package com.activity_sync.presentation.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable
{
    @SerializedName("commentID")
    private int commentID;

    @SerializedName("date")
    private String date;

    @SerializedName("comment")
    private String comment;

    @SerializedName("name")
    private String name;

    public Comment(int commentID, String name, String date, String comment)
    {
        this.commentID = commentID;
        this.name = name;
        this.date = date;
        this.comment = comment;
    }

    public Comment()
    {

    }

    public int getCommentID()
    {
        return commentID;
    }

    public String getDate()
    {
        return date;
    }

    public String getComment()
    {
        return comment;
    }

    public String getName()
    {
        return name;
    }
}