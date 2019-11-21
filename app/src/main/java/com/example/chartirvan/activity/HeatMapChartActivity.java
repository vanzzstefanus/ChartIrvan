package com.example.chartirvan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chartirvan.R;
import com.example.chartirvan.model.Malware;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HeatMapChartActivity extends AppCompatActivity {
    private static final String TAG = "HeatMapChartActivity";
    private LineChart mChart;
    private ArrayList<Malware> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map_chart);

        String malwareString = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();// create object GSON untuk rubah lagi ke object Malware
        Type type = new TypeToken<ArrayList<Malware>>() {
        }.getType();
        mDataList = gson.fromJson(malwareString, type); //Object dimasukkan ke dalam arraylist lagi

        mChart = findViewById(R.id.lineChart);

//        mChart.setOnChartGestureListener(HeatMapChartActivity.this);
//        mChart.setOnChartValueSelectedListener(HeatMapChartActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);


        setData(mDataList.size());

    }

    private void setData(int size) {
        ArrayList<Entry> yValues = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float y = (float) mDataList.get(i).getFwdPackets();
            yValues.add(new Entry(i, y));
        }

        LineDataSet set = new LineDataSet(yValues, "Data Set");
        set.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

    }
}
