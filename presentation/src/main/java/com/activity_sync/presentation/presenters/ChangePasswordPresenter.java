package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IChangePasswordView;

import rx.Scheduler;

public class ChangePasswordPresenter extends Presenter<IChangePasswordView>
{
    private Scheduler uiThread;

    public ChangePasswordPresenter(IChangePasswordView view, Scheduler uiThread)
    {
        super(view);
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.onSaveClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    boolean canContinue = true;

                    if (!(StringUtils.isNullOrEmpty(view.getNewPassword()) || StringUtils.isNullOrEmpty(view.getConfirmNewPassword())))
                    {
                        if (!view.getNewPassword().equals(view.getConfirmNewPassword()))
                        {
                            view.confirmNewPasswordErrorEnabled(true);
                            view.confirmNewPasswordErrorText(view.confirmedNotMatchingErrorText());
                            canContinue = false;
                        }
                    }

                    if (!(StringUtils.isNullOrEmpty(view.getOldPassword()) || StringUtils.isNullOrEmpty(view.getNewPassword())))
                    {
                        if (view.getNewPassword().equals(view.getOldPassword()))
                        {
                            view.newPasswordErrorEnabled(true);
                            view.newPasswordErrorText(view.samePasswordsErrorText());
                            canContinue = false;
                        }
                    }

                    if (StringUtils.isNullOrEmpty(view.getOldPassword()))
                    {
                        view.oldPasswordErrorEnabled(true);
                        view.oldPasswordErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.getNewPassword()))
                    {
                        view.newPasswordErrorEnabled(true);
                        view.newPasswordErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.getConfirmNewPassword()))
                    {
                        view.confirmNewPasswordErrorEnabled(true);
                        view.confirmNewPasswordErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (canContinue)
                    {
                        boolean callSucceded = true;
                        if (callSucceded)
                        {
                            view.saveSucceded();
                            view.close();
                        } else
                        {
                            view.saveFailed("Wrong password.");
                        }
                    }
                }));
    }
}
