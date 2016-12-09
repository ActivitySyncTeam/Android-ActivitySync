package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.AddedFriendsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.UsersRemovableRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class AddedFriendsFragment extends UsersFragmentBase implements IUserActionListener
{
    public AddedFriendsFragment(boolean shouldDisplayAllOptions)
    {
        super(shouldDisplayAllOptions);
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new UsersRemovableRenderer.Builder(getContext(), shouldDisplayAllOptions, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new AddedFriendsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }
}
