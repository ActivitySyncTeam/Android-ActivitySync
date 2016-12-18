package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.MyProfilePresenter;
import com.activity_sync.presentation.services.CurrentUser;
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

    private MyProfilePresenter myProfilePresenter;
    private PublishSubject openEditAccountScreenEvent = PublishSubject.create();
    private PublishSubject openChangePasswordScreenEvent = PublishSubject.create();

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        CurrentUser currentUser = new CurrentUser(storage, navigator);
        myProfilePresenter = new MyProfilePresenter(view, navigator, Schedulers.immediate(), currentUser);

        when(view.editUserAccount()).thenReturn(openEditAccountScreenEvent);
        when(view.changeUserPassword()).thenReturn(openChangePasswordScreenEvent);

        myProfilePresenter.start();
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

}