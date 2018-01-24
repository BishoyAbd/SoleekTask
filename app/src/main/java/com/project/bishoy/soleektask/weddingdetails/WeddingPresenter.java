package com.project.bishoy.soleektask.weddingdetails;

import com.project.bishoy.soleektask.Util;
import com.project.bishoy.soleektask.data.DataSource;
import com.project.bishoy.soleektask.data.WeddingRepository;
import com.project.bishoy.soleektask.data.model.ServerResponse;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by bisho on 1/23/2018.
 */

public class WeddingPresenter implements WeedingContract.Presenter {

    private DataSource mWeddingRepository;
    private CompositeDisposable compositeDisposable;
    WeedingContract.View view;

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
    public void addPlan() {

    }

    @Override
    public void openAddPlanScreen() {

    }

    @Override
    public void getTips() {
        view.showLoading();
        view.hideError();
        Disposable disposable = mWeddingRepository.getTips().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ServerResponse>() {

                    @Override
                    public void onSuccess(ServerResponse serverResponse) {
                        view.hideLoading();
                        if (serverResponse.getCode() != Util.ERROR_404)
                            view.displayTips(serverResponse.getData());
                        else
                            view.showError(Util.ERROR_NO_DATA);

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(Util.ERROR_NO_DATA);
                        view.hideLoading();
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void getTodos() {

        view.showLoading();
        view.hideError();
        Disposable disposable = mWeddingRepository.getTodos().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ServerResponse>() {

                    @Override
                    public void onSuccess(ServerResponse serverResponse) {
                        view.hideLoading();
                        if (serverResponse.getCode() != Util.ERROR_404)
                            view.displayTips(serverResponse.getData());
                        else
                            view.showError(Util.ERROR_NO_DATA);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //network error or request timeout reached
                        view.showError();
                        view.hideLoading();
                    }
                });

        compositeDisposable.add(disposable);

    }

    @Override
    public void getPlans() {

        //TODO store and get data locally
        view.showLoading();
        view.hideError();
        Disposable disposable = mWeddingRepository.getPlans().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ServerResponse>() {

                    @Override
                    public void onSuccess(ServerResponse serverResponse) {
                        view.hideLoading();
                        if (serverResponse.getCode() != Util.ERROR_404)
                            view.displayTips(serverResponse.getData());
                        else
                            view.showError();

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError();
                        view.hideLoading();
                    }
                });

        compositeDisposable.add(disposable);
    }
}
