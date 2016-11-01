package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.UserDetailsPresenter;
import com.activity_sync.presentation.views.IUserDetailsView;

public class UserDetailsScreen extends Screen implements IUserDetailsView
{
    public UserDetailsScreen()
    {
        super(R.layout.user_details_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new UserDetailsPresenter(this);
    }
}
