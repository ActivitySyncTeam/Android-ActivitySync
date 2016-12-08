package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.DeclinedParticipantsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.SimpleUsersRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class DeclinedParticipantsFragment extends UsersFragmentBase implements IUserActionListener
{
    public DeclinedParticipantsFragment(boolean isOrganizer)
    {
        super(isOrganizer);
    }

    public DeclinedParticipantsFragment()
    {
        super();
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new SimpleUsersRenderer.Builder(getContext(), isOrganizer, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new DeclinedParticipantsPresenter(this, navigator, AndroidSchedulers.mainThread(), isOrganizer, apiService);
    }
}
