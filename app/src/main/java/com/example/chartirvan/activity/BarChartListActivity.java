package com.example.chartirvan.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chartirvan.R;
import com.example.chartirvan.adapter.ChartAdapter;
import com.example.chartirvan.model.ListChart;

import java.util.ArrayList;

public class BarChartListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ChartAdapter mAdapter;
    private ArrayList<ListChart> mData;
    public static final String DATA_PACKET = "data_packet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart_list);

        initUI();
    }

    private void initUI() {
        setData();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new ChartAdapter(getApplicationContext(), mData, new ChartAdapter.OnClicked() {
            @Override
            public void onClick(ListChart listChart) {
                Intent intent = new Intent(BarChartListActivity.this, BarChartActivity.class);
                intent.putExtra(DATA_PACKET, listChart);
                startActivity(intent);
            }
        }));
    }

    private void setData() {
        mData = new ArrayList<>();
        mData.add(new ListChart(1, "Total fwd Packet", "Source Port", "Description"));
        mData.add(new ListChart(2, "FIN Flag Count", "Source Port", "Description"));
//        mData.add(new ListChart(3, "FIN Flag Count", "Destination Port", "Description"));

    }
}
