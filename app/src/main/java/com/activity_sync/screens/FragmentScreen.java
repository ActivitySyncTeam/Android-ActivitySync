package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class FragmentScreen extends Fragment
{
    private final int layoutResId;
    protected FragmentScreen(int layoutResId)
    {
        this.layoutResId = layoutResId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(layoutResId, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
