package com.project.bishoy.soleektask.data;

import com.project.bishoy.soleektask.data.model.ServerResponse;

import io.reactivex.Single;

/**
 * Created by bisho on 1/23/2018.
 */


/*
      most important class, it's tasks are :

      1-to encapsulate methods invocation inspired by Command pattern
      2-caching and data refreshing
      3-delegates getting data to LocalDataSource and RemoteDataSource
 */

public class WeddingRepository implements DataSource {

    private static WeddingRepository instance = null;
    private DataSource localDataSource;
    private DataSource remoteDataSource;


    /*
    singleton WeddingRepository to make sure only an instance in our application.
     */
    public static synchronized WeddingRepository getInstance(DataSource remoteDataSource, DataSource localDataSource) {
        if (instance == null)
            instance = new WeddingRepository(remoteDataSource, localDataSource);
        return instance;
    }


    private WeddingRepository(DataSource remoteDataSource, DataSource localDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<ServerResponse> getTodos() {

        //get cached data using the localDataSource. if not found, get data from remote server
        //if(!dataISUpToDate) return remoteDataSource.getTodos(); else

        return remoteDataSource.getTodos();
    }

    @Override
    public Single<ServerResponse> getPlans() {
        return remoteDataSource.getPlans();
    }

    @Override
    public Single<ServerResponse> getTips() {
        return remoteDataSource.getTips();
    }

}
