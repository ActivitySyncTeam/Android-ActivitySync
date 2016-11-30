package com.activity_sync.screens;

import android.os.Bundle;
import com.activity_sync.presentation.presenters.CandidatesPresenter;
import com.activity_sync.presentation.presenters.IPresenter;

import rx.android.schedulers.AndroidSchedulers;

public class CandidatesFragment extends ParticipantsFragmentBase
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
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new CandidatesPresenter(this, navigator, AndroidSchedulers.mainThread());
    }
}
