package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroLastPresenter;
import com.activity_sync.presentation.views.IIntroLastView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class IntroLastScreen extends FragmentScreenWithLogic implements ISlideBackgroundColorHolder, IIntroLastView
{
    @Bind(R.id.intro_last_screen_relative_layout)
    RelativeLayout introLastScreenLayout;

    @Bind(R.id.image_check)
    ImageView checkImageView;

    public IntroLastScreen() {
        super(R.layout.intro_last_screen);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_last_screen_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introLastScreenLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        return new IntroLastPresenter(AndroidSchedulers.mainThread(), this);
    }

    @Override
    public Observable checkImageClick()
    {
        return ViewObservable.clicks(checkImageView);
    }

    @Override
    public void endFlow()
    {
        Toast.makeText(getContext(), "not implemented yet", Toast.LENGTH_LONG).show();
    }
}
