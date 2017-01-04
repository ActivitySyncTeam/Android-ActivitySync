package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IEditAccountView;

import rx.Scheduler;

public class EditAccountPresenter extends Presenter<IEditAccountView>
{
    private final Scheduler uiThread;
    private final IApiService apiService;
    private User user;

    public EditAccountPresenter(IEditAccountView view, Scheduler uiThread, IApiService apiService, User user)
    {
        super(view);
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.user = user;
    }

    @Override
    public void start()
    {
        super.start();

        view.setUserUpdateDetails(user);

        subscriptions.add(view.onSaveClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    boolean canContinue = true;

                    if (StringUtils.isNullOrEmpty(view.getFirstName()))
                    {
                        view.firstNameErrorEnabled(true);
                        view.firstNameErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.getLastName()))
                    {
                        view.lastNameErrorEnabled(true);
                        view.lastNameErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.getEmail()))
                    {
                        view.emailErrorEnabled(true);
                        view.emailErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (canContinue)
                    {
                        apiService.updateUser(view.getFirstName(), view.getLastName(), view.getSignature(), view.getEmail())
                                .observeOn(uiThread)
                                .subscribe(response -> {
                                    view.saveSucceded();
                                    view.close();
                                }, this::handleError);
                    }
                }));
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        view.saveFailed();
    }
}
