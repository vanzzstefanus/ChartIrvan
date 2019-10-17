package com.example.chartirvan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.BubbleDataEntry;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Scatter;
import com.example.chartirvan.R;
import com.example.chartirvan.model.Malware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ScatterChartActivity extends AppCompatActivity {
    private ArrayList<Malware> mDataList;
    private static final String TAG = "ScatterChartActivity";
    private Scatter bubble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_chart);
        try {
            readMalwareData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AnyChartView anyChartView = findViewById(R.id.scatter);

        bubble = AnyChart.bubble();
        bubble.animation(true);
        bubble.title().enabled(true);
        bubble.title().useHtml(true);
        bubble.title()
                .padding(0d, 0d, 10d, 0d)
                .text("Penyebaran Malware Berdasarkan Fwd Packetnya");

        bubble.padding(20d, 20d, 10d, 20d);
        bubble.yGrid(true)
                .xGrid(true)
                .xMinorGrid(true)
                .yMinorGrid(true);

        bubble.minBubbleSize(5d)
                .maxBubbleSize(40d);

        bubble.xAxis(0)
                .title("Source Port")
                .minorTicks(true);
        bubble.yAxis(0)
                .title("Forward Packet")
                .minorTicks(true);

        bubble.legend().enabled(true);
        bubble.labels().padding(0d, 0d, 10d, 0d);

        List<DataEntry> data = new ArrayList<>();

        for(int i =0;i<mDataList.size();i++){
            if(mDataList.get(i).getLabel().equals("adware")){
                data.add(new CustomBubbleDataEntry(1, mDataList.get(i).getSourcePort(), (int)mDataList.get(i).getFwdPackets()," adware",100));
            }
        }
        bubble.bubble(data).name("Adware");
        data.clear();

        for(int i =0;i<mDataList.size();i++){
            if(mDataList.get(i).getLabel().equals("General malware")){
                data.add(new CustomBubbleDataEntry(2, mDataList.get(i).getSourcePort(), (int)mDataList.get(i).getFwdPackets()," General malware",100));
            }
        }
        bubble.bubble(data).name("General Malware");
        data.clear();

        for(int i =0;i<mDataList.size();i++){
            if(mDataList.get(i).getLabel().equals("Benign")){
                data.add(new CustomBubbleDataEntry(3, mDataList.get(i).getSourcePort(), (int)mDataList.get(i).getFwdPackets()," Benign",100));
            }
        }
        bubble.bubble(data).name("Benign");
        bubble.tooltip()
                .useHtml(true)
                .fontColor("#fff")
                .format("function() {\n" +
                        "        return this.getData('data') + '<br/>' +\n" +
                        "          'Forward Packet: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('value') + '</span></strong><br/>' +\n" +
                        "          'Source port: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('x') + '</span></strong><br/>' +\n" +
                        "          'size: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('size') + '</span></strong>';\n" +
                        "      }");

        anyChartView.setChart(bubble);



    }


    private void readMalwareData() throws IOException {
        mDataList = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.scatter);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;

        reader.readLine(); // baca garis pertama

        while ((line = reader.readLine()) != null){
            String[] tokens = line.split(",");
            Malware malware = new Malware();
            malware.setSourcePort(Integer.parseInt(tokens[0]));
            malware.setDestinationPort(Integer.parseInt(tokens[1]));
            malware.setProtocol(Integer.parseInt(tokens[2]));
            malware.setFlowDuration(Integer.parseInt(tokens[3]));
            malware.setTotalFwdPackets(Integer.parseInt(tokens[4]));
            malware.setFlowPackets(Double.parseDouble(tokens[5]));
            malware.setFlowIATMean(Double.parseDouble(tokens[6]));
            malware.setFlowIATMax(Double.parseDouble(tokens[7]));
            malware.setFlowIATMin(Integer.parseInt(tokens[8]));
            malware.setFwdIATTotal(Double.parseDouble(tokens[9]));
            malware.setFwdIATMean(Double.parseDouble(tokens[10]));
            malware.setFwdIATMax(Double.parseDouble(tokens[11]));
            malware.setFwdIATMin(Double.parseDouble(tokens[12]));
            malware.setFwdHeaderLength(Integer.parseInt(tokens[13]));
            malware.setBwdHeaderLength(Integer.parseInt(tokens[14]));
            malware.setFwdPackets(Double.parseDouble(tokens[15]));
            malware.setInitWinBytesFwd(Integer.parseInt(tokens[16]));
            malware.setLabel(tokens[17]);

            mDataList.add(malware);
        }
    }

    private class CustomBubbleDataEntry extends BubbleDataEntry {

        CustomBubbleDataEntry(Integer training, Integer x, Integer value, String data, Integer size) {
            super(x, value, size);
            setValue("training", training);
            setValue("data", data);
        }
    }
}
