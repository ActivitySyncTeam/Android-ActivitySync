package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.User;

public class UserBuilder
{
    private int userId;
    private int id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String registerDate;
    private String signature;
    private int events;
    private boolean isOrganizer;
    private boolean isCandidate;
    private boolean isFriend;
    private int rate;
    private int credibility;
    private int ratesNumber;
    private int likes;
    private int friends;

    public UserBuilder setUserId(int userId)
    {
        this.userId = userId;
        this.id = userId;
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

    public UserBuilder setCredibility(int credibility)
    {
        this.credibility = credibility;
        return this;
    }

    public UserBuilder setOrganizer(boolean organizer)
    {
        isOrganizer = organizer;
        return this;
    }

    public UserBuilder setCandidate(boolean candidate)
    {
        isCandidate = candidate;
        return this;
    }

    public UserBuilder setFriend(boolean friend)
    {
        isFriend = friend;
        return this;
    }

    public UserBuilder setRate(int rate)
    {
        this.rate = rate;
        return this;
    }

    public UserBuilder setRatesNumber(int ratesNumber)
    {
        this.ratesNumber = ratesNumber;
        return this;
    }

    public UserBuilder setLikes(int likes)
    {
        this.likes = likes;
        return this;
    }

    public UserBuilder setFriends(int friends)
    {
        this.friends = friends;
        return this;
    }

    public User createUser()
    {
        return new User(userId, id, name, surname, username, email, registerDate, signature, events, isOrganizer, isCandidate, isFriend, rate, credibility, ratesNumber, friends, likes);
    }
}
