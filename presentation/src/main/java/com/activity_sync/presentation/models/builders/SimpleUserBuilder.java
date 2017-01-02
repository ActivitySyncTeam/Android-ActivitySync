package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.SimpleUser;

public class SimpleUserBuilder
{
    private int userId;
    private String name;
    private int like;
    private int dislike;

    public SimpleUserBuilder setUserId(int userId)
    {
        this.userId = userId;
        return this;
    }

    public SimpleUserBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public SimpleUserBuilder setLike(int like)
    {
        this.like = like;
        return this;
    }

    public SimpleUserBuilder setDislike(int dislike)
    {
        this.dislike = dislike;
        return this;
    }

    public SimpleUser create()
    {
        return new SimpleUser(userId, name, like, dislike);
    }
}
