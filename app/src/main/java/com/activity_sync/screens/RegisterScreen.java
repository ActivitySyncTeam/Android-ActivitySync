package com.activity_sync.screens;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IRegisterView;

import javax.inject.Inject;

import butterknife.Bind;

public class RegisterScreen extends Screen implements IRegisterView
{
    @Inject
    INavigator navigator;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.create_account)
    TextView createAccountTv;

    @Bind(R.id.text_input_layout_login)
    TextInputLayout inputLoginLayout;

    @Bind(R.id.text_input_layout_password)
    TextInputLayout inputPasswordLayout;

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
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
    }
}
