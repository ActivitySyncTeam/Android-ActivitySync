package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;

import rx.Observable;

public interface IEventDetailsView
{
    Observable joinLeaveEventClick();

    Observable cancelEventClick();

    Observable organizerDetailsClick();

    Observable participantsDetailsClick();

    void setEventData(Event event);

    void showJoinConfirmationDialog();

    void showLeaveConfirmationDialog();

    void showCancelConfirmationDialog();

    Observable joinEventConfirmClick();

    Observable leaveEventConfirmClick();

    Observable cancelEventConfirmClick();

    void showJoinEventMessage();

    void showLeaveEventMessage();

    void setOrganizerParticipantView(Event event);
}
