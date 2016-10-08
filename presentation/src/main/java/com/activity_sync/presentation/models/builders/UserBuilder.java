package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.User;

public class UserBuilder
{
    private int id;
    private String name;

    public UserBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name)
    {
        this.name = name;
        return this;
    }


    public User createUser()
    {
        return new User(id, name);
    }
}
