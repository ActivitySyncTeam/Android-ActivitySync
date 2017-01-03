package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Friends;
import com.activity_sync.presentation.models.User;

import java.util.List;

public class FriendsBuilder
{
    private List<User> friends;
    private List<User> candidates;

    public FriendsBuilder setFriends(List<User> friends)
    {
        this.friends = friends;
        return this;
    }

    public FriendsBuilder setCandidates(List<User> candidates)
    {
        this.candidates = candidates;
        return this;
    }

    public Friends create()
    {
        return new Friends(friends, candidates);
    }
}
