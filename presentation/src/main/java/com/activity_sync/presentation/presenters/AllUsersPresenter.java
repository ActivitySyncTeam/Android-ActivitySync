package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.FindUsersResponse;
import com.activity_sync.presentation.models.FoundUsersCollection;
import com.activity_sync.presentation.models.builders.FoundUserBuilder;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IAllUsersScreen;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

public class AllUsersPresenter extends Presenter<IAllUsersScreen>
{
    private final INavigator navigator;
    private final Scheduler uiThread;
    private static FoundUsersCollection data;

    static
    {
        List<FindUsersResponse> users = new ArrayList<FindUsersResponse>()
        {{
            add(new FoundUserBuilder().setEvents(100).setFriends(100).setLikes(100)
                    .setName("Luke").setSurname("Petka").setRegisterDate("never")
                    .setUserId(1).setUsername("lukepetka").build());
            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
                    .setUserId(2).setUsername("runnigga").build());
        }};
        data = new FoundUsersCollection(0, "temp", "temp", users);
    }

    public AllUsersPresenter(IAllUsersScreen view, INavigator navigator, Scheduler uiThread)
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

        subscriptions.add(view.refreshUsers()
                .observeOn(uiThread)
                .subscribe(o -> {
                    loadUsers();
                    view.refreshingVisible(false);
                })
        );
    }

    private void loadUsers()
    {
        view.addUsersList(data.getUsers());
    }
}
