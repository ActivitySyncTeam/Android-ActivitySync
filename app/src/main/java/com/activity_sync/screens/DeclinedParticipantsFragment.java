package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.DeclinedUsersPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.SimpleUsersRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class DeclinedParticipantsFragment extends UsersFragmentBase implements IUserActionListener
{
    public DeclinedParticipantsFragment(boolean shouldDisplayAllOptions)
    {
        super(shouldDisplayAllOptions);
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new SimpleUsersRenderer.Builder(getContext(), shouldDisplayAllOptions, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new DeclinedUsersPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }
}
