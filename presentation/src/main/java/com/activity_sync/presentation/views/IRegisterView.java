package com.activity_sync.presentation.views;

import rx.Observable;

public interface IRegisterView extends IScreenView
{
    Observable registerBtnClick();

    Observable alreadyRegisteredClick();

    String firstName();

    String lastName();

    String userName();

    String email();

    String password();

    void firstNameErrorText(String error);

    void lastNameErrorText(String error);

    void userNameErrorText(String error);

    void emailErrorText(String error);

    void passwordErrorText(String error);

    void firstNameErrorEnabled(boolean enabled);

    void lastNameErrorEnabled(boolean enabled);

    void userNameErrorEnabled(boolean enabled);

    void emailErrorEnabled(boolean enabled);

    void passwordErrorEnabled(boolean enabled);

    String emptyFieldErrorText();

    void progressBarVisible(boolean isVisible);
}
