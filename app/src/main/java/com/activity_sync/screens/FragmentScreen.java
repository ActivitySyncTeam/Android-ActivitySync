package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activity_sync.presentation.presenters.IPresenter;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public abstract class FragmentScreen extends Fragment
{
    protected IPresenter presenter;
    private final int layoutResId;
    protected final CompositeSubscription subscriptions = new CompositeSubscription();

    protected FragmentScreen(int layoutResId)
    {
        this.layoutResId = layoutResId;
    }

    public IPresenter presenter()
    {
        return this.presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(layoutResId, container, false);
        ButterKnife.bind(this, view);
        this.presenter = this.createPresenter(this, savedInstanceState);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.presenter.start();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.presenter.stop();
        this.subscriptions.clear();
    }

    protected abstract IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState);
}
