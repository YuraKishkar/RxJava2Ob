package com.example.liban.omtest.mvp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.liban.omtest.AlmostPost;
import com.example.liban.omtest.ApiImages;
import com.example.liban.omtest.ApiPosts;
import com.example.liban.omtest.ReadyPost;
import com.example.liban.omtest.dto.Images;
import com.example.liban.omtest.dto.ListPhoto;
import com.example.liban.omtest.dto.Posts;
import com.example.liban.omtest.dto.Urls;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {


    public interface onData {
        void dataPosts(List<ReadyPost> readyPosts);
        void dataError(String msg);

    }

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static final String BASE_URL_PHOTO = "https://api.unsplash.com/";
    private ApiPosts apiPosts;
    private ApiImages apiImages;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Retrofit mRetrofitPhoto;
    private Retrofit mRetrofitPosts;

    public Model() {
        if (mRetrofitPhoto == null || mRetrofitPosts == null) {
            mRetrofitPosts = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiPosts = mRetrofitPosts.create(ApiPosts.class);

            mRetrofitPhoto = new Retrofit.Builder()
                    .baseUrl(BASE_URL_PHOTO)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiImages = mRetrofitPhoto.create(ApiImages.class);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    public void getPosts(final onData onData) {

        Observable<Posts> textObservable = apiPosts.getPosts().toObservable()
                .flatMap((Function<List<Posts>, ObservableSource<Posts>>) posts -> Observable.fromIterable(posts));
        Observable<Images> imagesObservable = apiImages.getPhotos().toObservable()
                .flatMap((Function<List<Images>, ObservableSource<Images>>) images -> Observable.fromIterable(images));


        mCompositeDisposable.add(Observable.zip(textObservable, imagesObservable, new AlmostPost())
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<ReadyPost>>() {
                    @Override
                    public void onSuccess(List<ReadyPost> readyPosts) {
                        onData.dataPosts(readyPosts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onData.dataError(e.getMessage());
                    }
                }));


    }

    public void destroy() {
        mCompositeDisposable.dispose();
    }

}
