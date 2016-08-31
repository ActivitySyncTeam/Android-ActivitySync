package com.activity_sync.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import com.activity_sync.R;
import butterknife.Bind;

public abstract class ScreenWithMenu extends Screen
{
    @Bind(R.id.navigation_view_main)
    NavigationView navigationView;

    @Bind(R.id.navigation_drawer_layout)
    DrawerLayout drawerLayout;

    protected ScreenWithMenu()
    {
        super(R.layout.screen_with_menu);
    }

    protected ScreenWithMenu(int layoutResId)
    {
        super(layoutResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || drawerLayout.isDrawerOpen(GravityCompat.END))
        {
            drawerLayout.closeDrawers();
            return;
        }
        else
        {
            super.onBackPressed();
        }
    }
}