package com.activity_sync.screens;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.LoginPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ILoginView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class LoginScreen extends Screen implements ILoginView
{
    @Inject
    INavigator navigator;

    @Inject
    IApiService apiService;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.create_account)
    TextView createAccountTv;

    @Bind(R.id.text_input_layout_email)
    TextInputLayout inputLoginLayout;

    @Bind(R.id.text_input_layout_password)
    TextInputLayout inputPasswordLayout;

    @Bind(R.id.email)
    AppCompatEditText loginEditText;

    @Bind(R.id.password)
    AppCompatEditText passwordEditText;

    public LoginScreen()
    {
        super(R.layout.login_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        prepareEditTexts();
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new LoginPresenter(AndroidSchedulers.mainThread(), this, navigator, apiService);
    }

    @Override
    public Observable loginBtnClick()
    {
        return ViewObservable.clicks(loginBtn);
    }

    @Override
    public Observable createAccountClick()
    {
        return ViewObservable.clicks(createAccountTv);
    }

    @Override
    public String login()
    {
        return loginEditText.getText().toString();
    }

    @Override
    public String password()
    {
        return passwordEditText.getText().toString();
    }

    @Override
    public void loginErrorText(String error)
    {
        inputLoginLayout.setError(error);
    }

    @Override
    public void passwordErrorText(String error)
    {
        inputPasswordLayout.setError(error);
    }

    @Override
    public void loginErrorEnabled(boolean enabled)
    {
        inputLoginLayout.setErrorEnabled(enabled);
    }

    @Override
    public void passwordErrorEnabled(boolean enabled)
    {
        inputPasswordLayout.setErrorEnabled(enabled);
    }

    @Override
    public String emptyFieldErrorText()
    {
        return getString(R.string.err_empty_field);
    }

    private void prepareEditTexts()
    {
        loginEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                loginErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                passwordErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }
}
