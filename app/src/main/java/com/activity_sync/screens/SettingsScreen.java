package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.SettingsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISettingsView;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.subjects.PublishSubject;

public class SettingsScreen extends ScreenWithMenu implements ISettingsView
{
    @Inject
    INavigator navigator;

    @Inject
    IPermanentStorage permanentStorage;

    @Bind(R.id.notification_switch)
    SwitchCompat notificationSwitch;

    @Bind(R.id.notification_sound_checkbox)
    AppCompatCheckBox notificationSoundCheckBox;

    @Bind(R.id.notification_vibrate_checkbox)
    AppCompatCheckBox notificationVibrateCheckBox;

    @Bind(R.id.location_switch)
    SwitchCompat locationSwitch;

    @Bind(R.id.days_seekbar)
    AppCompatSeekBar daysSeekbar;

    @Bind(R.id.range_seekbar)
    AppCompatSeekBar rangeSeekbar;

    @Bind(R.id.search_days_ahead_label)
    TextView searchDaysAheadLabel;

    @Bind(R.id.search_range_label)
    TextView searchRangeLabel;

    private PublishSubject<Boolean> enableNotificationsChange = PublishSubject.create();
    private PublishSubject<Boolean> enableLocationChange = PublishSubject.create();
    private PublishSubject<Boolean> enableNotificationsSoundChange = PublishSubject.create();
    private PublishSubject<Boolean> enableNotificationsVibrateChange = PublishSubject.create();
    private PublishSubject<Integer> searchDaysChange = PublishSubject.create();
    private PublishSubject<Integer> searchRangeChange = PublishSubject.create();

    public SettingsScreen()
    {
        super(R.layout.settings_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_settings);

        initializeListeners();
    }

    private void initializeListeners()
    {
        notificationSwitch.setOnCheckedChangeListener(this::notificationSwitchChanged);
        notificationSoundCheckBox.setOnCheckedChangeListener((compoundButton, b) -> enableNotificationsSoundChange.onNext(b));
        notificationVibrateCheckBox.setOnCheckedChangeListener((compoundButton, b) -> enableNotificationsVibrateChange.onNext(b));
        locationSwitch.setOnCheckedChangeListener((compoundButton, b) -> enableLocationChange.onNext(b));
        daysSeekbar.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (i < 1)
                {
                    seekBar.setProgress(1);
                }
                searchDaysAheadLabel.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                searchDaysAheadLabel.setText(String.valueOf(seekBar.getProgress()));
                searchDaysChange.onNext(seekBar.getProgress());
            }
        });
        rangeSeekbar.setOnSeekBarChangeListener(new AppCompatSeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                if (i < 1)
                {
                    seekBar.setProgress(1);
                }
                searchRangeLabel.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                searchRangeLabel.setText(String.valueOf(seekBar.getProgress()));
                searchRangeChange.onNext(seekBar.getProgress());
            }
        });
    }

    @Override
    public void loadSavedValues()
    {
        notificationSwitch.setChecked(permanentStorage.retrieveBoolean(IPermanentStorage.IS_NOTIFICATION_ENABLED, IPermanentStorage.IS_NOTIFICATION_ENABLED_DEFAULT));
        setNotificationCheckBoxesEnabled(notificationSwitch.isChecked());
        locationSwitch.setChecked(permanentStorage.retrieveBoolean(IPermanentStorage.IS_LOCATION_ENABLED, IPermanentStorage.IS_LOCATION_ENABLED_DEFAULT));
        notificationVibrateCheckBox.setChecked(permanentStorage.retrieveBoolean(IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED, IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED_DEFAULT));
        notificationSoundCheckBox.setChecked(permanentStorage.retrieveBoolean(IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED, IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED_DEFAULT));
        daysSeekbar.setProgress(permanentStorage.retrieveInteger(IPermanentStorage.SEARCH_DAYS_AHEAD, IPermanentStorage.SEARCH_DAYS_AHEAD_DEFAULT));
        searchDaysAheadLabel.setText(String.valueOf(daysSeekbar.getProgress()));
        rangeSeekbar.setProgress(permanentStorage.retrieveInteger(IPermanentStorage.SEARCH_RANGE, IPermanentStorage.SEARCH_RANGE_DEFAULT));
        searchRangeLabel.setText(String.valueOf(rangeSeekbar.getProgress()));
    }

    private void notificationSwitchChanged(CompoundButton c, Boolean value)
    {
        setNotificationCheckBoxesEnabled(value);
        enableNotificationsChange.onNext(value);
    }

    private void setNotificationCheckBoxesEnabled(Boolean value)
    {
        notificationSoundCheckBox.setEnabled(value);
        notificationVibrateCheckBox.setEnabled(value);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new SettingsPresenter(this, permanentStorage);
    }

    public Observable<Boolean> enableNotificationsChange()
    {
        return enableNotificationsChange;
    }

    public Observable<Boolean> enableLocationChange()
    {
        return enableLocationChange;
    }

    public Observable<Boolean> enableNotificationsSoundChange()
    {
        return enableNotificationsSoundChange;
    }

    public Observable<Boolean> enableNotificationsVibrateChange()
    {
        return enableNotificationsVibrateChange;
    }

    @Override
    public Observable<Integer> searchDaysChange()
    {
        return searchDaysChange;
    }

    @Override
    public Observable<Integer> searchRangeChange()
    {
        return searchRangeChange;
    }
}
