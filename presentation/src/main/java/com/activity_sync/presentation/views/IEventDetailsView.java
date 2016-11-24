package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;

import rx.Observable;

public interface IEventDetailsView
{
    Observable joinLeaveEventClick();

    Observable organizerDetailsClick();

    Observable participantsDetailsClick();

    void setEventData(Event event);

    void showDialog(String message);

    Observable confirmationDialogOk();

    Observable confirmationDialogCancel();

    String joinEventConfirmationText();

    String joinEventConfirmationTitle();

    String leaveEventConfirmationText();

    String leaveEventConfirmationTitle();

    String cancelEventConfirmationText();

    String cancelEventConfirmationTitle();

    void showJoinEventMessage();

    void showLeaveEventMessage();
}
