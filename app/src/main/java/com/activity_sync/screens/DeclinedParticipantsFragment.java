package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IParticipantActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.DeclinedParticipantsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.ParticipantsRendererBase;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class DeclinedParticipantsFragment extends ParticipantsFragmentBase implements IParticipantActionListener
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
        return new ParticipantsRendererBase.Builder(getContext(), isOrganizer, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new DeclinedParticipantsPresenter(this, navigator, AndroidSchedulers.mainThread(), isOrganizer, apiService);
    }
}
