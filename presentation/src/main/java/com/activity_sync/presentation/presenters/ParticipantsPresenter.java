package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IParticipantsView;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

public class ParticipantsPresenter extends Presenter<IParticipantsView>
{
    private final INavigator navigator;
    private Scheduler uiThread;

    public ParticipantsPresenter(IParticipantsView view, INavigator navigator, Scheduler uiThread)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();
        loadUsers();

        subscriptions.add(view.refreshParticipants()
                .subscribe(event -> {
                    loadUsers();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedUser()
                .subscribe(event -> {
                    navigator.openUserDetailsScreen(1);
                })
        );
    }

    void loadUsers()
    {
        //API CALL WILL BE HERE

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

        view.addUsersList(users);
    }
}
