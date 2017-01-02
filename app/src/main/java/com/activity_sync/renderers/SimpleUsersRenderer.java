package com.activity_sync.renderers;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.action_listeners.IUserActionListener;
import com.activity_sync.presentation.models.User;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;
import com.activity_sync.utils.StringBasedColorGenerator;
import com.amulyakhare.textdrawable.TextDrawable;

import java.util.Arrays;

import butterknife.Bind;

public class SimpleUsersRenderer extends Renderer<User>
{
    protected Context context;
    protected boolean shouldDisplayAllOptions;

    @Bind(R.id.user_name_image)
    ImageView usernameImage;

    @Bind(R.id.user_name)
    TextView username;

    public SimpleUsersRenderer(Context context, int layoutRes, boolean shouldDisplayAllOptions)
    {
        super(layoutRes);
        this.context = context;
        this.shouldDisplayAllOptions = shouldDisplayAllOptions;
    }

    @Override
    public void render()
    {
        username.setText(String.format("%s", getContent().getName()));

        TextDrawable drawable = TextDrawable.builder().buildRound(
                String.format("%s", getContent().getName().toUpperCase().charAt(0)),
                convertNameToColor(getContent().getName()));

        usernameImage.setImageDrawable(drawable);
    }

    public static class Builder extends RendererBuilder<User>
    {
        public Builder(Context context, boolean isOrganizer, IUserActionListener actionListener)
        {
            super(Arrays.asList(new SimpleUsersRenderer(context, R.layout.user_item_view, isOrganizer)));
        }

        @Override
        protected Class getPrototypeClass(User content)
        {
            return SimpleUsersRenderer.class;
        }
    }

    private int convertNameToColor(String name)
    {
        return Color.parseColor(StringBasedColorGenerator.convertStringToColor(String.format("%s", getContent().getName())));
    }
}