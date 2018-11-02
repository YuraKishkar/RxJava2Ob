package com.example.liban.omtest;

import com.example.liban.omtest.dto.Images;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiImages {

    @GET("photos/random?client_id=116ad03f72e7947aefbf79a35fafe7800cc74bb5395b6a594261ee0082c98952&&count=30&orientation=landscape")
    Single<List<Images>> getPhotos();
}
