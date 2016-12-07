package com.activity_sync.tests;

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

    @Before
    public void setup()
    {
        Mockito.when(view.thumbDownClick()).thenReturn(thumbDownClickEvent);
        Mockito.when(view.thumbUpClick()).thenReturn(thumbUpClickEvent);
        Mockito.when(view.friendsBtnClick()).thenReturn(friendsClickEvent);
    }


    @Test
    public void userDetailsPresenter_initTest_loadData()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).setData(any());
    }

    @Test
    public void userDetailsPresenter_myProfile_hideThumbs()
    {
        Mockito.when(currentUser.userID()).thenReturn(123);

        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).thumbsAndFollowBtnVisible(false);
    }

    @Test
    public void userDetailsPresenter_notMyProfile_prepareThumbs()
    {
        Mockito.when(currentUser.userID()).thenReturn(12345);

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

        Mockito.verify(view).setThumbsColor(1);
    }

    @Test
    public void userDetailsPresenter_thumbDownWhenNotMatched_colorRed()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        thumbDownClickEvent.onNext(this);

        Mockito.verify(view).setThumbsColor(-1);
    }

    @Test
    public void userDetailsPresenter_thumbDownWhenMatched_colorGray()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);
        presenter.createUser(-1, false, false);

        thumbDownClickEvent.onNext(this);
        Mockito.verify(view).setThumbsColor(0);
    }

    @Test
    public void userDetailsPresenter_thumbUpWhenMatched_colorGray()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);
        presenter.createUser(1, false, false);

        thumbUpClickEvent.onNext(this);
        Mockito.verify(view).setThumbsColor(0);
    }

    @Test
    public void userDetailsPresenter_friendAlready_deleteFriend()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);
        presenter.createUser(1, true, false);

        friendsClickEvent.onNext(this);
        Mockito.verify(view).displayFriendRemovedMessage();
    }

    @Test
    public void userDetailsPresenter_candidateAlready_cancelFriendRequest()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);
        presenter.createUser(1, false, true);

        friendsClickEvent.onNext(this);
        Mockito.verify(view).displayFriendRequestCanceledMessage();
    }

    @Test
    public void userDetailsPresenter_stranger_sendFriendRequest()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);
        presenter.createUser(1, false, false);

        friendsClickEvent.onNext(this);
        Mockito.verify(view).displayFriendRequestSentMessage();
    }

    private UserDetailsPresenter createPresenter()
    {
        return new UserDetailsPresenter(view, apiService, Schedulers.immediate(), currentUser);
    }
}
