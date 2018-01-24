package com.project.bishoy.soleektask.data;

import com.project.bishoy.soleektask.data.model.ServerResponse;

import io.reactivex.Single;

/**
 * Created by bisho on 1/23/2018.
 */

public class LocalDataSource implements DataSource {

    private static DataSource instance=null;

    private LocalDataSource() {
    }

    public static synchronized DataSource getInstance() {
        if (instance == null)
            instance = new LocalDataSource();
        return instance;
    }

    @Override
    public Single<ServerResponse> getTodos() {
        return null;
    }

    @Override
    public Single<ServerResponse> getPlans() {
        return null;
    }

    @Override
    public Single<ServerResponse> getTips() {
        return null;
    }
}
