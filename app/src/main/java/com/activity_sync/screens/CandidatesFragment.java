package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.presentation.action_listeners.IParticipantActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.CandidatesPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.renderers.CandidatesMainRenderer;
import com.activity_sync.renderers.base.RendererBuilder;

import rx.android.schedulers.AndroidSchedulers;

public class CandidatesFragment extends ParticipantsFragmentBase implements IParticipantActionListener
{
    public CandidatesFragment(boolean isOrganizer)
    {
        super(isOrganizer);
    }

    public CandidatesFragment()
    {
        super();
    }

    @Override
    RendererBuilder<User> getRendererBuilder()
    {
        return new CandidatesMainRenderer.Builder(getContext(), isOrganizer, this);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new CandidatesPresenter(this, navigator, AndroidSchedulers.mainThread(), isOrganizer);
    }
}
