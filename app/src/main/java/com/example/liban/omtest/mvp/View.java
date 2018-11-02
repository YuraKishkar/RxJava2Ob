package com.example.liban.omtest.mvp;

import com.example.liban.omtest.ReadyPost;
import com.example.liban.omtest.dto.Images;
import com.example.liban.omtest.dto.ListPhoto;
import com.example.liban.omtest.dto.Posts;

import java.util.List;

public interface View {

    void onDataPosts(List<ReadyPost> posts);
    void onError(String msg);
}
