package com.activity_sync.presentation.models.builders;


import com.activity_sync.presentation.models.RegisterResponse;

public class RegisterResponseBuilder
{
    private int id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public RegisterResponseBuilder setId(int id)
    {
        this.id = id;
        return this;
    }

    public RegisterResponseBuilder setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public RegisterResponseBuilder setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public RegisterResponseBuilder setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public RegisterResponseBuilder setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public RegisterResponseBuilder setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public RegisterResponse create()
    {
        return new RegisterResponse(id, username, password, email, firstName, lastName);
    }
}
