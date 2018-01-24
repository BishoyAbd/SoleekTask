package com.project.bishoy.soleektask.weddingdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.project.bishoy.soleektask.Injector;
import com.project.bishoy.soleektask.R;
import com.project.bishoy.soleektask.customviews.ProgressBarWithText;
import com.project.bishoy.soleektask.data.model.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WeedingContract.View {


    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.progressBarWithText)
    ProgressBarWithText progressBar;

    @BindView(R.id.recyclerView_tips)
    RecyclerView tipsRecyclerView;
    @BindView(R.id.recyclerView_todos)
    RecyclerView todosRecyclerView;

    private TipsRecyclerAdapter tipsRecyclerAdapter;
    private TodoRecyclerAdapter todoRecyclerAdapter;
    private List<Data> tipsList = new ArrayList<>();
    private WeedingContract.Presenter weddingPresenter;
    private List<Data> todoList = new ArrayList<>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolBar();
        initProgressBar();
        setupRecyclerView();
        weddingPresenter = new WeddingPresenter(this, Injector.provideApartmentRepository(this));
        weddingPresenter.subscribe();
    }


    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
        }
    }


    private void initProgressBar() {
        progressBar.setProgressColor(getResources().getColor(R.color.colorPrimary));
        progressBar.setBackgroundColor(getResources().getColor(R.color.grey));
        progressBar.setProgress(50);
        progressBar.setText("50%");
        progressBar.setTextColor(getResources().getColor(R.color.colorPrimary));
        progressBar.setStrokeWidth(6f);
    }


    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tipsRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        todosRecyclerView.setLayoutManager(layoutManager2);
    }

    @Override
    public void setPresenter(WeedingContract.Presenter presenter) {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorNoData) {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void showError() {
        //show progress dialog
    }

    @Override
    public void displayTips(List<Data> tipsList) {
        this.tipsList = tipsList;
        tipsRecyclerAdapter = new TipsRecyclerAdapter(this.tipsList, this);
        tipsRecyclerView.setAdapter(tipsRecyclerAdapter);

    }

    @Override
    public void displayTodo(List<Data> todoList) {
        this.todoList = todoList;
        todoRecyclerAdapter = new TodoRecyclerAdapter(this.todoList, this);
        todosRecyclerView.setAdapter(todoRecyclerAdapter);
    }

    @Override
    public void displayPlans() {

    }

    @Override
    public void openGallery() {

    }

    @Override
    public void showTimer() {

    }

    @Override
    public void openDateTimePicker() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        weddingPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        weddingPresenter.unsubscribe();
        super.onPause();

    }
}
