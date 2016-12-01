package com.activity_sync.renderers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.activity_sync.R;
import com.activity_sync.presentation.action_listeners.IParticipantActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;

public class RegisteredParticipantsRenderer extends ParticipantsRendererBase
{
    protected IParticipantActionListener actionListener;

    @Bind(R.id.decline_sign)
    ImageView declineSign;

    @OnClick(R.id.decline_sign)
    public void onDeclineButtonClick(View view)
    {
        if (actionListener == null)
        {
            return;
        }
        actionListener.onDeclineButtonClick(getContent());
    }

    public RegisteredParticipantsRenderer(Context context, int layoutRes, boolean isOrganizer, IParticipantActionListener actionListener)
    {
        super(context, layoutRes, isOrganizer);
        this.actionListener = actionListener;
    }

    @Override
    public void render()
    {
        super.render();

        if(isOrganizer)
        {
            declineSign.setVisibility(View.VISIBLE);
        }
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder(Context context, boolean isOrganizer, IParticipantActionListener actionListener)
        {
            super(Arrays.asList(new RegisteredParticipantsRenderer(context, R.layout.participant_item_view, isOrganizer, actionListener)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return RegisteredParticipantsRenderer.class;
        }
    }
}
