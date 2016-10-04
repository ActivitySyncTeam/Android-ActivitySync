package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroLocationPresenter;
import com.activity_sync.presentation.views.IIntroLocationView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class IntroLocationScreen extends FragmentScreenWithLogic implements ISlideBackgroundColorHolder, IIntroLocationView
{
    @Bind(R.id.intro_location_relative_layout)
    RelativeLayout introLocationMainLayout;

    @Bind(R.id.btn_location_permission)
    Button locationPermissionBtn;

    public IntroLocationScreen()
    {
        super(R.layout.intro_location_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        return new IntroLocationPresenter(AndroidSchedulers.mainThread(), this);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_locations_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introLocationMainLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    public Observable allowPermissionClick()
    {
        return ViewObservable.clicks(locationPermissionBtn);
    }

    @Override
    public void openPermissionDialog()
    {
        Toast.makeText(getContext(), "It will be implemented in different task", Toast.LENGTH_LONG).show();
    }
}
