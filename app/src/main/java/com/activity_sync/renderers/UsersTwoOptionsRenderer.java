package com.activity_sync.renderers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.activity_sync.R;
import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

public class UsersTwoOptionsRenderer extends UsersRemovableRenderer
{
    @Bind(R.id.approve_sign)
    ImageView approveSign;

    @OnClick(R.id.approve_sign)
    public void onApproveButtonClick(View view)
    {
        if (actionListener == null)
        {
            return;
        }
        actionListener.onAcceptButtonAction(getContent());
    }

    public UsersTwoOptionsRenderer(Context context, int layoutRes, boolean isOrganizer, IUserActionListener actionListener)
    {
        super(context, layoutRes, isOrganizer, actionListener);
    }

    public UsersTwoOptionsRenderer(Context context, int layoutRes, IUserActionListener actionListener)
    {
        super(context, layoutRes, actionListener);
    }

    @Override
    public void render()
    {
        super.render();

        if (isOrganizer)
        {
            approveSign.setVisibility(View.VISIBLE);
        }
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder(Context context, boolean isOrganizer, IUserActionListener actionListener)
        {
            super(Arrays.asList(new UsersTwoOptionsRenderer(context, R.layout.user_item_view, isOrganizer, actionListener)));
        }

        public Builder(Context context, IUserActionListener actionListener)
        {
            super(Arrays.asList(new UsersTwoOptionsRenderer(context, R.layout.user_item_view, actionListener)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return UsersTwoOptionsRenderer.class;
        }
    }
}
