package com.activity_sync.presentation.views;

import rx.Observable;

public interface ILoginView
{
    Observable loginBtnClick();

    Observable createAccountClick();
}
