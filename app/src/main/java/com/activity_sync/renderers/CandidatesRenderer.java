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

public class CandidatesRenderer extends RegisteredParticipantsRenderer
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

    public CandidatesRenderer(Context context, int layoutRes, boolean isOrganizer, IParticipantActionListener actionListener)
    {
        super(context, layoutRes, isOrganizer, actionListener);
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
        public Builder(Context context, boolean isOrganizer, IParticipantActionListener actionListener)
        {
            super(Arrays.asList(new CandidatesRenderer(context, R.layout.participant_item_view, isOrganizer, actionListener)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return CandidatesRenderer.class;
        }
    }
}
