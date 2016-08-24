package com.activity_sync.screens;

import android.os.Bundle;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.DummyPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IDummyView;

public class DummyScreen extends Screen implements IDummyView
{
    public DummyScreen()
    {
        super(R.layout.dummy_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new DummyPresenter(this);
    }
}
