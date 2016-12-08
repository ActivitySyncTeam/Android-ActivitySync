package com.activity_sync.screens;


import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.FriendsRequestPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.UsersTwoOptionsRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class FriendsRequestsFragment extends UsersFragmentBase implements IUserActionListener
{
    public FriendsRequestsFragment(boolean shouldDisplayAllOptions)
    {
        super(shouldDisplayAllOptions);
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new UsersTwoOptionsRenderer.Builder(getContext(), shouldDisplayAllOptions, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new FriendsRequestPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }
}
