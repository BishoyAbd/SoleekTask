package com.project.bishoy.soleektask.data;

import com.project.bishoy.soleektask.data.model.ServerResponse;

import io.reactivex.Single;

/**
 * Created by bisho on 1/23/2018.
 */

public interface DataSource {


    Single<ServerResponse> getTodos();

    Single<ServerResponse> getPlans();

    Single<ServerResponse> getTips();
}
