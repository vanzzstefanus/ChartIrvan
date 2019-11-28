package com.example.chartirvan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.chartirvan.R;
import com.example.chartirvan.model.Malware;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {
    private static final String TAG = "BarChartActivity";
    private BarChart mChart;
    private ArrayList<Malware> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        // Get the intent and its data.
        String malwareString = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        Gson gson = new Gson();// create object GSON untuk rubah lagi ke object Malware
        Type type = new TypeToken<ArrayList<Malware>>() {
        }.getType();
        mDataList = gson.fromJson(malwareString, type); //Object dimasukkan ke dalam arraylist lagi
        for (Malware malware : mDataList) {
            Log.d(TAG, "onCreate:" + malware);
        }

        mChart = findViewById(R.id.barChart);
        mChart.getDescription().setEnabled(false);

        setData(mDataList.size());
        mChart.setFitBars(true);

    }

    private void setData(int size) {
        ArrayList<BarEntry> yVals = new ArrayList<>(); //Array List untuk BarEntry


        for (int i = 0; i < size; i++) {
            float y = (float) mDataList.get(i).getFlowPacketPerSecond();

            yVals.add(new BarEntry(i, y));

            Log.d(TAG, "setData: " + yVals);
        } // dibuat x.y nya dengan variable name yVals, di set sejumlah panjang ArrayList dengan Data Type Malware

        BarDataSet set = new BarDataSet(yVals, "Data Set");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);

        mChart.setData(data);
        mChart.invalidate();
        mChart.animateX(500);
    }
}
