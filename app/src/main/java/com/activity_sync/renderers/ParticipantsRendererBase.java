package com.activity_sync.renderers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.action_listeners.IParticipantActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;
import com.activity_sync.utils.CredibilityService;
import com.amulyakhare.textdrawable.TextDrawable;

import java.util.Arrays;

import butterknife.Bind;

public class ParticipantsRendererBase extends Renderer<User>
{
    protected Context context;
    protected boolean isOrganizer;

    @Bind(R.id.participant_credibility_image)
    ImageView participantCredibilityImage;

    @Bind(R.id.participant_name)
    TextView participantName;

    public ParticipantsRendererBase(Context context, int layoutRes, boolean isOrganizer)
    {
        super(layoutRes);
        this.context = context;
        this.isOrganizer = isOrganizer;
    }

    @Override
    public void render()
    {
        participantName.setText(String.format("%s %s", getContent().getName(), getContent().getSurname()));
        CredibilityService credibilityService = new CredibilityService(context, getContent().getCredibility());
        TextDrawable drawable = TextDrawable.builder().buildRound(String.format("%d", getContent().getCredibility()), credibilityService.getColor());
        participantCredibilityImage.setImageDrawable(drawable);
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder(Context context, boolean isOrganizer, IParticipantActionListener actionListener)
        {
            super(Arrays.asList(new ParticipantsRendererBase(context, R.layout.participant_item_view, isOrganizer)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return ParticipantsRendererBase.class;
        }
    }
}