package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.FindUsersResponse;

public class FoundUserBuilder
{
    private int userId;
    private String name;
    private String surname;
    private String username;
    private String registerDate;
    private int events;
    private int friends;
    private int likes;

    public FoundUserBuilder setUserId(int userId)
    {
        this.userId = userId;
        return this;
    }

    public FoundUserBuilder setEvents(int events)
    {
        this.events = events;
        return this;
    }

    public FoundUserBuilder setFriends(int friends)
    {
        this.friends = friends;
        return this;
    }

    public FoundUserBuilder setLikes(int likes)
    {
        this.likes = likes;
        return this;
    }

    public FoundUserBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public FoundUserBuilder setSurname(String surname)
    {
        this.surname = surname;
        return this;
    }

    public FoundUserBuilder setUsername(String username)
    {
        this.username = username;
        return this;
    }

    public FoundUserBuilder setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
        return this;
    }

    public FindUsersResponse build()
    {
        return new FindUsersResponse(userId, name, surname, username, registerDate, events, friends, likes);
    }
}
