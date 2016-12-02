package com.activity_sync.presentation.models.builders;

import com.activity_sync.presentation.models.EnrollmentStatus;

public class EnrollmentStatusBuilder
{
    private boolean isOrganizer;
    private boolean isParticipant;
    private boolean isCandidate;
    private int rate;

    public EnrollmentStatusBuilder setOrganizer(boolean organizer)
    {
        isOrganizer = organizer;
        return this;
    }

    public EnrollmentStatusBuilder setParticipant(boolean participant)
    {
        isParticipant = participant;
        return this;
    }

    public EnrollmentStatusBuilder setCandidate(boolean candidate)
    {
        isCandidate = candidate;
        return this;
    }

    public EnrollmentStatusBuilder setRate(int rate)
    {
        this.rate = rate;
        return this;
    }

    public EnrollmentStatus createEnrollmentStatus()
    {
        return new EnrollmentStatus(isOrganizer, isParticipant, isCandidate, rate);
    }
}
