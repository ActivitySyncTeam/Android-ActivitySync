package com.activity_sync.screens;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.activity_sync.R;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.presenters.AllEventsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.views.IEventsFragmentView;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;

public class AllEventsFragment extends EventsFragmentBase implements IEventsFragmentView
{
    @Bind(R.id.search_date_btn)
    Button searchDateButton;

    @Bind(R.id.spinner_discipline)
    Spinner disciplineSpinner;

    private PublishSubject<String> newDateOccurred = PublishSubject.create();

    @Override
    protected IPresenter createPresenter(FragmentScreen screen, Bundle savedInstanceState)
    {
        return new AllEventsPresenter(this, navigator, AndroidSchedulers.mainThread(), apiService);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        setFragmentToolbarVisibility(LinearLayout.VISIBLE);
        setCurrentDate();
    }

    private void setCurrentDate()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        setDate(String.format(getString(R.string.date_format), year, month, day, hour, minute));
    }

    @Override
    public Observable searchDateClick()
    {
        return ViewObservable.clicks(searchDateButton);
    }

    @Override
    public void openDatePicker()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerStyle, (view, selectedYear, selectedMonth, selectedDay) ->
        {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DatePickerStyle, (view1, selectedHour, selectedMinute) ->
            {
                newDateOccurred.onNext(String.format(getString(R.string.date_format), selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute));

            }, hour, minute, false);

            timePickerDialog.show();

        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public Observable<String> newDateEvent()
    {
        return newDateOccurred;
    }

    @Override
    public void setDate(String date)
    {
        searchDateButton.setText(date);
    }

    @Override
    public void prepareDisciplineSpinner(List<Discipline> disciplines)
    {
        ArrayAdapter<Discipline> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_search_bar_item, disciplines);
        adapter.setDropDownViewResource(R.layout.spinner_default_dropdown_item);
        disciplineSpinner.setAdapter(adapter);
    }

    @Override
    public Discipline getDiscipline()
    {
        return (Discipline) disciplineSpinner.getSelectedItem();
    }
}
