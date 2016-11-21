package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.LoginPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ILoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    ILoginView view;

    PublishSubject loginBtnClickEvent = PublishSubject.create();
    PublishSubject createAccountClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.loginBtnClick()).thenReturn(loginBtnClickEvent);
        Mockito.when(view.createAccountClick()).thenReturn(createAccountClickEvent);
    }

    @Test
    public void loginPresenter_clickLoginBtn_openEventsScreen()
    {
        LoginPresenter loginPresenter = createPresenter();
        loginPresenter.start();

        loginBtnClickEvent.onNext(this);
        Mockito.verify(navigator).openEventsScreen();
    }

    @Test
    public void loginPresenter_clickRegisterBtn_openEventsScreen()
    {
        LoginPresenter loginPresenter = createPresenter();
        loginPresenter.start();

        createAccountClickEvent.onNext(this);
        Mockito.verify(navigator).openEventsScreen();
    }

    private LoginPresenter createPresenter()
    {
        return new LoginPresenter(Schedulers.immediate(), view, navigator);
    }
}
