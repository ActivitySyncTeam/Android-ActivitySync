package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

public class DeclinedParticipantsPresenter  extends ParticipantsFragmentBasePresenter
{
    public DeclinedParticipantsPresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, boolean isOrganizer, IApiService apiService)
    {
        super(view, navigator, uiThread, isOrganizer, apiService);
    }

    @Override
    void loadParticipants()
    {
        List<User> users = new ArrayList<>();

        users.add(new UserBuilder()
                .setName("Marcin")
                .setSurname("Zielinski")
                .setCredibility(85)
                .createUser());

        users.add(new UserBuilder()
                .setName("Michał")
                .setSurname("Wolny")
                .setCredibility(67)
                .createUser());

        users.add(new UserBuilder()
                .setName("Luke")
                .setSurname("Petka")
                .setCredibility(12)
                .createUser());

        users.add(new UserBuilder()
                .setName("Michał")
                .setSurname("Dudzik")
                .setCredibility(92)
                .createUser());

        view.addUsersList(users);
    }
}
