package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.UserDetails;

public class UserDetailsBuilder
{
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    public UserDetailsBuilder setUserName(String userName)
    {
        this.userName = userName;
        return this;
    }

    public UserDetailsBuilder setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public UserDetailsBuilder setlastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public UserDetailsBuilder setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public UserDetails createUserDetails()
    {
        return new UserDetails(userName, firstName, lastName, email);
    }
}
