package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Comment;

public class CommentBuilder
{
    private String date;
    private String comment;
    private String name;

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
        return new Comment(name, date, comment);
    }
}
