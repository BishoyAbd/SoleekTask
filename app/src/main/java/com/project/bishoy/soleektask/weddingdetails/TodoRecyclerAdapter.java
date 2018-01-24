package com.project.bishoy.soleektask.weddingdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.bishoy.soleektask.R;
import com.project.bishoy.soleektask.customviews.SmoothCheckBox;
import com.project.bishoy.soleektask.data.model.Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bisho on 1/24/2018.
 */

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder> {

    private List<Data> dataList;
    private Context context;

    public TodoRecyclerAdapter(List<Data> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_layout, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Data data = dataList.get(position);
        holder.checkBoxTodo.setChecked(true);
        holder.textViewTodo.setText(data.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_todo)
        TextView textViewTodo;
        @BindView(R.id.checkBox_todo)
        SmoothCheckBox checkBoxTodo;

        public TodoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
