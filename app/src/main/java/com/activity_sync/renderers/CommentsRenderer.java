package com.activity_sync.renderers;

import android.content.Context;
import android.widget.TextView;

import com.activity_sync.R;
import com.activity_sync.presentation.models.Comment;
import com.activity_sync.renderers.base.Renderer;
import com.activity_sync.renderers.base.RendererBuilder;

import java.util.Arrays;

import butterknife.Bind;

public class CommentsRenderer extends Renderer<Comment>
{
    private Context context;

    @Bind(R.id.comment_author)
    TextView commentAuthor;

    @Bind(R.id.comment_content)
    TextView commentContent;

    public CommentsRenderer(int layoutRes, Context context)
    {
        super(layoutRes);
        this.context = context;
    }

    @Override
    public void render()
    {
        commentAuthor.setText(String.format(context.getString(R.string.txt_comment_author), getContent().getName()));
        commentContent.setText(getContent().getComment());
    }

    public static class Builder extends RendererBuilder<Comment>
    {
        public Builder(Context context)
        {
            super(Arrays.asList(new CommentsRenderer(R.layout.comment_item_view, context)));
        }

        @Override
        protected Class getPrototypeClass(Comment content)
        {
            return CommentsRenderer.class;
        }
    }
}
