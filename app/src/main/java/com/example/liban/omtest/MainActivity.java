package com.example.liban.omtest;

import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;


import com.example.liban.omtest.mvp.Presenter;
import com.example.liban.omtest.mvp.View;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.SSLContext;


public class MainActivity extends AppCompatActivity implements View {

    private Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private ConstraintLayout mConstraintLayout;
    private ProgressBar mProgressBar;
    private Parcelable mListState;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFloatingActionButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable("ListState");
        }
        mFloatingActionButton = findViewById(R.id.id_floating_main);
        mConstraintLayout = findViewById(R.id.constraint_id);
        mProgressBar = findViewById(R.id.id_progress_bar_main);
        mSwipeRefreshLayout = findViewById(R.id.id_swipe_main);
        mRecyclerView = findViewById(R.id.recycler_id);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        init();
        initSSL();
        mPresenter = new Presenter(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ListState", mRecyclerView.getLayoutManager().onSaveInstanceState());

    }


    private  void initSSL(){
        try {
            SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            ProviderInstaller.installIfNeeded(this.getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {

        mRecyclerAdapter = new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.requestData());
        mFloatingActionButton.setOnClickListener(v -> mRecyclerView.scrollToPosition(0));
    }

    @Override
    public void onDataPosts(List<ReadyPost> posts) {
        mProgressBar.setVisibility(android.view.View.GONE);
        mRecyclerAdapter.setPosts(posts);
        mRecyclerAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onError(String msg) {
        Snackbar.make(mConstraintLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", v -> mPresenter.requestData()).show();
        Log.e("Error", "this " + msg);

    }

}

