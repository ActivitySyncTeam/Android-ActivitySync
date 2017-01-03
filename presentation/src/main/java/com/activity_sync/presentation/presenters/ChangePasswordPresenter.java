package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IChangePasswordView;

import rx.Scheduler;

public class ChangePasswordPresenter extends Presenter<IChangePasswordView>
{
    private final Scheduler uiThread;
    private final IApiService apiService;

    public ChangePasswordPresenter(IChangePasswordView view, Scheduler uiThread, IApiService apiService)
    {
        super(view);
        this.uiThread = uiThread;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.onSaveClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    boolean canContinue = true;

                    if (newAndConfirmInsertedAndNotMatching(view))
                    {
                        view.confirmNewPasswordErrorEnabled(true);
                        view.confirmNewPasswordErrorText(view.confirmedNotMatchingErrorText());
                        canContinue = false;
                    }

                    if (oldAndNewInsertedAndTheSame(view))
                    {
                        view.newPasswordErrorEnabled(true);
                        view.newPasswordErrorText(view.samePasswordsErrorText());
                        canContinue = false;
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
                        apiService.changePassword(view.getOldPassword(), view.getNewPassword())
                                .observeOn(uiThread)
                                .subscribe(response -> {
                                    view.saveSucceded();
                                    view.close();
                                }, this::handleError);
                    }
                }));
    }

    private boolean newAndConfirmInsertedAndNotMatching(IChangePasswordView view)
    {
        return !(StringUtils.isNullOrEmpty(view.getNewPassword()) || StringUtils.isNullOrEmpty(view.getConfirmNewPassword()))
                && !view.getNewPassword().equals(view.getConfirmNewPassword());
    }

    private boolean oldAndNewInsertedAndTheSame(IChangePasswordView view)
    {
        return !(StringUtils.isNullOrEmpty(view.getNewPassword()) || StringUtils.isNullOrEmpty(view.getOldPassword()))
                && view.getNewPassword().equals(view.getOldPassword());
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        view.saveFailed();
    }
}
