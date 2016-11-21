package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IRegisterView;

import rx.Scheduler;

public class RegisterPresenter extends Presenter<IRegisterView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public RegisterPresenter(Scheduler uiThread, IRegisterView view, INavigator navigator)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.registerBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    boolean canContinue = true;

                    if (StringUtils.isNullOrEmpty(view.firstName()))
                    {
                        view.firstNameErrorEnabled(true);
                        view.firstNameErrorText("Please provide your first name");
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.lastName()))
                    {
                        view.lastNameErrorEnabled(true);
                        view.lastNameErrorText("Please provide your last name");
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.email()))
                    {
                        view.emailErrorEnabled(true);
                        view.emailErrorText("Please provide your email");
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.nickName()))
                    {
                        view.nickNameErrorEnabled(true);
                        view.nickNameErrorText("Please provide your nick");
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.password()))
                    {
                        view.passwordErrorEnabled(true);
                        view.passwordErrorText("Please provide your password");
                        canContinue = false;
                    }

                    if (canContinue)
                    {
                        navigator.openEventsScreen();
                    }
                })
        );

        subscriptions.add(view.alreadyRegisteredClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openLoginScreen();
                })
        );

    }
}
