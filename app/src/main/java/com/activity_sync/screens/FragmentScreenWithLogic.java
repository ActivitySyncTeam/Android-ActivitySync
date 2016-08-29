package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.activity_sync.presentation.presenters.IPresenter;
import rx.subscriptions.CompositeSubscription;

public abstract class FragmentScreenWithLogic extends FragmentScreen
{
    protected IPresenter presenter;
    protected final CompositeSubscription subscriptions = new CompositeSubscription();

    public IPresenter presenter()
    {
        return this.presenter;
    }

    protected FragmentScreenWithLogic(int layoutResId)
    {
        super(layoutResId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.presenter = this.createPresenter(this, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
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
