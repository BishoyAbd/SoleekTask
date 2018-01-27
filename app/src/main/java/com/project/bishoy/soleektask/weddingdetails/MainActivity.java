package com.project.bishoy.soleektask.weddingdetails;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.bishoy.soleektask.Injector;
import com.project.bishoy.soleektask.R;
import com.project.bishoy.soleektask.addplan.AddPlanActivity;
import com.project.bishoy.soleektask.data.model.Data;
import com.project.bishoy.soleektask.data.model.Plan;
import com.project.bishoy.soleektask.util.PermissionUtil;
import com.project.bishoy.soleektask.util.Util;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements WeedingContract.View {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 22;
    private static final int REQUEST_CODE_COVER_PHOTO = 1;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;


    @BindView(R.id.recyclerView_tips)
    RecyclerView tipsRecyclerView;
    @BindView(R.id.recyclerView_todos)
    RecyclerView todosRecyclerView;
    @BindView(R.id.recyclerView_plans)
    RecyclerView plansRecyclerView;

    @BindView(R.id.dateTimeContainerLayout)
    LinearLayout dateTimeContainer;

    @BindView(R.id.tv_changeCover)
    TextView tvChangeCover;

    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_sec)
    TextView tvSec;

    @BindView(R.id.loadingProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.tv_error_or_empty)
    TextView erroOrNoPlansTextView;
    @BindView(R.id.imageView_cover)
    ImageView imageViewCover;
    private long seconds = 0;
    private long minutes = 0;
    private long hours = 0;
    private long days = 0;

    private TipsRecyclerAdapter tipsRecyclerAdapter;
    private TodoRecyclerAdapter todoRecyclerAdapter;
    private PlanRecyclerAdapter planRecyclerAdapter;
    private List<Data> tipsList = new ArrayList<>();
    private List<Data> todoList = new ArrayList<>();
    private List<Plan> planList = new ArrayList<>();
    private WeedingContract.Presenter weddingPresenter;

    private long millis;
    private CountDownTimer cdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolBar();
        setupRecyclerView();

        weddingPresenter = new WeddingPresenter(this, Injector.provideApartmentRepository(this));


    }


    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
        }
    }


    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tipsRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        todosRecyclerView.setLayoutManager(layoutManager2);

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(this);
        plansRecyclerView.setLayoutManager(layoutManager3);
    }


    @OnClick(R.id.dateTimeContainerLayout)
    void onClickTimer(View view) {
        openDatePicker();
    }

    private void openDatePicker() {

        final Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        //open the time picker

                        openTimePicker(year, monthOfYear, dayOfMonth);

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)


        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void openTimePicker(final int year, final int month, final int day) {
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hour, int minute, int second) {
                startCountDownTimer(year, month, day, hour, minute, second);
            }

        }, false);
        timePickerDialog.show(getFragmentManager(), "timePickerDialog");

        timePickerDialog.onCancel(new DialogInterface() {
            @Override
            public void cancel() {
                //show error message incase of canceling
            }

            @Override
            public void dismiss() {
                //show error message incase of canceling
            }
        });
    }

    private void startCountDownTimer(int year, int month, int day, int hourOfDay, int minute, int second) {


        Date current = new Date(); //now
        Date then = new Date(year, month, day, hourOfDay, minute, second); //to this date
        Calendar now = Calendar.getInstance();

        //get different between the two dates in millis
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hourOfDay, minute, second);
        millis = Util.getDiff(now, calendar);
        Timber.d("millis dif --> " + millis);


        Interval interval = new Interval(current.getTime(), then.getTime());
//
//        if (!interval.isAfterNow()) {
//            //output " time chosen is not valid"
//            return;
//
//        }

        // convert interval to period to get standard d,h,m,s within this period
        Period period = interval.toPeriod();
        seconds = period.getSeconds();
        hours = period.getHours();
        minutes = period.getMinutes();
        days = period.getDays();


        //format and upload dates to to server should happen here

        tvDay.setText(days + "");
        tvHour.setText(hours + "");
        tvMin.setText(minutes + "");
        tvSec.setText(seconds + "");


        cdt = new CountDownTimer(millis, 1000) {

            @Override

            public void onTick(long millis) {
                seconds--;

                if (seconds == 0) {
                    if (minutes > 0) {  //there are some minutes out there
                        minutes--;
                        seconds = 60;
                    } else { //min=0 so decrease h
                        if (hours > 0) {
                            hours--;
                            minutes = 59;
                            seconds = 60;
                        } else {  //h=0 so decrease date
                            if (days > 0) {
                                days--;
                                hours = 59;
                                minutes = 59;
                                seconds = 60;

                            } else {
                                days = 0;
                                hours = 0;
                                minutes = 0;
                                seconds = 0;
                                //stop counter
//                                stopTimer();

                            }

                        }

                    }
                }

                tvDay.setText(days + "");
                tvHour.setText(hours + "");
                tvMin.setText(minutes + "");
                tvSec.setText(seconds + "");

            }


            @Override
            public void onFinish() {
                Timber.d("countDown Finished");
            }
        }.start();

    }

    private void stopTimer() {
        cdt.cancel();

    }

    @OnClick(R.id.fab_addPlane)
    void onClickAdd(View view) {
        startActivity(new Intent(this, AddPlanActivity.class));


    }

    @OnClick(R.id.tv_changeCover)
    void onClickAddCover(View v) {
        openGallery();
    }

    @Override
    public void setPresenter(WeedingContract.Presenter presenter) {
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.animate();
        container.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        container.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError(String errorNoData) {
        Toast.makeText(this, errorNoData, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideError() {
//use it if you intend to use dialogs
    }

    @Override
    public void showError() {
        Toast.makeText(this, "error loading data!", Toast.LENGTH_SHORT).show();

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
    public void showPlans(ArrayList<Plan> plans) {
        this.planList = plans;
        planRecyclerAdapter = new PlanRecyclerAdapter(this.planList, this);
        plansRecyclerView.setAdapter(planRecyclerAdapter);

    }


    @Override
    public void openGallery() {
        //first check for permission
        PermissionUtil.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onNeedPermission() {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        );
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //  show a dialog explaining permission and then request permission
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        );


                    }

                    @Override
                    public void onPermissionDisabled() {
                        Toast.makeText(MainActivity.this, "Permission Disabled.", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionGranted() {
                        showGallery();
                    }
                });


    }

    @Override
    public void showNoResult() {
        //print no plans added before
        erroOrNoPlansTextView.setVisibility(View.VISIBLE);

    }


    private void showGallery() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(false)
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .theme(R.style.Matisse_Zhihu)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_COVER_PHOTO);

    }


    @Override
    protected void onResume() {
        super.onResume();
        weddingPresenter.subscribe();
        weddingPresenter.getPlans();
    }

    @Override
    protected void onPause() {
        weddingPresenter.unsubscribe();
        super.onPause();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COVER_PHOTO && resultCode == RESULT_OK) {
            //the data is returned as list
            Picasso.with(this).load(Matisse.obtainResult(data).get(0)).into(imageViewCover);
        }
    }
}
