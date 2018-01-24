package com.project.bishoy.soleektask.data.apiservices;

import com.project.bishoy.soleektask.Util;
import com.project.bishoy.soleektask.data.model.ServerResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by bisho on 1/23/2018.
 */

/*
        Retrofit service interface
 */

public interface WeddingService {


    @POST(Util.TODO_ENDPOINT)
    Single<ServerResponse> getTodos();

    @GET(Util.TIPS_ENDPOINT)
    Single<ServerResponse> getTips();

    Single<ServerResponse> getPlans();
}
