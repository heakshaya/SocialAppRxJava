package com.example.socialappjava.presentation.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.socialappjava.DisposableManager;
import com.example.socialappjava.data.model.post.APIResponse;
import com.example.socialappjava.domain.ApiRepository;
import com.example.socialappjava.data.util.Resource;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ViewModel extends AndroidViewModel {

    @NonNull
    private final MutableLiveData<APIResponse> posts = new MutableLiveData<>();
    private Observable<APIResponse> apiResponseObservable;
    private final MutableLiveData<Resource.Status> statusMutableLiveData = new MutableLiveData<>();
    Application app;

    private Observer<APIResponse> postObserver = new Observer<APIResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            DisposableManager.add(d);
            Log.d("Users", "postObserver:onSubscribe");
        }

        @Override
        public void onNext(APIResponse apiResponse) {
            Log.d("Users", "postObserver:onNext");
            if (!apiResponse.getData().isEmpty()) {
                posts.postValue(apiResponse);
            }

        }

        @Override
        public void onError(Throwable e) {
            Log.d("Error", "Error in Observer: " + e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.d("Users", "postObserver:onComplete");
        }
    };

    public ViewModel(Application app) {
        super(app);
        this.app = app;
    }

    public void stopApiCall() {
            DisposableManager.clear();
        Log.d("Users", "postObserver:stopApiCall");
    }

    public void getPostsList(int page, int limit) {
        if (isNetworkAvailable(app)) {

           apiResponseObservable = ApiRepository.getInstance().getPosts(page, limit);

            apiResponseObservable.doOnSubscribe(disposable -> {
                        Log.d("Users", "LOADING");
                        statusMutableLiveData.postValue(Resource.Status.LOADING);
                    }).doFinally(() -> {
                        statusMutableLiveData.postValue(Resource.Status.LOADING_COMPLETED);
                                Log.d("Users", "LOADING_COMPLETED");
                    }
                    ).onErrorReturn(new Function<Throwable, APIResponse>() {
                        @Override
                        public APIResponse apply(Throwable throwable) throws Exception {
                            return null;
                        }
                    }).subscribeOn(Schedulers.io())
                    //.debounce(5, TimeUnit.SECONDS)
                   // .distinct()
                   // .takeLast(5,TimeUnit.SECONDS)
                   // .sample(5,TimeUnit.SECONDS)
                   // .throttleFirst(5,TimeUnit.SECONDS)
                   // .takeLast(5,TimeUnit.SECONDS)
                    // .delay(5, TimeUnit.SECONDS)
                    .delaySubscription(5,TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(postObserver);
        }
    }

    public MutableLiveData<APIResponse> getPostsMutableLiveData() {
        return posts;
    }

    private boolean isNetworkAvailable(@NonNull Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

            if (capabilities != null) {
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            }
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;

    }

    @Override
    protected void onCleared() {
        stopApiCall();
        Log.d("Users", "onCleared");
    }
}
