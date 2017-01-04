package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.UsersCollection;

import java.util.List;

public class UsersCollectionBuilder
{
    private int count;
    private String next;
    private String previous;
    private List<User> users;

    public UsersCollectionBuilder setCount(int count)
    {
        this.count = count;
        return this;
    }

    public UsersCollectionBuilder setNext(String next)
    {
        this.next = next;
        return this;
    }

    public UsersCollectionBuilder setPrevious(String previous)
    {
        this.previous = previous;
        return this;
    }

    public UsersCollectionBuilder setUsers(List<User> users)
    {
        this.users = users;
        return this;
    }

    public UsersCollection build()
    {
        return new UsersCollection(count, next, previous, users);
    }
}
