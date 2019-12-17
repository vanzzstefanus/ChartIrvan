package com.example.chartirvan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.HeatDataEntry;
import com.anychart.charts.HeatMap;
import com.anychart.enums.SelectionMode;
import com.anychart.graphics.vector.SolidFill;
import com.example.chartirvan.R;
import com.example.chartirvan.model.Accuracy;
import com.example.chartirvan.model.ListChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity {
    private ArrayList<Accuracy> mDataList;
    private TextView mDescription;
    private static final String TAG = "HeatMapActivity";
    private HeatMap riskMap;
    private String fill;
    private ListChart mListChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
        mDescription = findViewById(R.id.description);
        Intent intent = getIntent();
        mListChart = intent.getParcelableExtra(MainActivity.PACKET_DATA);
        mDescription.setText(mListChart.getDescription());

        try {
            readAccuracyData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnyChartView anyChartView = findViewById(R.id.heatmap);
        riskMap = AnyChart.heatMap();

        riskMap.stroke("1 #fff");
        riskMap.hovered()
                .stroke("6 #fff")
                .fill(new SolidFill("#545f69", 1d))
                .labels("{ fontColor: '#fff' }");

        riskMap.interactivity().selectionMode(SelectionMode.NONE);

        riskMap.title().enabled(true);
        riskMap.title()
                .text("Heatmap Accuration of Malware based on Iteration")
                .padding(0d, 0d, 20d, 0d);

        riskMap.labels().enabled(true);
        riskMap.labels()
                .minFontSize(14d)
                .format("function() {\n" +
                        "      var namesList = [\"0%\",\"50-59%\", \"60-69%\", \"70-79%\", \"80-89%\", \"90-99%\"];\n" +
                        "      return namesList[this.heat];\n" +
                        "    }");

        riskMap.yAxis(0).stroke(null);
        riskMap.yAxis(0).labels().padding(0d, 15d, 0d, 0d);
        riskMap.yAxis(0).ticks(false);
        riskMap.xAxis(0).stroke(null);
        riskMap.xAxis(0).ticks(false);

        riskMap.tooltip().title().useHtml(true);
        riskMap.tooltip()
                .useHtml(true)
                .titleFormat("function() {\n" +
                        "      var namesList = [\"Low\", \"Medium\", \"High\", \"Extreme\"];\n" +
                        "      return '<b>' + '' \n" +
                        "    }")
                .format("function () {\n" +
                        "       return '<span style=\"color: #CECECE\"></span>' + this.x + '<br/>' +\n" +
                        "           '<span style=\"color: #CECECE\"></span>' + this.y;\n" +
                        "   }");

        List<DataEntry> data = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {

            if (mDataList.get(i).getAccuracy() == 1) {
                fill = "#F0FF00";
            } else if (mDataList.get(i).getAccuracy() == 2) {
                fill = "#FFCE00";
            } else if (mDataList.get(i).getAccuracy() == 3) {
                fill = "#FF9A00";
            } else if (mDataList.get(i).getAccuracy() == 4) {
                fill = "#FF5A00";
            } else if (mDataList.get(i).getAccuracy() == 5) {
                fill = "#FF0000";
            }

            data.add(new CustomHeatDataEntry(mDataList.get(i).getLabel(), String.valueOf(mDataList.get(i).getIteracy()), mDataList.get(i).getAccuracy(), fill));
        }
        riskMap.data(data);
        anyChartView.setChart(riskMap);
    }


    private class CustomHeatDataEntry extends HeatDataEntry {
        CustomHeatDataEntry(String x, String y, Integer heat, String fill) {
            super(x, y, heat);
            setValue("fill", fill);
        }
    }

    private void readAccuracyData() throws IOException {
        mDataList = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.heatmap);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            Accuracy accuracy = new Accuracy();
            accuracy.setIteracy(Integer.parseInt(tokens[1]));
            accuracy.setAccuracy(Integer.parseInt(tokens[2]));
            accuracy.setLabel(tokens[0]);

            mDataList.add(accuracy);

        }
    }


}
