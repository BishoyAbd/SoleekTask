package com.project.bishoy.soleektask.weddingdetails;

import com.project.bishoy.soleektask.BasePresenter;
import com.project.bishoy.soleektask.BaseView;
import com.project.bishoy.soleektask.data.model.Data;
import com.project.bishoy.soleektask.data.model.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bisho on 1/23/2018.
 */



/*
view and presenter of the wedding activity
 */
public interface WeedingContract {

    interface View extends BaseView<Presenter> {

        void displayTips(List<Data> tipsList);

        void displayTodo(List<Data> todoList);

        //void displayPlans(List<Plan> planList);


        void openGallery();

        void showNoResult();

        void showPlans(ArrayList<Plan> plans);
    }

    interface Presenter extends BasePresenter {

        void changeCoverPhoto(String id);

        void startTimer(String dat);


        void addPlan(Plan plan);

        void openAddPlanScreen();

        void getTips(); //add userId as a parameter for those methods

        void getTodos();

        void getPlans();
    }
}
