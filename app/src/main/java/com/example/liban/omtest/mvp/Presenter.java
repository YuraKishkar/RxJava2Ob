package com.example.liban.omtest.mvp;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.liban.omtest.ReadyPost;

import java.util.List;

public class Presenter implements Model.onData{
    private View mView;
    private Model mModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Presenter(View mView) {
        this.mView = mView;
        mModel = new Model();
        mModel.getPosts(this);
    }


    @Override
    public void dataPosts(List<ReadyPost> posts) {
        mView.onDataPosts(posts);
    }

    @Override
    public void dataError(String msg) {
        mView.onError(msg);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestData(){
        mModel.getPosts(this);
    }

    public void destroy(){
        mModel.destroy();
    }
}
