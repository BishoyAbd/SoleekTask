package com.project.bishoy.soleektask.weddingdetails;

import android.database.Cursor;

import com.project.bishoy.soleektask.data.DataSource;
import com.project.bishoy.soleektask.data.WeddingRepository;
import com.project.bishoy.soleektask.data.local.WeddingSqlHelper;
import com.project.bishoy.soleektask.data.model.Plan;
import com.project.bishoy.soleektask.data.model.ServerResponse;
import com.project.bishoy.soleektask.data.model.TipsAndTodos;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by bisho on 1/23/2018.
 */

public class WeddingPresenter implements WeedingContract.Presenter {

    private DataSource mWeddingRepository;
    private CompositeDisposable compositeDisposable;
    private WeedingContract.View view;

    public WeddingPresenter(WeedingContract.View view, WeddingRepository weddingRepository) {
        mWeddingRepository = weddingRepository;
        compositeDisposable = new CompositeDisposable();
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

        if (view == null)
            return;

        view.showLoading();
        Single<ServerResponse> todoObserver = mWeddingRepository.getTodos().subscribeOn(Schedulers.newThread());
        Single<ServerResponse> tipsObserver = mWeddingRepository.getTips().subscribeOn(Schedulers.newThread());

        Disposable disposable = Single.zip(todoObserver, tipsObserver, new BiFunction<ServerResponse, ServerResponse, TipsAndTodos>() {

            @Override
            public TipsAndTodos apply(ServerResponse todosResponse, ServerResponse tipsResponse) throws Exception {
                return new TipsAndTodos(todosResponse, tipsResponse);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TipsAndTodos>() {

                    @Override
                    public void onSuccess(TipsAndTodos tipsAndTodos) {
                        view.displayTips(tipsAndTodos.getTipsResponce().getData());
                        view.displayTodo(tipsAndTodos.getTodoResponse().getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void changeCoverPhoto(String id) {
        // TODO send cover photo to server
    }

    @Override
    public void startTimer(String dat) {

    }

    @Override
    public void addPlan(Plan plan) {
        mWeddingRepository.addPlan(plan);
    }

    @Override
    public void openAddPlanScreen() {

    }

    //implement these methods for loading tips and todos and plan simultainiuosly

    @Override
    public void getTips() {
    }

    @Override
    public void getTodos() {

    }

    @Override
    public void getPlans() {
        final ArrayList<Plan> plans = new ArrayList<>();
        view.showLoading();

        mWeddingRepository.getPlans().
                observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SqlBrite.Query>() {

                    @Override
                    public void onNext(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        Timber.d("number of plans ---> " + cursor.getCount());
                        while (cursor.moveToNext()) {

                            Plan plan = new Plan();
                            plan.setCost(cursor.getInt(cursor.getColumnIndex(WeddingSqlHelper.COLUMN_COST)));
                            plan.setAttendees(cursor.getInt(cursor.getColumnIndex(WeddingSqlHelper.COLUMN_ATTENDEES)));
                            plan.setLocation(cursor.getString(cursor.getColumnIndex(WeddingSqlHelper.COLUMN_LOCATION)));
                            plans.add
                                    (plan);
                        }

                        view.hideLoading();
                        if (plans.isEmpty())
                            view.showNoResult();
                        else
                            view.showPlans(plans);
                    }


                    @Override
                    public void onError(Throwable e) {
                        Timber.d("error getting plans ---> " + e.getLocalizedMessage());
                        view.showError();
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("on complete getting plans---> " );

                    }
                });

    }

}
