package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.SplashPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISplashView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    ISplashView view;

    @Mock
    IPermanentStorage permanentStorage;

    @Test
    public void splashPresenter_init_openIntroScreen()
    {
        SplashPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(navigator).openIntroScreen();
    }

    @Test
    public void splashPresenter_init_openLoginScreen()
    {
        Mockito.when(permanentStorage.retrieveBoolean(IPermanentStorage.IS_APP_OPENED_BEFORE, false)).thenReturn(true);

        SplashPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(navigator).openWelcomeScreen();
    }

    private SplashPresenter createPresenter()
    {
        return new SplashPresenter(view, Schedulers.immediate(), navigator, permanentStorage);
    }
}
