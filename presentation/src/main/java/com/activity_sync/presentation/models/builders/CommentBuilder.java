package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Comment;

public class CommentBuilder
{
    private int commentID;
    private String date;
    private String comment;
    private String name;

    public CommentBuilder setId(int date)
    {
        this.commentID = commentID;
        return this;
    }

    public CommentBuilder setDate(String date)
    {
        this.date = date;
        return this;
    }

    public CommentBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public CommentBuilder setComment(String comment)
    {
        this.comment = comment;
        return this;
    }

    public Comment createComment()
    {
        return new Comment(commentID, name, date, comment);
    }
}
