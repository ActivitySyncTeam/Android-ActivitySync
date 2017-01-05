package com.activity_sync.presentation.views;

import rx.Observable;

public interface ILoginView extends IScreenView
{
    Observable loginBtnClick();

    Observable createAccountClick();

    String login();

    String password();

    void loginErrorText(String error);

    void passwordErrorText(String error);

    void loginErrorEnabled(boolean enabled);

    void passwordErrorEnabled(boolean enabled);

    String emptyFieldErrorText();

    void progressBarVisible(boolean isVisible);
}
