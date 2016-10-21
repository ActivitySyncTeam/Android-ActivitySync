package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class Screen extends BaseActivity
{
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    protected IPresenter presenter;
    private final int layoutResId;
    protected Screen(int layoutResId)
    {
        this.layoutResId = layoutResId;
    }
    public IPresenter presenter()
    {
        return this.presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(this.layoutResId);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        this.initToolbar();
        this.presenter = this.createPresenter(this, savedInstanceState);
    }

    private void initToolbar()
    {
        if (toolbar == null)
        {
            return;
        }

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (presenter != null)
        {
            this.presenter.start();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (presenter != null)
        {
            this.presenter.stop();
        }
    }

    protected abstract IPresenter createPresenter(Screen screen, Bundle savedInstanceState);
}
