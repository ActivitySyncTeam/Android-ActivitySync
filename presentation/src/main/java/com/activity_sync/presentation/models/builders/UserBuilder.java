package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.UserDetails;

public class UserBuilder
{
    private int id;
    private UserDetails userDetails;
    private int credibility;

    public UserBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public UserBuilder setUserDetails(UserDetails userDetails)
    {
        this.userDetails = userDetails;
        return this;
    }

    public UserBuilder setCredibility(int credibility)
    {
        this.credibility = credibility;
        return this;
    }

    public User createUser()
    {
        return new User(id, userDetails, credibility);
    }
}
