package com.project.bishoy.soleektask.weddingdetails;

import com.project.bishoy.soleektask.BasePresenter;
import com.project.bishoy.soleektask.BaseView;
import com.project.bishoy.soleektask.data.model.Data;

import java.util.List;

/**
 * Created by bisho on 1/23/2018.
 */

public interface WeedingContract {

    interface View extends BaseView<Presenter> {

        void displayTips(List<Data> tipsList);

        void displayTodo(List<Data> todoList);

        void displayPlans();

        void openGallery();

        void showTimer();

        void openDateTimePicker();
    }

    interface Presenter extends BasePresenter {

        void changeCoverPhoto(String id);

        void startTimer(String dat);

        void addPlan();

        void openAddPlanScreen();

        void getTips();

        void getTodos();

        void getPlans();
    }
}
