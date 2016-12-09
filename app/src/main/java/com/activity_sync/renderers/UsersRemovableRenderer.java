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

public class UsersRemovableRenderer extends SimpleUsersRenderer
{
    protected IUserActionListener actionListener;

    @Bind(R.id.decline_sign)
    ImageView declineSign;

    @OnClick(R.id.decline_sign)
    public void onDeclineButtonClick(View view)
    {
        if (actionListener == null)
        {
            return;
        }
        actionListener.onDeclineButtonAction(getContent());
    }

    public UsersRemovableRenderer(Context context, int layoutRes, boolean shouldDisplayAllOptions, IUserActionListener actionListener)
    {
        super(context, layoutRes, shouldDisplayAllOptions);
        this.actionListener = actionListener;
    }

    @Override
    public void render()
    {
        super.render();

        if(shouldDisplayAllOptions)
        {
            declineSign.setVisibility(View.VISIBLE);
        }
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder(Context context, boolean isOrganizer, IUserActionListener actionListener)
        {
            super(Arrays.asList(new UsersRemovableRenderer(context, R.layout.user_item_view, isOrganizer, actionListener)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return UsersRemovableRenderer.class;
        }
    }
}
