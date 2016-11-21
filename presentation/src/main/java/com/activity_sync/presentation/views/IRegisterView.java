package com.activity_sync.presentation.views;

import rx.Observable;

public interface IRegisterView
{
    Observable registerBtnClick();

    Observable alreadyRegisteredClick();

    String fullName();

    String login();

    String password();

    void fullNameErrorText(String error);

    void loginErrorText(String error);

    void passwordErrorText(String error);

    void fullNameErrorEnabled(boolean enabled);

    void loginErrorEnabled(boolean enabled);

    void passwordErrorEnabled(boolean enabled);
}
