package com.example.chartirvan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.BubbleDataEntry;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Scatter;
import com.example.chartirvan.R;
import com.example.chartirvan.model.ListChart;
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
    private ListChart mListChart;
    private InputStream is;
    private TextView mDescription;
    AnyChartView anyChartViewl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_chart);
        mDescription = findViewById(R.id.description);
        anyChartViewl = findViewById(R.id.scatter);

        Intent intent = getIntent();
        mListChart = intent.getParcelableExtra(MainActivity.PACKET_DATA);
        try {
            readMalwareData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readMalwareData() throws IOException {
        mDataList = new ArrayList<>();
        mDescription.setText(mListChart.getDescription());
        if(mListChart.getIndex() == 1){
            is = getResources().openRawResource(R.raw.fwdpacketsourceportbubble);
        }
        else if(mListChart.getIndex() == 2){
            is = getResources().openRawResource(R.raw.flowpacketsourcebubble);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        reader.readLine(); // baca garis pertama

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            Malware malware = new Malware();
            malware.setSourcePort(Integer.parseInt(tokens[0]));
            malware.setDestinationPort(Integer.parseInt(tokens[1]));
            malware.setProtocol(Integer.parseInt(tokens[2]));
            malware.setFlowDuration(Integer.parseInt(tokens[3]));
            malware.setTotalFwdPackets(Integer.parseInt(tokens[4]));
            malware.setTotalBackwardPackets(Double.parseDouble(tokens[5]));
            malware.setTotalLengthofFwdPackets(Double.parseDouble(tokens[6]));
            malware.setTotalLengthofBwdPackets(Double.parseDouble(tokens[7]));
            malware.setFwdPacketsLengthMax(Double.parseDouble(tokens[8]));
            malware.setFwdPacketsLengthMin(Double.parseDouble(tokens[9]));
            malware.setFwdPacketsLengthMean(Double.parseDouble(tokens[10]));
            malware.setFwdPacketsLengthStd(Double.parseDouble(tokens[11]));
            malware.setBwdPacketLengthMax(Double.parseDouble(tokens[12]));
            malware.setBwdPacketLengthMin(Double.parseDouble(tokens[13]));
            malware.setBwdPacketLengthMean(Double.parseDouble(tokens[14]));
            malware.setBwdPacketLengthStd(Double.parseDouble(tokens[15]));
            malware.setFlowBytePerSecond(Double.parseDouble(tokens[16]));
            malware.setFlowPacketPerSecond(Double.parseDouble(tokens[17]));
            malware.setFlowIATMean(Double.parseDouble(tokens[18]));
            malware.setFlowIATStd(Double.parseDouble(tokens[19]));
            malware.setFlowIATMax(Double.parseDouble(tokens[20]));
            malware.setFlowIATMin(Double.parseDouble(tokens[21]));
            malware.setFwdIATTotal(Double.parseDouble(tokens[22]));
            malware.setFwdIATMean(Double.parseDouble(tokens[23]));
            malware.setFwdIATStd(Double.parseDouble(tokens[24]));
            malware.setFwdIATMax(Double.parseDouble(tokens[25]));
            malware.setFwdIATMin(Double.parseDouble(tokens[26]));
            malware.setBwdIATTotal(Double.parseDouble(tokens[27]));
            malware.setBwdIATMean(Double.parseDouble(tokens[28]));
            malware.setBwdIATStd(Double.parseDouble(tokens[29]));
            malware.setBwdIATMax(Double.parseDouble(tokens[30]));
            malware.setBwdIATMin(Double.parseDouble(tokens[31]));
            malware.setFwdPshFlags(Double.parseDouble(tokens[32]));
            malware.setBwdPshFlags(Double.parseDouble(tokens[33]));
            malware.setFwdUrgFlags(Double.parseDouble(tokens[34]));
            malware.setBwdUrgFlags(Double.parseDouble(tokens[35]));
            malware.setFwdHeaderLength(Double.parseDouble(tokens[36]));
            malware.setBwdHeaderLength(Double.parseDouble(tokens[37]));
            malware.setFwdPacketPerSecond(Double.parseDouble(tokens[38]));
            malware.setBwdPacketPerSecond(Double.parseDouble(tokens[39]));
            malware.setMinPacketLength(Double.parseDouble(tokens[40]));
            malware.setMaxPacketLength(Double.parseDouble(tokens[41]));
            malware.setPacketLengthMean(Double.parseDouble(tokens[42]));
            malware.setPacketLengthStd(Double.parseDouble(tokens[43]));
            malware.setPacketLengthVariance(Double.parseDouble(tokens[44]));
            malware.setFinFlagCount(Double.parseDouble(tokens[45]));
            malware.setSynFlagCount(Double.parseDouble(tokens[46]));
            malware.setRestFlagCount(Double.parseDouble(tokens[47]));
            malware.setPshFlagCount(Double.parseDouble(tokens[48]));
            malware.setAckFlagCount(Double.parseDouble(tokens[49]));
            malware.setUrgFlagCount(Double.parseDouble(tokens[50]));
            malware.setCweFlagCount(Double.parseDouble(tokens[51]));
            malware.setEceFlagCount(Double.parseDouble(tokens[52]));
            malware.setDownOrUpRatio(Double.parseDouble(tokens[53]));
            malware.setAveragePacketSize(Double.parseDouble(tokens[54]));
            malware.setAvgFwdSegmentSize(Double.parseDouble(tokens[55]));
            malware.setAvgBwdSegmentSize(Double.parseDouble(tokens[56]));
            malware.setFwdAvgBytesPerBulk(Double.parseDouble(tokens[57]));
            malware.setFwdAvgPacketsPerBulk(Double.parseDouble(tokens[58]));
            malware.setFwdAvgBulkRate(Double.parseDouble(tokens[59]));
            malware.setBwdAvgBytesPerBulk(Double.parseDouble(tokens[60]));
            malware.setBwdAvgPacketsPerBulk(Double.parseDouble(tokens[61]));
            malware.setBwdAvgBulkRate(Double.parseDouble(tokens[62]));
            malware.setSubflowFwdPackets(Double.parseDouble(tokens[63]));
            malware.setSubflowFwdBytes(Double.parseDouble(tokens[64]));
            malware.setSubflowBwdPackets(Double.parseDouble(tokens[65]));
            malware.setSubflowBwdBytes(Double.parseDouble(tokens[66]));
            malware.setInitWinBytesFwd(Double.parseDouble(tokens[67]));
            malware.setInitWinBytesBwd(Double.parseDouble(tokens[68]));
            malware.setActDataPktFwd(Double.parseDouble(tokens[69]));
            malware.setMinSegSizeFwd(Double.parseDouble(tokens[70]));
            malware.setActiveMean(Double.parseDouble(tokens[71]));
            malware.setActiveStd(Double.parseDouble(tokens[72]));
            malware.setActiveMax(Double.parseDouble(tokens[73]));
            malware.setActiveMin(Double.parseDouble(tokens[74]));
            malware.setIdleMean(Double.parseDouble(tokens[75]));
            malware.setIdleStd(Double.parseDouble(tokens[76]));
            malware.setIdleMax(Double.parseDouble(tokens[77]));
            malware.setIdleMin(Double.parseDouble(tokens[78]));
            malware.setLabel(tokens[79]);
            mDataList.add(malware);

        }
        if(mListChart.getIndex() == 1){
            chartOne();
        }
        else if (mListChart.getIndex() == 2){
            chartTwo();
        }


    }

    private void chartTwo() {
        bubble = AnyChart.bubble();
        bubble.animation(true);
        bubble.title().enabled(true);
        bubble.title().useHtml(true);
        bubble.title()
                .padding(0d, 0d, 10d, 0d)
                .text("Penyebaran Malware Berdasarkan Flow Packetnya");

        bubble.padding(20d, 20d, 10d, 20d);
        bubble.yGrid(true)
                .xGrid(true)
                .xMinorGrid(true)
                .yMinorGrid(true);

        bubble.minBubbleSize(5d)
                .maxBubbleSize(10d);

        bubble.xAxis(0)
                .title("Source Port")
                .minorTicks(true);
        bubble.yAxis(0)
                .title("Flow packet")
                .minorTicks(true);

        bubble.legend().enabled(true);
        bubble.labels().padding(0d, 0d, 10d, 0d);

        List<DataEntry> data = new ArrayList<>();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equalsIgnoreCase("adware")) {
                data.add(new CustomBubbleDataEntry(1, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFlowPacketPerSecond(), " adware", 1));
            }
        }
        bubble.bubble(data).name("Adware").color("#FF0000");
        data.clear();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equalsIgnoreCase("benign")) {
                data.add(new CustomBubbleDataEntry(2, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFlowPacketPerSecond(), " benign", 3));
            }
        }
        bubble.bubble(data).name("Benign").color("#0000FF");
        data.clear();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equalsIgnoreCase("GeneralMalware")) {
                data.add(new CustomBubbleDataEntry(3, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFlowPacketPerSecond(), " General Malware", 2));
            }
        }
        bubble.bubble(data).name("General Malware").color("#00FF00");

        bubble.tooltip()
                .useHtml(true)
                .fontColor("#fff")
                .format("function() {\n" +
                        "        return this.getData('data') + '<br/>' +\n" +
                        "          'Flow Packet: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('value') + '</span></strong><br/>' +\n" +
                        "          'Source port: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('x') + '</span></strong><br/>' +\n" +
                        "          'size: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('size') + '</span></strong>';\n" +
                        "      }");

        anyChartViewl.setChart(bubble);
    }

    private void chartOne(){
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
                .maxBubbleSize(10d);

        bubble.xAxis(0)
                .title("Source Port")
                .minorTicks(true);
        bubble.yAxis(0)
                .title("Forward Packet")
                .minorTicks(true);

        bubble.legend().enabled(true);
        bubble.labels().padding(0d, 0d, 10d, 0d);

        List<DataEntry> data = new ArrayList<>();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equalsIgnoreCase("adware")) {
                data.add(new CustomBubbleDataEntry(1, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " adware", 1));
            }
        }
        bubble.bubble(data).name("Adware").color("#FF0000");
        data.clear();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equalsIgnoreCase("benign")) {
                data.add(new CustomBubbleDataEntry(2, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " benign", 3));
            }
        }
        bubble.bubble(data).name("Benign").color("#0000FF");
        data.clear();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equalsIgnoreCase("GeneralMalware")) {
                data.add(new CustomBubbleDataEntry(3, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " General Malware", 2));
            }
        }
        bubble.bubble(data).name("General Malware").color("#00FF00");

//        for (int i = 0; i < 100; i++) {
//            if (mDataList.get(i).getLabel().equals("GeneralMalware")) {
//                data.add(new CustomBubbleDataEntry(2, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " General malware", 100));
//            }
//        }
//        bubble.bubble(data).name("GeneralMalware");
//        data.clear();
//        for (int i = 0; i < 100; i++) {
//            if (mDataList.get(i).getLabel().equals("benign")) {
//                data.add(new CustomBubbleDataEntry(3, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " Benign", 100));
//            }
//        }
//        bubble.bubble(data).name("Benign");
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

        anyChartViewl.setChart(bubble);
        Log.d(TAG, "readMalwareData: " + mDataList);
    }

    private class CustomBubbleDataEntry extends BubbleDataEntry {

        CustomBubbleDataEntry(Integer training, Integer x, Integer value, String data, Integer size) {
            super(x, value, size);
            setValue("training", training);
            setValue("data", data);
        }
    }
}
