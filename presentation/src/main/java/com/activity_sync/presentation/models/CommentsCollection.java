package com.activity_sync.presentation.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommentsCollection implements Serializable
{
    @SerializedName("count")
    private int count;

    @SerializedName("next")
    private String next;

    @SerializedName("previous")
    private String previous;

    @SerializedName("results")
    private List<Comment> comments;

    public CommentsCollection(int count, String next, String previous, List<Comment> comments)
    {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.comments = comments;
    }

    public CommentsCollection()
    {

    }

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Comment> getComments() {
        return comments;
    }
}