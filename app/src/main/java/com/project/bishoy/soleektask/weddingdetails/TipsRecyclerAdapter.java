package com.project.bishoy.soleektask.weddingdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.bishoy.soleektask.R;
import com.project.bishoy.soleektask.Util;
import com.project.bishoy.soleektask.data.model.Data;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bisho on 1/23/2018.
 */

public class TipsRecyclerAdapter extends RecyclerView.Adapter<TipsRecyclerAdapter.TipViewHolder> {

    private List<Data> dataList = Collections.emptyList();
    private Context context;


    public TipsRecyclerAdapter(List<Data> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public TipsRecyclerAdapter.TipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_layout, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipsRecyclerAdapter.TipViewHolder holder, int position) {
        Data data = dataList.get(position);
        holder.indexTv.setText((position + 1)+"");
        holder.contentTv.setText(data.getTitle());
        Picasso.with(context).load(Util.IMAGE_URL + data.getImage()).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TipViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_index)
        TextView indexTv;
        @BindView(R.id.tv_content)
        TextView contentTv;
        @BindView(R.id.imageView_thumbnail)
        ImageView thumbnail;

        public TipViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
