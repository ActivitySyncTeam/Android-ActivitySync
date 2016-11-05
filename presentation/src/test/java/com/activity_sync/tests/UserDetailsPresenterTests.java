package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.views.IUserDetailsView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailsPresenterTests
{
    @Mock
    IUserDetailsView view;

    @Test
    public void userDetailsPresenter_initTest_loadData()
    {
        UserDetailsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).setData(any());
    }

    private UserDetailsPresenter createPresenter()
    {
        return new UserDetailsPresenter(view);
    }
}
