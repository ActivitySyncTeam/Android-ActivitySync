package com.activity_sync.presentation.views;

import rx.Observable;

public interface IChangePasswordView
{
    void oldPasswordErrorEnabled(boolean enabled);

    void newPasswordErrorEnabled(boolean enabled);

    void confirmNewPasswordErrorEnabled(boolean enabled);

    void oldPasswordErrorText(String error);

    void newPasswordErrorText(String error);

    void confirmNewPasswordErrorText(String error);

    Observable onSaveClick();

    String getOldPassword();

    String getNewPassword();

    String getConfirmNewPassword();

    String emptyFieldErrorText();

    void saveSucceded();

    void saveFailed(String message);

    void close();
}
