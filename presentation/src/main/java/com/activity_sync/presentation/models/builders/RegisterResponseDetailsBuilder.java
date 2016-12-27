package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.RegisterResponseDetails;

public class RegisterResponseDetailsBuilder
{
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public RegisterResponseDetailsBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public RegisterResponseDetailsBuilder setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public RegisterResponseDetailsBuilder setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public RegisterResponseDetailsBuilder setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public RegisterResponseDetailsBuilder setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public RegisterResponseDetailsBuilder setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public RegisterResponseDetails create()
    {
        return new RegisterResponseDetails(id, username, password, email, firstName, lastName);
    }
}
