package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import rx.Observable;

public interface IEditAccountView extends IScreenView
{
    void saveSucceded();

    void saveFailed();

    void firstNameErrorEnabled(boolean enabled);

    void lastNameErrorEnabled(boolean enabled);

    void emailErrorEnabled(boolean enabled);

    void firstNameErrorText(String error);

    void lastNameErrorText(String error);

    void emailErrorText(String error);

    String emptyFieldErrorText();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getSignature();

    Observable onSaveClick();

    void close();

    void setUserUpdateDetails(User user);
}
