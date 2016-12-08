package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.EventCandidatesPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.UsersTwoOptionsRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class EventCandidatesFragment extends UsersFragmentBase implements IUserActionListener
{
    public EventCandidatesFragment(boolean isOrganizer)
    {
        super(isOrganizer);
    }

    public EventCandidatesFragment()
    {
        super();
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new UsersTwoOptionsRenderer.Builder(getContext(), isOrganizer, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new EventCandidatesPresenter(this, navigator, AndroidSchedulers.mainThread(), isOrganizer, apiService);
    }
}
