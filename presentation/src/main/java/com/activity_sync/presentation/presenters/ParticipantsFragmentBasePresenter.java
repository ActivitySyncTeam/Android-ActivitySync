package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsFragmentView;

import rx.Scheduler;

abstract public class ParticipantsFragmentBasePresenter extends Presenter<IParticipantsFragmentView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    private final INavigator navigator;
    private final Scheduler uiThread;
    protected final boolean isOrganizer;

    public ParticipantsFragmentBasePresenter(IParticipantsFragmentView view, INavigator navigator, Scheduler uiThread, boolean isOrganizer)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.isOrganizer = isOrganizer;
    }

    @Override
    public void start()
    {
        super.start();
        loadParticipants();

        subscriptions.add(view.refreshParticipants()
                .subscribe(participants -> {
                    loadParticipants();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedParticipant()
                .subscribe(participant -> {
                    navigator.openUserDetailsScreen(participant.getId());
                })
        );
    }

    abstract void loadParticipants();
}
