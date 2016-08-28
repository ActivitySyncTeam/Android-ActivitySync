package com.activity_sync.screens;

import android.os.Bundle;
import com.github.paolorotolo.appintro.AppIntro2;

public class IntroScreen extends AppIntro2
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new IntroWelcomeScreen());
        addSlide(new IntroHappyScreen());
        addSlide(new IntroSimpleScreen());
        addSlide(new IntroSimpleScreen());

        setColorTransitionsEnabled(true);
    }
}
