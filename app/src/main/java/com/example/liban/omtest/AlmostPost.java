package com.example.liban.omtest;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.liban.omtest.dto.Images;
import com.example.liban.omtest.dto.Posts;

import io.reactivex.functions.BiFunction;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AlmostPost implements BiFunction<Posts, Images, ReadyPost> {


    @Override
    public ReadyPost apply(Posts posts, Images images) throws Exception {
        ReadyPost readyPost = new ReadyPost();
        readyPost.setId(posts.getId());
        readyPost.setTitle(posts.getTitle());
        readyPost.setBody(posts.getBody());
        readyPost.setSmall(images.getUrls().getSmall());
        readyPost.setRegular(images.getUrls().getRegular());
        readyPost.setFull(images.getUrls().getFull());
        return readyPost;
    }
}
