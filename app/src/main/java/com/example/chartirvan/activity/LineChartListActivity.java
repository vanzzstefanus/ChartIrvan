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

public class LineChartListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ChartAdapter mAdapter;
    private ArrayList<ListChart> mData;
    private static final String TAG = "LineChartListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_list);

        initUI();
    }

    private void initUI() {
        setData();
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new ChartAdapter(getApplicationContext(), mData, new ChartAdapter.OnClicked() {
            @Override
            public void onClick(ListChart listChart) {
                Intent intent = new Intent(LineChartListActivity.this, LineChartActivity.class);
                intent.putExtra(BarChartListActivity.DATA_PACKET, listChart);
                startActivity(intent);
            }
        }));

    }

    private void setData() {
        mData = new ArrayList<>();
        mData.add(new ListChart(1,"Fwd Packet/s","Bwd Packet/s","description"));
        mData.add(new ListChart(2,"Source Port","Destination Port","description"));
        mData.add(new ListChart(3,"Fwd IAT std","Source Port","description"));
        mData.add(new ListChart(4,"Bwd IAT std","Destination Port","description"));
    }
}
