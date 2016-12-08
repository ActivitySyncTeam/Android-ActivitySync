package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;

import rx.Observable;

public interface IEventDetailsView
{
    Observable joinLeaveEventClick();

    Observable editEventClick();

    Observable cancelEventClick();

    Observable organizerDetailsClick();

    Observable participantsDetailsClick();

    void setEventData(Event event);

    void showEnrollConfirmationDialog();

    void showLeaveConfirmationDialog();

    void showEditConfirmationDialog();

    void showCancelConfirmationDialog();

    Observable joinEventConfirmClick();

    Observable editEventConfirmClick();

    Observable leaveEventConfirmClick();

    Observable cancelEventConfirmClick();

    void showEnrollMessage();

    void showLeaveEventMessage();

    void setOrganizerParticipantView(Event event);

    Observable googleMapAsyncCompleted();

    Observable commentsClick();
}
