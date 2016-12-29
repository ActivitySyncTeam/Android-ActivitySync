package com.activity_sync.tests;

import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.models.CommentsCollection;
import com.activity_sync.presentation.models.builders.CommentBuilder;
import com.activity_sync.presentation.models.builders.CommentsCollectionBuilder;
import com.activity_sync.presentation.presenters.CommentsPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.ICommentsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class CommentsPresenterTests
{
    @Mock
    IApiService apiService;

    @Mock
    ICommentsView view;

    @Mock
    CurrentUser currentUser;

    protected PublishSubject sendCommentEvent = PublishSubject.create();
    protected PublishSubject refreshCommentsEvent = PublishSubject.create();

    private int eventId = 1;
    private String testComment = "Test comment";
    private List<Comment> comments = new ArrayList<>();
    private CommentsCollection commentsCollection;

    @Before
    public void setup()
    {
        Mockito.when(view.sendCommentClick()).thenReturn(sendCommentEvent);
        Mockito.when(view.refreshComments()).thenReturn(refreshCommentsEvent);
        Mockito.when(view.comment()).thenReturn(testComment);

        comments.add(new CommentBuilder().setComment(testComment).createComment());
        commentsCollection = new CommentsCollectionBuilder().setComments(comments).create();

        Mockito.when(apiService.getEventComments(eventId)).thenReturn(Observable.just(commentsCollection));
        Mockito.when(apiService.addComment(eventId, testComment)).thenReturn(Observable.just(null));
    }

    @Test
    public void commentsPresenter_clickSendButton_addComment()
    {
        CommentsPresenter presenter = createPresenter();
        presenter.start();

        sendCommentEvent.onNext(this);

        Mockito.verify(view).addSingleComment(any());
        Mockito.verify(view).scrollToBottom();
        Mockito.verify(view).hideKeyboard();
        Mockito.verify(view).clearComment();
    }

    @Test
    public void commentsPresenter_clickSendButton_emptyError()
    {
        Mockito.when(view.comment()).thenReturn(StringUtils.EMPTY);

        CommentsPresenter presenter = createPresenter();
        presenter.start();

        sendCommentEvent.onNext(this);

        Mockito.verify(view).showEmptyCommentError();
        Mockito.verify(view, never()).addSingleComment(any());
    }

    @Test
    public void commentsPresenter_refreshList_reloadComments()
    {
        CommentsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        refreshCommentsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view).refreshingVisible(false);
    }

    private CommentsPresenter createPresenter()
    {
        return new CommentsPresenter(view, Schedulers.immediate(), eventId, apiService, currentUser);
    }
}
