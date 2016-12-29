package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.models.CommentsCollection;

import java.util.List;

public class CommentsCollectionBuilder
{
    private int count;
    private String next;
    private String previous;
    private List<Comment> comments;

    public CommentsCollectionBuilder setCount(int count)
    {
        this.count = count;
        return this;
    }

    public CommentsCollectionBuilder setNext(String next)
    {
        this.next = next;
        return this;
    }

    public CommentsCollectionBuilder setPrevious(String previous)
    {
        this.previous = previous;
        return this;
    }

    public CommentsCollectionBuilder setComments(List<Comment> comments)
    {
        this.comments = comments;
        return this;
    }

    public CommentsCollection create()
    {
        return new CommentsCollection(count, next, previous, comments);
    }
}
