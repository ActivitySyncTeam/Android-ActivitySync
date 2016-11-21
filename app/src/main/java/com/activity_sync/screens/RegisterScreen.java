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
import com.activity_sync.presentation.presenters.RegisterPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IRegisterView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class RegisterScreen extends Screen implements IRegisterView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.register_btn)
    Button registerBtn;

    @Bind(R.id.already_registered)
    TextView alreadyRegisteredTv;

    @Bind(R.id.text_input_layout_full_name)
    TextInputLayout inputFullNameLayout;

    @Bind(R.id.text_input_layout_login)
    TextInputLayout inputLoginLayout;

    @Bind(R.id.text_input_layout_password)
    TextInputLayout inputPasswordLayout;

    @Bind(R.id.full_name)
    AppCompatEditText fullNameEditText;

    @Bind(R.id.login)
    AppCompatEditText loginEditText;

    @Bind(R.id.password)
    AppCompatEditText passwordEditText;

    public RegisterScreen()
    {
        super(R.layout.register_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new RegisterPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        prepareEditTexts();
    }

    @Override
    public Observable registerBtnClick()
    {
        return ViewObservable.clicks(registerBtn);
    }

    @Override
    public Observable alreadyRegisteredClick()
    {
        return ViewObservable.clicks(alreadyRegisteredTv);
    }

    @Override
    public String fullName()
    {
        return fullNameEditText.getText().toString();
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
    public void fullNameErrorText(String error)
    {
        inputFullNameLayout.setError(error);
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
    public void fullNameErrorEnabled(boolean enabled)
    {
        inputFullNameLayout.setErrorEnabled(enabled);
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

    private void prepareEditTexts()
    {
        fullNameEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                fullNameErrorEnabled(false);
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
