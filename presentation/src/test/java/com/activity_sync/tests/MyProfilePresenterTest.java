package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.MyProfilePresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IMyProfileView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Mockito.when;

public class MyProfilePresenterTest
{
    @Mock
    IMyProfileView view;

    @Mock
    INavigator navigator;

    @Mock
    IPermanentStorage storage;

    @Mock
    CurrentUser currentUser;

    @Mock
    IApiService apiService;

    private MyProfilePresenter myProfilePresenter;
    private PublishSubject openEditAccountScreenEvent = PublishSubject.create();
    private PublishSubject openChangePasswordScreenEvent = PublishSubject.create();

    private User user;
    private int userId = 1;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        myProfilePresenter = createPresenter();

        when(view.editUserAccount()).thenReturn(openEditAccountScreenEvent);
        when(view.changeUserPassword()).thenReturn(openChangePasswordScreenEvent);

        user = new UserBuilder().setUserId(userId).createUser();

        when(apiService.getUserData(currentUser.userId())).thenReturn(Observable.just(user));

        myProfilePresenter.start();
    }

    @Test
    public void myProfilePresenter_init_loadData()
    {
        Mockito.verify(apiService).getUserData(currentUser.userId());
    }

    @Test
    public void myProfilePresenter_clickEditAccountBtn_openEditAccountScreen()
    {
        openEditAccountScreenEvent.onNext(this);
        Mockito.verify(navigator).openEditAccountScreen(Matchers.any(User.class));
    }

    @Test
    public void myProfilePresenter_clickChangePasswordBtn_openChangePasswordScreen()
    {
        openChangePasswordScreenEvent.onNext(this);
        Mockito.verify(navigator).openChangePasswordScreen();
    }

    @After
    public void tearDown() throws Exception
    {
        myProfilePresenter = null;
    }

    private MyProfilePresenter createPresenter()
    {
        return new MyProfilePresenter(view, navigator, Schedulers.immediate(), currentUser, apiService);
    }
}