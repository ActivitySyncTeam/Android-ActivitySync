package com.activity_sync.custom;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.activity_sync.behaviours.MoveUpwardBehavior;

@CoordinatorLayout.DefaultBehavior(MoveUpwardBehavior.class)
public class MoveUpwardAwareLinearLayout extends LinearLayout
{
    public MoveUpwardAwareLinearLayout(Context context)
    {
        super(context);
    }

    public MoveUpwardAwareLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MoveUpwardAwareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
}