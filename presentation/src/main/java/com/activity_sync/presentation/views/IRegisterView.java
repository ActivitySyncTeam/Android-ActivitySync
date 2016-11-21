package com.activity_sync.presentation.views;

import rx.Observable;

public interface IRegisterView
{
    Observable registerBtnClick();

    Observable alreadyRegisteredClick();

    String firstName();

    String lastName();

    String nickName();

    String email();

    String password();

    void firstNameErrorText(String error);

    void lastNameErrorText(String error);

    void nickNameErrorText(String error);

    void emailErrorText(String error);

    void passwordErrorText(String error);

    void firstNameErrorEnabled(boolean enabled);

    void lastNameErrorEnabled(boolean enabled);

    void nickNameErrorEnabled(boolean enabled);

    void emailErrorEnabled(boolean enabled);

    void passwordErrorEnabled(boolean enabled);

    String emptyFieldErrorText();
}
