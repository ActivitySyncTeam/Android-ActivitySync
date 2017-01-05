package com.activity_sync.tests;

import com.activity_sync.presentation.models.Friends;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.FriendsBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.AddedFriendsPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AddedFriendsPresenterTests extends ParticipantsBasePresenterTests
{
    private PublishSubject<User> removeEventClick = PublishSubject.create();
    private PublishSubject<User> removeConfirmEvent = PublishSubject.create();

    private Friends friends;
    User candidate;
    User friend;

    int userId = 1;

    @Override
    public void setup()
    {
        super.setup();

        candidate = new UserBuilder().createUser();
        friends = new FriendsBuilder().setFriends(Arrays.asList(friend)).setCandidates(Arrays.asList(candidate)).create();

        Mockito.when(currentUser.userId()).thenReturn(userId);
        Mockito.when(apiService.getFriends(userId)).thenReturn(Observable.just(friends));
        Mockito.when(apiService.deleteFriend(any())).thenReturn(Observable.just(null));

        Mockito.when(view.removeConfirmClick()).thenReturn(removeConfirmEvent);
        Mockito.when(view.removeEventClick()).thenReturn(removeEventClick);
    }

    @Test
    public void addedFriendsPresenter_init_loadFriends()
    {
        AddedFriendsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(apiService).getFriends(userId);
    }

    @Test
    public void addedFriendsPresenter_removeClick_openRemoveConfDialog()
    {
        AddedFriendsPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedUser);
        Mockito.verify(view).openRemoveConfirmationDialog(testedUser);
    }

    @Test
    public void addedFriendsPresenter_removeAccept_refresh()
    {
        AddedFriendsPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedUser);
        Mockito.verify(apiService).deleteFriend(any());
        Mockito.verify(view).removeSuccessMessage(testedUser);
        Mockito.verify(view).removeUser(testedUser);
    }

    private AddedFriendsPresenter createPresenter()
    {
        return new AddedFriendsPresenter(view, navigator, Schedulers.immediate(), apiService, currentUser);
    }
}
