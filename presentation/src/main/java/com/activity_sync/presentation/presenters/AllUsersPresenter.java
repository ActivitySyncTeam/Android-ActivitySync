package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IAllUsersScreen;

import rx.Scheduler;
import timber.log.Timber;

public class AllUsersPresenter extends Presenter<IAllUsersScreen>
{
    private final INavigator navigator;
    private final Scheduler uiThread;
    private final IApiService apiService;
//    private static UsersCollection data;
//
//    static
//    {
//        List<FindUsersResponse> users = new ArrayList<FindUsersResponse>()
//        {{
//            add(new FoundUserBuilder().setEvents(100).setFriends(100).setLikes(100)
//                    .setName("Luke").setSurname("Petka").setRegisterDate("never")
//                    .setUserId(1).setUsername("lukepetka").build());
//            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
//                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
//                    .setUserId(2).setUsername("runnigga").build());
//            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
//                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
//                    .setUserId(2).setUsername("runnigga").build());
//            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
//                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
//                    .setUserId(2).setUsername("runnigga").build());
//            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
//                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
//                    .setUserId(2).setUsername("runnigga").build());
//            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
//                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
//                    .setUserId(2).setUsername("runnigga").build());
//            add(new FoundUserBuilder().setEvents(11).setFriends(123).setLikes(1234)
//                    .setName("Marcin").setSurname("Ziel").setRegisterDate("never")
//                    .setUserId(2).setUsername("runnigga").build());
//
//        }};
//        data = new UsersCollection(0, "temp", "temp", users);
//    }

    public AllUsersPresenter(IAllUsersScreen view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
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
        apiService.getAllUsers()
                .observeOn(uiThread)
                .subscribe(usersCollection -> {
                    view.refreshingVisible(false);
                    view.addUsersList(usersCollection.getUsers());
                }, this::handleError);
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
    }
}
