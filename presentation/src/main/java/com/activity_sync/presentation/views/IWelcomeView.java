package com.activity_sync.presentation.views;

import rx.Observable;

public interface IWelcomeView
{
    Observable loginBtnClick();

    Observable registerBtnClick();
}
