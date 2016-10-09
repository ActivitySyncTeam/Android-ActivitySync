package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activity_sync.presentation.presenters.IPresenter;

import butterknife.ButterKnife;

public abstract class FragmentScreen extends Fragment
{
    private final int layoutResId;
    private IPresenter presenter;
    protected FragmentScreen(int layoutResId)
    {
        this.layoutResId = layoutResId;
    }

    public IPresenter presenter()
    {
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(layoutResId, container, false);
        ButterKnife.bind(this, view);
        presenter = createPresenter(this, savedInstanceState);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (presenter != null)
        {
            presenter.start();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (presenter != null)
        {
            presenter.stop();
        }
    }

    protected abstract IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState);
}
