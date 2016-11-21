package com.activity_sync.presentation.views;

import rx.Observable;

public interface ILoginView
{
    Observable loginBtnClick();

    Observable createAccountClick();

    String login();

    String password();

    void loginErrorText(String error);

    void passwordErrorText(String error);

    void loginErrorEnabled(boolean enabled);

    void passwordErrorEnabled(boolean enabled);
}
