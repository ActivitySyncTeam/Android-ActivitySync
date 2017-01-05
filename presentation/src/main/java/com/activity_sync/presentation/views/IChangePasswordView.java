package com.activity_sync.presentation.views;

import rx.Observable;

public interface IChangePasswordView extends IScreenView
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

    String confirmedNotMatchingErrorText();

    String samePasswordsErrorText();

    void saveSucceded();

    void saveFailed();

    void close();
}
