package com.project.bishoy.soleektask.addplan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.bishoy.soleektask.R;
import com.project.bishoy.soleektask.customviews.ProgressBarWithText;
import com.project.bishoy.soleektask.data.model.Plan;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bisho on 1/27/2018.
 */

public class PlanRecyclerAdapter extends RecyclerView.Adapter<PlanRecyclerAdapter.PlanViewHolder> {

    private List<Plan> planList = Collections.emptyList();
    private Context context;


    public PlanRecyclerAdapter(List<Plan> planList, Context context) {
        this.planList = planList;
        this.context = context;
    }


    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_layout, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        Plan plan = planList.get(position);

        holder.planTv.setText("Plan " + generateChar(position));
        holder.locationTv.setText(plan.getLocation());
        holder.costTv.setText("Planned cost : " + plan.getCost() + "");
        holder.attendeesTv.setText(plan.getAttendees() + "+ Family and Friends");
        //fake percentage
        holder.initProgressBar();

    }

    private String generateChar(int position) {
        return Character.toString((char) (position + 65));


    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public class PlanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_plane)
        TextView planTv;
        @BindView(R.id.tv_location)
        TextView locationTv;
        @BindView(R.id.tv_attendees)
        TextView attendeesTv;
        @BindView(R.id.tv_planedCost)
        TextView costTv;
        @BindView(R.id.progressBarWithText)
        ProgressBarWithText progressBar;

        public PlanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        private void initProgressBar() {
            //simulate fak progress
            progressBar.setProgressColor(context.getResources().getColor(R.color.colorPrimary));
            progressBar.setBackgroundColor(context.getResources().getColor(R.color.grey));
            progressBar.setProgress(50);
            progressBar.setText("50%");
            progressBar.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            progressBar.setStrokeWidth(6f);
        }

    }

}

