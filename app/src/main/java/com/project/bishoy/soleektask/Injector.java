package com.project.bishoy.soleektask;

/**
 * Created by bisho on 1/24/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.project.bishoy.soleektask.data.DataSource;
import com.project.bishoy.soleektask.data.WeddingRepository;
import com.project.bishoy.soleektask.data.local.LocalDataSource;
import com.project.bishoy.soleektask.data.remote.apiservices.RemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link  DataSource} at compile time.
 */
public class Injector {

    public static WeddingRepository provideApartmentRepository(@NonNull Context context) {
        checkNotNull(context);
        return WeddingRepository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance(context));
    }


}
