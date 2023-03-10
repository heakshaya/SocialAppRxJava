package com.example.socialappjava.data.api;

import com.example.socialappjava.data.model.post.APIResponse;
import com.example.socialappjava.data.model.user.OwnerAPIResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIService {
    @Headers("app-id: 625696752fbcea7f2c39fe88")
    @GET("v1/post")
    Observable<APIResponse> getPosts(@Query("page") int page, @Query("limit") int limit);

}
