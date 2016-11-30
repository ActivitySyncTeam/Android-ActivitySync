package com.activity_sync.screens;

import android.os.Bundle;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.RegisteredParticipantsPresenter;

import rx.android.schedulers.AndroidSchedulers;

public class RegisteredParticipantsFragment extends ParticipantsFragmentBase
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
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new RegisteredParticipantsPresenter(this, navigator, AndroidSchedulers.mainThread());
    }
}
