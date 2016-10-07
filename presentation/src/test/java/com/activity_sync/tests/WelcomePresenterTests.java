package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.WelcomePresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IWelcomeView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IWelcomeView view;

    PublishSubject loginBtnClickEvent = PublishSubject.create();
    PublishSubject registerBtnClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.loginBtnClick()).thenReturn(loginBtnClickEvent);
        Mockito.when(view.registerBtnClick()).thenReturn(registerBtnClickEvent);
    }

    @Test
    public void welcomePresenter_clickLoginBtn_openDummyScreen()
    {
        WelcomePresenter welcomePresenter = createPresenter();
        welcomePresenter.start();

        loginBtnClickEvent.onNext(this);
        Mockito.verify(navigator).openDummyScreen();
    }

    @Test
    public void welcomePresenter_clickRegisterBtn_openDummyScreen()
    {
        WelcomePresenter welcomePresenter = createPresenter();
        welcomePresenter.start();

        registerBtnClickEvent.onNext(this);
        Mockito.verify(navigator).openDummyScreen();
    }

    private WelcomePresenter createPresenter()
    {
        return new WelcomePresenter(Schedulers.immediate(), view, navigator);
    }
}
