package com.example.chartirvan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chartirvan.R;
import com.example.chartirvan.model.ListChart;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ListChart> mDataList;
    private static final String TAG = "ChartAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barchart_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mDataList.get(position), onClick);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public interface OnClicked{
        void  onClick (ListChart listChart);
    }
    private ChartAdapter.OnClicked onClick;

    public ChartAdapter(Context mContext, ArrayList<ListChart> mDataList, OnClicked onClick) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        this.onClick = onClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView number, parameterX, parameterY;
        private LinearLayout mainContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            parameterX = itemView.findViewById(R.id.parameterX);
            parameterY = itemView.findViewById(R.id.parameterY);
            mainContainer = itemView.findViewById(R.id.mainContainer);
        }
        public void bind(final ListChart listChart, final OnClicked onClicked){
            number.setText(String.valueOf(listChart.getIndex()));
            parameterX.setText(listChart.getParameterX());
            parameterY.setText(listChart.getParameterY());
            mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClicked.onClick(listChart);
                }
            });

        }
    }




}
