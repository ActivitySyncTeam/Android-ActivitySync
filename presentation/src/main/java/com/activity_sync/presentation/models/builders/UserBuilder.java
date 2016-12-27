package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.AdditionalInfo;
import com.activity_sync.presentation.models.User;

public class UserBuilder
{
    private String userId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String registerDate;
    private String signature;
    private int events;
    private AdditionalInfo additionalInfo;
    private int credibility;

    public UserBuilder setUserId(String userId)
    {
        this.userId = userId;
        return this;
    }

    public UserBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public UserBuilder setSurname(String surname)
    {
        this.surname = surname;
        return this;
    }

    public UserBuilder setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public UserBuilder setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public UserBuilder setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
        return this;
    }

    public UserBuilder setSignature(String signature)
    {
        this.signature = signature;
        return this;
    }

    public UserBuilder setEvents(int events)
    {
        this.events = events;
        return this;
    }

    public UserBuilder setAdditionalInfo(AdditionalInfo additionalInfo)
    {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public UserBuilder setCredibility(int credibility)
    {
        this.credibility = credibility;
        return this;
    }

    public User createUser()
    {
        return new User(userId, name, surname, username, email, registerDate, signature, events, additionalInfo, credibility);
    }
}
