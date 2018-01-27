package com.project.bishoy.soleektask.data;

import com.project.bishoy.soleektask.data.model.Plan;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by bisho on 1/23/2018.
 */

//
public interface DataSource<T, E> {


    Single<T> getTodos();

    Observable<E> getPlans();

    Single<T> getTips();

    Completable addPlan(Plan plane);
}
