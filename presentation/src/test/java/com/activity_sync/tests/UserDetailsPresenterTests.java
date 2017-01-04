package com.activity_sync.tests;

import com.activity_sync.presentation.models.Friends;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.FriendsBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.views.IUserDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailsPresenterTests
{
    @Mock
    IUserDetailsView view;

    @Mock
    IApiService apiService;

    @Mock
    CurrentUser currentUser;

    PublishSubject thumbUpClickEvent = PublishSubject.create();
    PublishSubject thumbDownClickEvent = PublishSubject.create();
    PublishSubject friendsClickEvent = PublishSubject.create();
    PublishSubject rejectClickEvent = PublishSubject.create();

    int userId = 1;
    User user;
    Friends friends;

    @Before
    public void setup()
    {
        friends = new FriendsBuilder().create();

        user = createUser(3, false, false, false);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        Mockito.when(apiService.rateUser(userId, view.LIKE)).thenReturn(Observable.just(user));
        Mockito.when(apiService.rateUser(userId, view.DISLIKE)).thenReturn(Observable.just(user));
        Mockito.when(apiService.rateUser(userId, view.NO_ASSESSMENT)).thenReturn(Observable.just(user));

        Mockito.when(apiService.sendFriendRequest(userId)).thenReturn(Observable.just(friends));
        Mockito.when(apiService.cancelFriendInvitation(any())).thenReturn(Observable.just(friends));
        Mockito.when(apiService.acceptFriendInvitation(userId)).thenReturn(Observable.just(null));
        Mockito.when(apiService.deleteFriend(any())).thenReturn(Observable.just(null));
        Mockito.when(apiService.rejectFriendRequest(any())).thenReturn(Observable.just(null));

        Mockito.when(view.thumbDownClick()).thenReturn(thumbDownClickEvent);
        Mockito.when(view.thumbUpClick()).thenReturn(thumbUpClickEvent);
        Mockito.when(view.friendsBtnClick()).thenReturn(friendsClickEvent);
        Mockito.when(view.rejectInvitationClick()).thenReturn(rejectClickEvent);
    }

    @Test
    public void userDetailsPresenter_initTest_loadData()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(apiService).getUserData(userId);
        Mockito.verify(view).setData(user);
    }

    @Test
    public void userDetailsPresenter_notMyProfile_prepareThumbs()
    {
        Mockito.when(currentUser.clientId()).thenReturn("12345");

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).thumbsAndFollowBtnVisible(true);
    }

    @Test
    public void userDetailsPresenter_thumbUpWhenNotMatched_colorGreen()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        thumbUpClickEvent.onNext(this);

        Mockito.verify(apiService).rateUser(userId, view.LIKE);
        Mockito.verify(view).setThumbsColor(1);
    }

    @Test
    public void userDetailsPresenter_thumbDownWhenNotMatched_colorRed()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        thumbDownClickEvent.onNext(this);

        Mockito.verify(apiService).rateUser(userId, view.DISLIKE);
        Mockito.verify(view).setThumbsColor(-1);
    }

    @Test
    public void userDetailsPresenter_thumbDownWhenMatched_colorGray()
    {
        user = createUser(-1, false, false, false);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        thumbDownClickEvent.onNext(this);
        Mockito.verify(apiService).rateUser(userId, view.NO_ASSESSMENT);
        Mockito.verify(view).setThumbsColor(0);
    }

    @Test
    public void userDetailsPresenter_thumbUpWhenMatched_colorGray()
    {
        user = createUser(1, false, false, false);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        thumbUpClickEvent.onNext(this);
        Mockito.verify(apiService).rateUser(userId, view.NO_ASSESSMENT);
        Mockito.verify(view).setThumbsColor(0);
    }

    @Test
    public void userDetailsPresenter_friendAlready_deleteFriend()
    {
        user = createUser(1, true, false, false);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        friendsClickEvent.onNext(this);
        Mockito.verify(apiService).deleteFriend(any());
        Mockito.verify(view).displayFriendRemovedMessage();
    }

    @Test
    public void userDetailsPresenter_candidateAlready_cancelFriendRequest()
    {
        user = createUser(-1, false, true, false);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        friendsClickEvent.onNext(this);
        Mockito.verify(apiService).cancelFriendInvitation(any());
        Mockito.verify(view).displayFriendRequestCanceledMessage();
    }

    @Test
    public void userDetailsPresenter_stranger_sendFriendRequest()
    {
        user = createUser(1, false, false, false);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        friendsClickEvent.onNext(this);
        Mockito.verify(apiService).sendFriendRequest(userId);
        Mockito.verify(view).displayFriendRequestSentMessage();
    }

    @Test
    public void userDetailsPresenter_userInvitedMe_acceptFriendRequest()
    {
        user = createUser(1, false, false, true);
        Mockito.when(apiService.getUserData(userId)).thenReturn(Observable.just(user));

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        friendsClickEvent.onNext(this);
        Mockito.verify(apiService).acceptFriendInvitation(userId);
        Mockito.verify(view).displayFriendRequestAcceptedMessage();
    }

    @Test
    public void userDetailsPresenter_userInvitedMe_rejectRequest()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        rejectClickEvent.onNext(this);
        Mockito.verify(apiService).rejectFriendRequest(any());
        Mockito.verify(view).displayFriendRequestRejectedMessage();
    }

    private UserDetailsPresenter createPresenter()
    {
        return new UserDetailsPresenter(view, apiService, Schedulers.immediate(), currentUser, userId);
    }

    public User createUser(int rate, boolean isFriend, boolean isCandidate, boolean isInvited)
    {
        return new UserBuilder()
                .setName("Marcin")
                .setSurname("Zielinski")
                .setUsername("mzielu")
                .setEmail("kmarcinzielnski@gmail.com")
                .setRegisterDate("2015-12-12")
                .setSignature("Randomly written text")
                .setEvents(23)
                .setUserId(userId)
                .setCredibility(85)
                .setRate(rate)
                .setFriend(isFriend)
                .setCandidate(isCandidate)
                .setInvited(isInvited)
                .createUser();
    }
}
