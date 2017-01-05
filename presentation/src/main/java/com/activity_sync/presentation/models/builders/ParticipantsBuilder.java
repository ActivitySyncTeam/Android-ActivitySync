package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.User;

import java.util.List;

public class ParticipantsBuilder
{
    private List<User> participants;
    private List<User> candidates;

    public ParticipantsBuilder setParticipants(List<User> participants)
    {
        this.participants = participants;
        return this;
    }

    public ParticipantsBuilder setCandidates(List<User> candidates)
    {
        this.candidates = candidates;
        return this;
    }

    public Participants create()
    {
        return new Participants(participants, candidates);
    }
}
