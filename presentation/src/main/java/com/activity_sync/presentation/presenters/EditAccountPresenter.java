package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.UserUpdate;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IEditAccountView;

import rx.Scheduler;

public class EditAccountPresenter extends Presenter<IEditAccountView>
{
    private Scheduler uiThread;
    private UserUpdate userUpdateDetails;

    public EditAccountPresenter(IEditAccountView view, Scheduler uiThread)
    {
        super(view);
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.onSaveClick().observeOn(uiThread).subscribe(o -> {
            this.userUpdateDetails = view.getUserUpdateDetails();

            boolean canContinue = true;

            if (StringUtils.isNullOrEmpty(userUpdateDetails.getFirstName()))
            {
                view.firstNameErrorEnabled(true);
                view.firstNameErrorText(view.emptyFieldErrorText());
                canContinue = false;
            }

            if (StringUtils.isNullOrEmpty(userUpdateDetails.getLastName()))
            {
                view.lastNameErrorEnabled(true);
                view.lastNameErrorText(view.emptyFieldErrorText());
                canContinue = false;
            }

            if (StringUtils.isNullOrEmpty(userUpdateDetails.getEmail()))
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
                } else
                {
                    view.saveFailed("Wrong password.");
                }
            }
        }));
    }
}
