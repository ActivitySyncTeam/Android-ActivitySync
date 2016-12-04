package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.MyEventsPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MyEventsFragment extends EventsFragmentBase implements IEventsFragmentView
{
    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new MyEventsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setFragmentToolbarVisibility(LinearLayout.GONE);
    }

    @Override
    public Observable searchDateClick()
    {
        return null;
    }

    @Override
    public void openDatePicker()
    {

    }

    @Override
    public Observable<String> newDateEvent()
    {
        return null;
    }

    @Override
    public void setDate(String date)
    {

    }

    @Override
    public void prepareDisciplineSpinner(List<Discipline> disciplines)
    {

    }

    @Override
    public Discipline getDiscipline()
    {
        return null;
    }
}
