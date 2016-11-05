package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.SplashPresenter;
import com.activity_sync.presentation.services.INavigator;
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

    @Test
    public void splashPresenter_init_openIntroScreen()
    {
        SplashPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(navigator).openIntroScreen();
    }

    private SplashPresenter createPresenter()
    {
        return new SplashPresenter(view, Schedulers.immediate(), navigator);
    }
}
