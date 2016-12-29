package com.activity_sync.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.activity_sync.R;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.services.Navigator;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;

public abstract class ScreenWithMenu extends Screen
{
    @Bind(R.id.navigation_view_main)
    NavigationView navigationView;

    @Bind(R.id.navigation_drawer_layout)
    DrawerLayout drawerLayout;

    @Inject
    CurrentUser currentUser;

    private MenuNavigator menuNavigator;

    protected ScreenWithMenu(int layoutResId)
    {
        super(layoutResId);
    }

    protected abstract int getMenuId();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initMenu();

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_corner);
        }
    }

    @Override
    public void setTitle(CharSequence title)
    {
        super.setTitle(title);
        if (getSupportActionBar() != null)
        {
            this.getSupportActionBar().setTitle(title);
        }
    }

    public void setTitle(@StringRes int titleRes)
    {
        this.setTitle(getString(titleRes));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMenu()
    {
        clearAndInflateMenu();

        menuNavigator = new MenuNavigator(new Navigator(this), () -> this.drawerLayout.closeDrawers());

        menuNavigator.addAction(R.id.menu_dummy, INavigator::openDummyScreen);

        menuNavigator.addAction(R.id.menu_events, INavigator::openEventsScreen);

        menuNavigator.addAction(R.id.menu_friends, INavigator::openFriendsScreen);

        menuNavigator.addAction(R.id.menu_my_profile, INavigator::openMyProfileScreen);

        menuNavigator.addAction(R.id.menu_settings, INavigator::openSettingsScreen);

        menuNavigator.addAction(R.id.menu_create_event, INavigator::openEventCreatorScreen);

        menuNavigator.addAction(R.id.menu_logout, navigator -> {
            currentUser.logout();
            navigator.stopBackgroundService();
            navigator.openLoginScreen();
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (getMenuId() == menuItem.getItemId())
            {
                drawerLayout.closeDrawers();
                return true;
            }
            menuNavigator.runAction(menuItem.getItemId());
            return true;
        });
    }

    private void clearAndInflateMenu()
    {
        navigationView.getMenu().clear();
        navigationView.inflateMenu(getMenuType());
    }

    private int getMenuType()
    {
        return R.menu.navigation_menu;
    }

    @Override
    public void startActivity(Intent intent)
    {
        drawerLayout.closeDrawers();
        super.startActivity(intent);
    }

    private static class MenuNavigator
    {
        private final INavigator navigator;
        private final OnBeforeAction onBeforeAction;
        private final Map<Integer, NavigatorAction> actions = new HashMap<>();

        public MenuNavigator(INavigator navigator, OnBeforeAction onBeforeAction)
        {
            this.navigator = navigator;
            this.onBeforeAction = onBeforeAction;
        }

        public void addAction(@IdRes int id, NavigatorAction action)
        {
            actions.put(id, action);
        }

        public void runAction(@IdRes int id)
        {
            if (actions.get(id) == null)
            {
                return;
            }
            onBeforeAction.execute();
            actions.get(id).execute(navigator);
        }

        public interface NavigatorAction
        {
            void execute(INavigator navigator);
        }

        public interface OnBeforeAction
        {
            void execute();
        }
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || drawerLayout.isDrawerOpen(GravityCompat.END))
        {
            drawerLayout.closeDrawers();
        }
        else
        {
            super.onBackPressed();
        }
    }
}