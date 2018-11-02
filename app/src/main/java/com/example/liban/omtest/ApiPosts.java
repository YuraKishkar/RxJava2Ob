package com.example.liban.omtest;

import com.example.liban.omtest.dto.Posts;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiPosts {

    @GET("posts")
    Single<List<Posts>> getPosts();
}
