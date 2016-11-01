package com.activity_sync.renderers;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;
import com.amulyakhare.textdrawable.TextDrawable;

import java.util.Arrays;

import butterknife.Bind;

public class ParticipantsRenderer extends Renderer<User>
{
    @Bind(R.id.participant_creditability_image)
    ImageView participantCreditabilityImage;

    @Bind(R.id.participant_name)
    TextView participantName;

    public ParticipantsRenderer(int layoutRes)
    {
        super(layoutRes);
    }

    @Override
    public void render()
    {
        participantName.setText(String.format("%s %s", getContent().getUserDetails().getFirstName(), getContent().getUserDetails().getLastName()));

        TextDrawable drawable = TextDrawable.builder().buildRound(String.format("%d", getContent().getCreditability()), Color.RED);
        participantCreditabilityImage.setImageDrawable(drawable);
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder()
        {
            super(Arrays.asList(new ParticipantsRenderer(R.layout.participant_item_view)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return ParticipantsRenderer.class;
        }
    }
}
