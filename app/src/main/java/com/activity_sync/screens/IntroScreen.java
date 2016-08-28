package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.activity_sync.R;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;


import java.util.ArrayList;

public class IntroScreen extends AppIntro2
{
    private ArrayList<Integer> colors = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragmentWithColor(new IntroWelcomeScreen(), R.color.red);
        addFragmentWithColor(new IntroPermissionScreen(), R.color.orange);
        addFragmentWithColor(new IntroWelcomeScreen(), R.color.yellow);
        addFragmentWithColor(new IntroPermissionScreen(), R.color.green);
        addFragmentWithColor(new IntroWelcomeScreen(), R.color.darkgreen);

        setColorTransitionsEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        super.onSkipPressed(currentFragment);

        getPager().setCurrentItem(4, true);
    }

    private void addFragmentWithColor(FragmentScreen fragmentScreen, int color)
    {
        addSlide(fragmentScreen);
        colors.add(color);
    }
}
