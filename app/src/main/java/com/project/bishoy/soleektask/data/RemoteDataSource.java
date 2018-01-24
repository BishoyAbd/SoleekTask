package com.project.bishoy.soleektask.data;

import com.project.bishoy.soleektask.data.apiservices.ServiceGenerator;
import com.project.bishoy.soleektask.data.apiservices.WeddingService;
import com.project.bishoy.soleektask.data.model.ServerResponse;

import io.reactivex.Single;

/**
 * Created by bisho on 1/23/2018.
 */


  /*    Notes on this class :

        1-it behaves as a layer that deals with remote server
        2-it encapsulates behaviour of data manipulation.you can use whatever to retrieve data from server (eg Volley)
        just implement @DataSource and you are good to go
        */
public class RemoteDataSource implements DataSource {

    private WeddingService weddingService=null;


    private static DataSource instance;


    public static synchronized DataSource getInstance() {
        if (instance == null)
            instance = new RemoteDataSource();
        return instance;
    }

    private RemoteDataSource() {
        weddingService = ServiceGenerator.createService(WeddingService.class);

    }

    @Override
    public Single<ServerResponse> getTodos() {
        return weddingService.getTodos();
    }

    @Override
    public Single<ServerResponse> getPlans() {
        return weddingService.getPlans();
    }

    @Override
    public Single<ServerResponse> getTips() {
        return weddingService.getTips();
    }
}
