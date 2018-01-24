package com.project.bishoy.soleektask;

/**
 * Created by el on 10/8/2017.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();

    void showError(String errorNoData);

    void hideError();


    void showError();
}

