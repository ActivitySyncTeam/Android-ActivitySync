package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.User;

import java.io.Serializable;
import java.util.List;

public class ParticpantsBuilder implements Serializable
{
    private List<User> participants;
    private List<User> candidates;

    public ParticpantsBuilder(List<User> participants, List<User> candidates)
    {
        this.participants = participants;
        this.candidates = candidates;
    }

    public ParticpantsBuilder()
    {

    }

    public ParticpantsBuilder setParticipants(List<User> participants)
    {
        this.participants = participants;
        return this;
    }

    public ParticpantsBuilder setCandidates(List<User> candidates)
    {
        this.candidates = candidates;
        return this;
    }

    public Participants create()
    {
        return new Participants(participants, candidates);
    }
}
