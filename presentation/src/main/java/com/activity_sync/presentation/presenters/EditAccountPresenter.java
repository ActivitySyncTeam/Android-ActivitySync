package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IEditAccountView;

import rx.Scheduler;

public class EditAccountPresenter extends Presenter<IEditAccountView>
{
    private Scheduler uiThread;
    private User user;

    public EditAccountPresenter(IEditAccountView view, Scheduler uiThread, User user)
    {
        super(view);
        this.uiThread = uiThread;
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

                    if (StringUtils.isNullOrEmpty(view.getPassword()))
                    {
                        view.passwordErrorEnabled(true);
                        view.passwordErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (canContinue)
                    {
                        boolean callSucceded = true;
                        if (callSucceded)
                        {
                            view.saveSucceded();
                            view.close();
                        }
                        else
                        {
                            view.saveFailed("Wrong password.");
                        }
                    }
                }));
    }
}
