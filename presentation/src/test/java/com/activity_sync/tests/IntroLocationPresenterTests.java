package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.IntroLocationPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IIntroLocationView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class IntroLocationPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IIntroLocationView view;

    PublishSubject enablePermissionBtnClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.allowPermissionClick()).thenReturn(enablePermissionBtnClickEvent);
    }

    @Test
    public void introBasePresenter_clickSkipBtn_openDummyScreen()
    {
        IntroLocationPresenter introLocationPresenter = createPresenter();
        introLocationPresenter.start();

        enablePermissionBtnClickEvent.onNext(this);
        Mockito.verify(view).openPermissionDialog();
    }

    private IntroLocationPresenter createPresenter()
    {
        return new IntroLocationPresenter(Schedulers.immediate(), view);
    }
}
