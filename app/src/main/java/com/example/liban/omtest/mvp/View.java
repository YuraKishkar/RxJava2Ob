package com.example.liban.omtest.mvp;

import com.example.liban.omtest.ReadyPost;

import java.util.List;

public interface View {

    void onDataPosts(List<ReadyPost> posts);
    void onError(String msg);
}
