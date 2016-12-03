package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsFragmentView;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

public class CandidatesPresenter extends ParticipantsFragmentBasePresenter
{
    public CandidatesPresenter(IParticipantsFragmentView view, INavigator navigator, Scheduler uiThread, boolean isOrganizer, IApiService apiService)
    {
        super(view, navigator, uiThread, isOrganizer, apiService);
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.acceptEventClick()
                .subscribe(view::openAcceptConfirmationDialog)
        );

        subscriptions.add(view.removeEventClick()
                .subscribe(view::openRemoveConfirmationDialog)
        );

        subscriptions.add(view.acceptConfirmClick()
                .subscribe(user -> {
                    view.acceptSuccessMessage(user);
                    view.removeParticipant(user);
                })
        );

        subscriptions.add(view.removeConfirmClick()
                .subscribe(user -> {
                    view.removeSuccessMessage(user);
                    view.removeParticipant(user);
                })
        );
    }

    @Override
    void loadParticipants()
    {
        List<User> users = new ArrayList<>();

        users.add(new UserBuilder()
                .setUserDetails(new UserDetailsBuilder()
                        .setFirstName("Marcin")
                        .setlastName("Zielinski")
                        .createUserDetails())
                .setCredibility(85)
                .createUser());

        users.add(new UserBuilder()
                .setUserDetails(new UserDetailsBuilder()
                        .setFirstName("Michał")
                        .setlastName("Wolny")
                        .createUserDetails())
                .setCredibility(67)
                .createUser());

        users.add(new UserBuilder()
                .setUserDetails(new UserDetailsBuilder()
                        .setFirstName("Luke")
                        .setlastName("Petka")
                        .createUserDetails())
                .setCredibility(12)
                .createUser());

        users.add(new UserBuilder()
                .setUserDetails(new UserDetailsBuilder()
                        .setFirstName("Michał")
                        .setlastName("Dudzik")
                        .createUserDetails())
                .setCredibility(92)
                .createUser());

        view.addParticipantsList(users);
    }
}
