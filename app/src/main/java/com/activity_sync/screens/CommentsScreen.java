package com.activity_sync.screens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.presenters.CommentsPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ICommentsView;
import com.activity_sync.renderers.CommentsRenderer;
import com.activity_sync.renderers.base.DividerItemDecoration;
import com.activity_sync.renderers.base.RVRendererAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.subjects.PublishSubject;

public class CommentsScreen extends Screen implements ICommentsView
{
    public static final String EVENT_ID = "event_id";

    @Inject
    IApiService apiService;

    @Bind(R.id.comments_refresh)
    SwipeRefreshLayout commentsRefreshLayout;

    @Bind(R.id.comments_list)
    RecyclerView commentsList;

    @Bind(R.id.comment_edit_text)
    EditText commentEditText;

    @Bind(R.id.send_comment_btn)
    Button sendCommentBtn;

    private PublishSubject refreshComments = PublishSubject.create();
    private RVRendererAdapter<Comment> adapter;
    private List<Comment> comments = new ArrayList<>();

    public CommentsScreen()
    {
        super(R.layout.comments_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        int eventId = getIntent().getIntExtra(CommentsScreen.EVENT_ID, 0);
        return new CommentsPresenter(this, AndroidSchedulers.mainThread(), eventId, apiService);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);

        commentsRefreshLayout.setOnRefreshListener(() -> refreshComments.onNext(this));
        adapter = new RVRendererAdapter<>(this, new CommentsRenderer.Builder(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        commentsList.setLayoutManager(linearLayoutManager);
        commentsList.addItemDecoration(new DividerItemDecoration(this));
        commentsList.setAdapter(adapter);

        setTitle(getString(R.string.title_comments));
    }

    @Override
    public Observable refreshComments()
    {
        return refreshComments;
    }

    @Override
    public void addCommentsList(Collection<Comment> comments)
    {
        adapter.clear();
        this.comments.addAll(comments);
        adapter.addAll(comments);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addSingleComment(Comment comment)
    {
        this.comments.add(comment);
        adapter.add(comment);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshingVisible(boolean isRefreshing)
    {
        commentsRefreshLayout.post(() -> commentsRefreshLayout.setRefreshing(isRefreshing));
    }

    @Override
    public Observable sendCommentClick()
    {
        return ViewObservable.clicks(sendCommentBtn);
    }

    @Override
    public String comment()
    {
        return commentEditText.getText().toString();
    }

    @Override
    public void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void scrollToBottom()
    {
        commentsList.scrollToPosition(comments.size() - 1);
    }

    @Override
    public void clearComment()
    {
       commentEditText.setText(StringUtils.EMPTY);
    }

    @Override
    public void showEmptyCommentError()
    {
        Toast.makeText(this, R.string.err_comment_empty, Toast.LENGTH_LONG).show();
    }
}
