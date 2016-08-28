package com.activity_sync.screens;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.IntroSimplePresenter;
import com.activity_sync.presentation.views.IIntroSimpleView;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class IntroSimpleScreen extends FragmentScreen implements IIntroSimpleView, ISlideBackgroundColorHolder
{
    @Bind(R.id.intro_simple_linear_layout)
    LinearLayout introWelcomeMainLayout;

    @Bind(R.id.test_button)
    Button testButton;

    public IntroSimpleScreen()
    {
        super(R.layout.intro_simple_screen);
    }

    @Override
    protected IPresenter createPresenter(FragmentScreen fragmentScreen, Bundle savedInstanceState)
    {
        return new IntroSimplePresenter(AndroidSchedulers.mainThread(), this);
    }

    @Override
    public int getDefaultBackgroundColor()
    {
        return ContextCompat.getColor(getActivity(), R.color.intro_simple_bg);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor)
    {
        introWelcomeMainLayout.setBackgroundColor(backgroundColor);
    }

    @Override
    public Observable testBtnClick()
    {
        return ViewObservable.clicks(testButton);
    }

    @Override
    public void displayMessage()
    {
        Toast.makeText(getContext(), "Test message from intro screen", Toast.LENGTH_LONG).show();
    }
}
