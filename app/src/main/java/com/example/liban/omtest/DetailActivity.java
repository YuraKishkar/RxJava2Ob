package com.example.liban.omtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;




public class DetailActivity extends AppCompatActivity {

    public static final String URL = "URL_PHOTO";
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private String mStringUrl = "";
    private String mStringId = "";
    private String mStringTitle = "";
    private String mStringBody = "";
    private TextView mTextViewId;
    private TextView mTextViewTitle;
    private TextView mTextViewBody;
    private LinearLayout mLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mImageView = findViewById(R.id.id_image_view);
        mProgressBar = findViewById(R.id.id_progress_bar_detail);
        mLinearLayout = findViewById(R.id.id_linear_layout);

        mTextViewId = findViewById(R.id.id_detail_count);
        mTextViewTitle = findViewById(R.id.id_detail_title);
        mTextViewBody = findViewById(R.id.id_detail_body);

        Intent intent = getIntent();
        mStringId = intent.getStringExtra("id");
        mStringTitle = intent.getStringExtra("title");
        mStringBody = intent.getStringExtra("body");
        mStringUrl = intent.getStringExtra(URL);
        loadPhoto();


    }

    private void loadPhoto() {

        Glide.with(this)
                .load(mStringUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("PHOTO", "this: " + mStringUrl);
                        Snackbar.make(mLinearLayout, "Failed to upload photo", Snackbar.LENGTH_INDEFINITE)
                                .setAction("RETRY LOAD", v -> tryPhoto()).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        mTextViewId.setText(mStringId);
                        mTextViewTitle.setText(mStringTitle);
                        mTextViewBody.setText(mStringBody);
                        Log.e("PHOTO", "this: " + mStringUrl);
                        return false;
                    }
                })
                .into(mImageView);

    }

    private void tryPhoto() {
        loadPhoto();
    }

}
