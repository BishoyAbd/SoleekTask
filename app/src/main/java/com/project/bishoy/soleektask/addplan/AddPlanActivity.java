package com.project.bishoy.soleektask.addplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.project.bishoy.soleektask.Injector;
import com.project.bishoy.soleektask.R;
import com.project.bishoy.soleektask.customviews.SmoothCheckBox;
import com.project.bishoy.soleektask.data.model.Plan;
import com.project.bishoy.soleektask.weddingdetails.MainActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by bisho on 1/24/2018.
 */

public class AddPlanActivity extends AppCompatActivity {


    @BindView(R.id.seekBar_attendees)
    CrystalSeekbar seekBar;
    @BindView(R.id.seekbar_tv_value)  //textview of the bar
            TextView seekBarTextView;

    @BindView(R.id.et_location)
    AutoCompleteTextView locationEt;

    @BindView(R.id.et_money)
    EditText budgetEt;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.checkbox_money)
    SmoothCheckBox checkBoxMoney;
    @BindView(R.id.checkBox_location)
    SmoothCheckBox checkBoxLocation;
    private Disposable disposable;


    private int budget = 0, attendeesNum = 100;
    private String location = "Any";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan_activity);
        ButterKnife.bind(this);
        setupToolBar();
        setupSeekBar();
        setupAutoCompleteTextView();


    }

    private void setupToolBar() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            //disable toolbar default icon
            getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    private void setupSeekBar() {

        seekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                attendeesNum = adjustProgress(value.intValue());
                seekBarTextView.setText(attendeesNum + "");
            }
        });
    }

    /**
     * @param progress
     * @return int in range [100,150, ...]
     */
    private int adjustProgress(int progress) {
        int value = progress / 50;
        value = value * 50;
        Timber.d(value + " --> progress");
        return value;
    }

    /*
setup adapter for the AutoCompleteTextView of location
     */
    private void setupAutoCompleteTextView() {
        List<String> cities = getCitiesFromServer();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, cities);
        locationEt.setThreshold(1);
        locationEt.setAdapter(adapter);
    }


    /**
     * @return List<String>  from server
     */
    private List<String> getCitiesFromServer() {


        return Arrays.asList("Cairo", "Giza", "Alex", "Minia", "Luxor", "Aswan", "Ben Suef");
    }

    @OnClick(R.id.btn_next)
    void onClickNet(View view) {


        if (!dataIsValid()) {
            showError();
            return;
        }

        Plan plan = new Plan(budget, location, attendeesNum);

        disposable = Injector.provideApartmentRepository(this).addPlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(AddPlanActivity.this, MainActivity.class);
                        startActivity(intent);
                        AddPlanActivity.this.finish();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("error adding plan -->" + e.getLocalizedMessage());
                    }
                });


    }

    /**
     * uses the {@link Validator} to validate data
     *
     * @return boolean -> if date is valid
     */
    private boolean dataIsValid() {

        boolean valid = true;

        Validator validator = new Validator();
        location = locationEt.getText().toString();
        //append 0 before text to prevent NumberFormatException pointer exception in case of empty
        budget = Integer.parseInt(0 + budgetEt.getText().toString());

        if (!checkBoxLocation.isChecked()) {
            if (!validator.validateLocation(location)) {
                locationEt.setError(getResources().getString(R.string.error_invalid_location));
                valid = false;
            }

        }
        if (!checkBoxMoney.isChecked()) {
            if (!validator.validateBudget(budget)) {
                valid = false;
                budgetEt.setError(getResources().getString(R.string.error_invalid_budget));

            }
        }

        return valid;
    }

    private void showError() {

    }


    @OnClick(R.id.up_icon)
    void onClickBack() {
        onBackPressed();
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (disposable != null)
            disposable.dispose();
    }
}
