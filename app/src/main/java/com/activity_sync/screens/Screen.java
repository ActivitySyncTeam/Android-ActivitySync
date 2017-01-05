package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IScreenView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

public abstract class Screen extends BaseActivity implements IScreenView
{
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MenuItem actionProgress;
    protected IPresenter presenter;
    private final int layoutResId;
    protected Screen(int layoutResId)
    {
        this.layoutResId = layoutResId;
    }
    public IPresenter presenter()
    {
        return this.presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(this.layoutResId);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        inject(this);
        this.initToolbar();
        this.presenter = this.createPresenter(this, savedInstanceState);
    }

    private void initToolbar()
    {
        if (toolbar == null)
        {
            return;
        }

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (presenter != null)
        {
            this.presenter.start();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (presenter != null)
        {
            this.presenter.resume();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (presenter != null)
        {
            this.presenter.stop();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.action_progress:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        actionProgress = menu.findItem(R.id.action_progress);
        MenuItemCompat.getActionView(actionProgress);
        return super.onCreateOptionsMenu(menu);
    }

    public void showDialog(int title, int message, PublishSubject confirmClicked)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);

        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, (dialog, which) ->
        {
            confirmClicked.onNext(this);
        });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, (dialog, which) ->
        {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void displayDefaultError()
    {
        Toast.makeText(this, "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayNetworkError()
    {
        Toast.makeText(this, "Something went wrong with network. Please try again later.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar()
    {
        if (actionProgress != null)
        {
            actionProgress.setVisible(true);
        }
    }

    @Override
    public void hideProgressBar()
    {
        if (actionProgress != null)
        {
            actionProgress.setVisible(false);
        }
    }

    protected abstract IPresenter createPresenter(Screen screen, Bundle savedInstanceState);

    protected abstract void inject(Context screen);
}
