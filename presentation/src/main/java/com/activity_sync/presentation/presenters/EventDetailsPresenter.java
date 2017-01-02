package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import rx.Scheduler;
import timber.log.Timber;

public class EventDetailsPresenter extends Presenter<IEventDetailsView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final int eventId;
    private final IApiService apiService;

    private Event currentEvent;

    public EventDetailsPresenter(Scheduler uiThread, IEventDetailsView view, INavigator navigator, int eventId, IApiService apiService)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.eventId = eventId;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.googleMapAsyncCompleted()
                .observeOn(uiThread)
                .subscribe(o -> {

                    apiService.getEventDetails(eventId)
                            .observeOn(uiThread)
                            .subscribe(event -> {

                                view.setEventData(event);
                                currentEvent = event;

                            }, this::handleError);

                })
        );

        subscriptions.add(view.joinLeaveEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (currentEvent.isParticipant())
                    {
                        view.showLeaveConfirmationDialog();
                    }
                    else
                    {
                        view.showEnrollConfirmationDialog();
                    }
                })
        );

        subscriptions.add(view.cancelEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showCancelConfirmationDialog();
                })
        );

        subscriptions.add(view.editEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventUpdateScreen(currentEvent);
                })
        );

        subscriptions.add(view.joinEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (currentEvent.isOrganizer())
                    {
                        apiService.joinEventAsAdmin(eventId)
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showEnrollMessage();
                                    currentEvent.setParticipant(true);
                                    view.setOrganizerParticipantView(currentEvent);

                                }, this::handleError);
                    }
                    else
                    {
                        apiService.joinEvent(eventId)
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showEnrollMessage();
                                    currentEvent.setCandidate(true);
                                    view.setOrganizerParticipantView(currentEvent);

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.leaveEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (currentEvent.isParticipant())
                    {
                        apiService.leaveEvent(eventId)
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showLeaveEventMessage();
                                    currentEvent.setCandidate(false);
                                    currentEvent.setParticipant(false);
                                    view.setOrganizerParticipantView(currentEvent);

                                }, this::handleError);
                    }
                    else
                    {
                        apiService.cancelEventJoinRequest(eventId)
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showLeaveEventMessage();
                                    currentEvent.setCandidate(false);
                                    currentEvent.setParticipant(false);
                                    view.setOrganizerParticipantView(currentEvent);

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.cancelEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventsScreen();
                })
        );

        subscriptions.add(view.organizerDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openUserDetailsScreen(1);
                })
        );

        subscriptions.add(view.participantsDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (currentEvent != null)
                    {
                        navigator.openParticipantsScreen(currentEvent.isOrganizer(), currentEvent.getEventId());
                    }
                })
        );

        subscriptions.add(view.commentsClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (currentEvent != null)
                    {
                        navigator.openCommentsScreen(currentEvent.getEventId());
                    }
                })
        );
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.displayDefaultError();
    }
}
