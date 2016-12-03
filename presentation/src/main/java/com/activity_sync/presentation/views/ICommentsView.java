package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Comment;

import java.util.Collection;

import rx.Observable;

public interface ICommentsView
{
    Observable refreshComments();
    void addCommentsList(Collection<Comment> comments);
    void addSingleComment(Comment comment);
    void refreshingVisible(boolean isRefreshing);

    Observable sendCommentClick();
    String comment();

    void sendCommentMessage();
}
