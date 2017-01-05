package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.body_models.EventIDBody;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
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
    private final CurrentUser currentUser;
    private final IErrorHandler errorHandler;

    private Event currentEvent;

    public EventDetailsPresenter(Scheduler uiThread, IEventDetailsView view, INavigator navigator, int eventId, IApiService apiService, CurrentUser currentUser, IErrorHandler errorHandler)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.eventId = eventId;
        this.apiService = apiService;
        this.currentUser = currentUser;
        this.errorHandler = errorHandler;
    }

    @Override
    public void start()
    {
        super.start();
        view.buttonsLayoutVisible(false);
        view.showProgressBar();

        subscriptions.add(view.googleMapAsyncCompleted()
                .observeOn(uiThread)
                .subscribe(o -> {

                    apiService.getEventDetails(eventId)
                            .observeOn(uiThread)
                            .subscribe(event -> {

                                view.setEventData(event);
                                currentEvent = event;
                                view.buttonsLayoutVisible(true);
                                view.hideProgressBar();

                            }, this::handleError);
                })
        );

        subscriptions.add(view.joinLeaveEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (isCandidateOrParticpant())
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
                        view.showProgressBar();

                        apiService.joinEventAsAdmin(eventId)
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showEnrollMessage();
                                    currentEvent.setParticipant(true);
                                    currentEvent.setFreePlaces(currentEvent.getFreePlaces() - 1);
                                    view.setEventData(currentEvent);
                                    view.setOrganizerParticipantView(currentEvent);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else
                    {
                        view.showProgressBar();

                        apiService.joinEvent(eventId)
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showEnrollMessage();
                                    currentEvent.setCandidate(true);
                                    view.setOrganizerParticipantView(currentEvent);

                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.leaveEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (currentEvent.isParticipant())
                    {
                        view.showProgressBar();

                        apiService.leaveEvent(new EventIDBody(eventId))
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showLeaveEventMessage();
                                    currentEvent.setCandidate(false);
                                    currentEvent.setParticipant(false);
                                    currentEvent.setFreePlaces(currentEvent.getFreePlaces() + 1);
                                    view.setEventData(currentEvent);
                                    view.setOrganizerParticipantView(currentEvent);
                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                    else
                    {
                        view.showProgressBar();

                        apiService.cancelEventJoinRequest(new EventIDBody(eventId))
                                .observeOn(uiThread)
                                .subscribe(participants -> {

                                    view.showLeaveEventMessage();
                                    currentEvent.setCandidate(false);
                                    currentEvent.setParticipant(false);
                                    view.setOrganizerParticipantView(currentEvent);
                                    view.hideProgressBar();

                                }, this::handleError);
                    }
                })
        );

        subscriptions.add(view.cancelEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    view.showProgressBar();

                    apiService.deleteEvent(eventId)
                            .observeOn(uiThread)
                            .subscribe(result -> {

                                view.hideProgressBar();
                                navigator.openEventsScreen();

                            }, this::handleError);
                })
        );

        subscriptions.add(view.organizerDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (currentEvent.getOrganizer().getUserId() == currentUser.userId())
                    {
                        navigator.openMyProfileScreen();
                    }
                    else
                    {
                        navigator.openUserDetailsScreen(currentEvent.getOrganizer().getUserId());
                    }
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

    @Override
    public void resume()
    {
        super.resume();

        view.showProgressBar();

        apiService.getEventDetails(eventId)
                .observeOn(uiThread)
                .subscribe(event -> {

                    view.setEventData(event);
                    currentEvent = event;

                    view.hideProgressBar();
                    view.buttonsLayoutVisible(true);

                }, this::handleError);
    }

    private boolean isCandidateOrParticpant()
    {
        return currentEvent.isParticipant() || currentEvent.isCandidate();
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        errorHandler.handleError(error);
        view.hideProgressBar();
    }
}
