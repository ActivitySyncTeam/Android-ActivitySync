package com.activity_sync.presentation.views;

import rx.Observable;

public interface IEditAccountView
{
    void saveSucceded();

    void saveFailed(String message);

    void firstNameErrorEnabled(boolean enabled);

    void lastNameErrorEnabled(boolean enabled);

    void emailErrorEnabled(boolean enabled);

    void passwordErrorEnabled(boolean enabled);

    void firstNameErrorText(String error);

    void lastNameErrorText(String error);

    void emailErrorText(String error);

    void passwordErrorText(String error);

    String emptyFieldErrorText();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getSignature();

    String getPassword();

    Observable onSaveClick();

    void close();
}
