package com.project.bishoy.soleektask.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.project.bishoy.soleektask.data.DataSource;
import com.project.bishoy.soleektask.data.model.Data;
import com.project.bishoy.soleektask.data.model.Plan;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by bisho on 1/23/2018.
 */

public class LocalDataSource implements DataSource<List<Data>, SqlBrite.Query> {

    private static DataSource instance = null;
    private WeddingSqlHelper weddingSqlHelper;
    private SQLiteDatabase sqLiteDatabase;
    private SqlBrite sqlBrite;
    private BriteDatabase db;


    private LocalDataSource(Context context) {
        weddingSqlHelper = new WeddingSqlHelper(context);
        sqlBrite = new SqlBrite.Builder().build();
        db = sqlBrite.wrapDatabaseHelper(weddingSqlHelper, Schedulers.io());
    }

    public static synchronized DataSource getInstance(Context context) {
        if (instance == null)
            instance = new LocalDataSource(context);
        return instance;
    }


    @Override
    public Single<List<Data>> getTodos() {
        return null;
    }

    @Override
    public Observable<SqlBrite.Query> getPlans() {
        return getAllPlans();
    }

    @Override
    public Single<List<Data>> getTips() {
        return null;
    }

    @Override
    public Completable addPlan(Plan plane) {
        long id = -1; //row id if success or -1 if failure

        ContentValues contentValues = new ContentValues();
        contentValues.put(WeddingSqlHelper.COLUMN_LOCATION, plane.getLocation());
        contentValues.put(WeddingSqlHelper.COLUMN_COST, plane.getCost());
        contentValues.put(WeddingSqlHelper.COLUMN_ATTENDEES, plane.getAttendees());
        contentValues.put(WeddingSqlHelper.COLUMN_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        try {

            id = db.insert(WeddingSqlHelper.TABLE_PLAN, contentValues); //if successfully added apartment then add images

        } catch (SQLException e) {

            Timber.d("error inserting plan -> " + e.getLocalizedMessage());
        }

        if (id < 0) return Completable.error(new Throwable("erro adding plan"));
        else {
            Timber.d("plan inserted ");
            return Completable.complete();
        }
    }

    public void close() {
        db.close();
    }


    public Observable<SqlBrite.Query> getAllPlans() {
        return db.createQuery(WeddingSqlHelper.TABLE_PLAN, WeddingSqlHelper.QUERY_GET_ALL_PLANS);
    }
}
