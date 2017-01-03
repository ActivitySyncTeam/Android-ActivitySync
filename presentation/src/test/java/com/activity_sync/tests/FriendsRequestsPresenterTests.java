package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.FriendsRequestPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FriendsRequestsPresenterTests extends ParticipantsBasePresenterTests
{
    private PublishSubject<User> acceptEventClick = PublishSubject.create();
    private PublishSubject<User> removeEventClick = PublishSubject.create();
    private PublishSubject<User> acceptConfirmEvent = PublishSubject.create();
    private PublishSubject<User> removeConfirmEvent = PublishSubject.create();

    @Override
    public void setup()
    {
        super.setup();

        Mockito.when(view.acceptConfirmClick()).thenReturn(acceptConfirmEvent);
        Mockito.when(view.removeConfirmClick()).thenReturn(removeConfirmEvent);
        Mockito.when(view.acceptEventClick()).thenReturn(acceptEventClick);
        Mockito.when(view.removeEventClick()).thenReturn(removeEventClick);

        Mockito.when(apiService.acceptFriendInvitation(testedUser.getUserId())).thenReturn(Observable.just(friends));
        Mockito.when(apiService.rejectFriendRequest(any())).thenReturn(Observable.just(null));
    }

    @Test
    public void friendRequestPresenter_acceptClick_openAcceptConfDialog()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        acceptEventClick.onNext(testedUser);
        Mockito.verify(view).openAcceptConfirmationDialog(testedUser);
    }

    @Test
    public void friendRequestPresenter_removeClick_openRemoveConfDialog()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedUser);
        Mockito.verify(view).openRemoveConfirmationDialog(testedUser);
    }

    @Test
    public void friendRequestPresenter_confirmAccept_refresh()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        acceptConfirmEvent.onNext(testedUser);
        Mockito.verify(apiService).acceptFriendInvitation(testedUser.getUserId());
        Mockito.verify(view).acceptSuccessMessage(testedUser);
        Mockito.verify(view).removeUser(testedUser);
    }

    @Test
    public void friendRequestPresenter_removeAccept_refresh()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedUser);
        Mockito.verify(apiService).rejectFriendRequest(any());
        Mockito.verify(view).removeSuccessMessage(testedUser);
        Mockito.verify(view).removeUser(testedUser);
    }

    private FriendsRequestPresenter createPresenter()
    {
        return new FriendsRequestPresenter(view, navigator, Schedulers.immediate(), apiService, currentUser);
    }
}
