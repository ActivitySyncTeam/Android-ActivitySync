package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IParticipantActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.RegisteredParticipantsPresenter;
import com.activity_sync.renderers.RegisteredParticipantsRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class RegisteredParticipantsFragment extends ParticipantsFragmentBase implements IParticipantActionListener
{
    public RegisteredParticipantsFragment(boolean isOrganizer)
    {
        super(isOrganizer);
    }

    public RegisteredParticipantsFragment()
    {
       super();
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new RegisteredParticipantsRenderer.Builder(getContext(), isOrganizer, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new RegisteredParticipantsPresenter(this, navigator, AndroidSchedulers.mainThread(), isOrganizer, apiService);
    }
}
