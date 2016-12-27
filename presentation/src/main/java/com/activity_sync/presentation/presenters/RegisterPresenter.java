package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IRegisterView;
import com.innahema.tuples.Tuple2;

import rx.Observable;
import rx.Scheduler;
import timber.log.Timber;

public class RegisterPresenter extends Presenter<IRegisterView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IApiService apiService;

    public RegisterPresenter(Scheduler uiThread, IRegisterView view, INavigator navigator, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
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
                        view.firstNameErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.lastName()))
                    {
                        view.lastNameErrorEnabled(true);
                        view.lastNameErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.email()))
                    {
                        view.emailErrorEnabled(true);
                        view.emailErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.nickName()))
                    {
                        view.nickNameErrorEnabled(true);
                        view.nickNameErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (StringUtils.isNullOrEmpty(view.password()))
                    {
                        view.passwordErrorEnabled(true);
                        view.passwordErrorText(view.emptyFieldErrorText());
                        canContinue = false;
                    }

                    if (canContinue)
                    {

                        Observable.zip(getRegisterQuery(), getClientDetailsQuery(), (registerQuery, clientDetailsQuery) -> new Tuple2<>(registerQuery, clientDetailsQuery))
                                .observeOn(uiThread)
                                .subscribe(tuple -> {

                                    Timber.i("Client username: %s, Client id: %s", tuple.val0.getRegisterResponseDetails().getUsername(), tuple.val1.getClientId());

                                    navigator.openEventsScreen();

                                }, this::handleError);
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

    private Observable<RegisterResponse> getRegisterQuery()
    {
        return apiService.registerUser(view.nickName(), view.password(), view.firstName(), view.lastName(), view.email());
    }

    private Observable<ClientDetails> getClientDetailsQuery()
    {
        return apiService.getClientDetails();
    }

    private void handleError(Throwable error)
    {
        Timber.d(error.getMessage());
        view.displayDefaultError();
    }
}
