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

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equals("adware")) {
                data.add(new CustomBubbleDataEntry(1, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " adware", 100));
            }
        }
        bubble.bubble(data).name("Adware");
        data.clear();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equals("General malware")) {
                data.add(new CustomBubbleDataEntry(2, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " General malware", 100));
            }
        }
        bubble.bubble(data).name("General Malware");
        data.clear();

        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLabel().equals("Benign")) {
                data.add(new CustomBubbleDataEntry(3, mDataList.get(i).getSourcePort(), (int) mDataList.get(i).getFwdPacketPerSecond(), " Benign", 100));
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

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            Malware malware = new Malware();
            malware.setSourcePort(Integer.parseInt(tokens[0]));
            malware.setDestinationPort(Integer.parseInt(tokens[1]));
            malware.setProtocol(Integer.parseInt(tokens[2]));
            malware.setFlowDuration(Integer.parseInt(tokens[3]));
            malware.setTotalFwdPackets(Integer.parseInt(tokens[4]));
            malware.setTotalBackwardPackets(Integer.parseInt(tokens[5]));
            malware.setTotalLengthofFwdPackets(Integer.parseInt(tokens[6]));
            malware.setTotalLengthofBwdPackets(Integer.parseInt(tokens[7]));
            malware.setFwdPacketsLengthMax(Integer.parseInt(tokens[8]));
            malware.setFwdPacketsLengthMin(Integer.parseInt(tokens[9]));
            malware.setFwdPacketsLengthMean(Integer.parseInt(tokens[10]));
            malware.setFwdPacketsLengthStd(Integer.parseInt(tokens[11]));
            malware.setBwdPacketLengthMax(Integer.parseInt(tokens[12]));
            malware.setBwdPacketLengthMin(Integer.parseInt(tokens[13]));
            malware.setBwdPacketLengthMean(Integer.parseInt(tokens[14]));
            malware.setBwdPacketLengthStd(Integer.parseInt(tokens[15]));
            malware.setFlowBytePerSecond(Integer.parseInt(tokens[16]));
            malware.setFlowPacketPerSecond(Double.parseDouble(tokens[17]));
            malware.setFlowIATMean(Double.parseDouble(tokens[18]));
            malware.setFlowIATStd(Double.parseDouble(tokens[19]));
            malware.setFlowIATMax(Double.parseDouble(tokens[20]));
            malware.setFlowIATMin(Integer.parseInt(tokens[21]));
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
            malware.setFwdPshFlags(Integer.parseInt(tokens[32]));
            malware.setBwdPshFlags(Integer.parseInt(tokens[33]));
            malware.setFwdUrgFlags(Integer.parseInt(tokens[34]));
            malware.setBwdUrgFlags(Integer.parseInt(tokens[35]));
            malware.setFwdHeaderLength(Integer.parseInt(tokens[36]));
            malware.setBwdHeaderLength(Integer.parseInt(tokens[37]));
            malware.setFwdPacketPerSecond(Double.parseDouble(tokens[38]));
            malware.setBwdPacketPerSecond(Double.parseDouble(tokens[39]));
            malware.setMinPacketLength(Integer.parseInt(tokens[40]));
            malware.setMaxPacketLength(Integer.parseInt(tokens[41]));
            malware.setPacketLengthMean(Integer.parseInt(tokens[42]));
            malware.setPacketLengthStd(Integer.parseInt(tokens[43]));
            malware.setPacketLengthVariance(Integer.parseInt(tokens[44]));
            malware.setFinFlagCount(Integer.parseInt(tokens[45]));
            malware.setSynFlagCount(Integer.parseInt(tokens[46]));
            malware.setRestFlagCount(Integer.parseInt(tokens[47]));
            malware.setPshFlagCount(Integer.parseInt(tokens[48]));
            malware.setAckFlagCount(Integer.parseInt(tokens[49]));
            malware.setUrgFlagCount(Integer.parseInt(tokens[50]));
            malware.setCweFlagCount(Integer.parseInt(tokens[51]));
            malware.setEceFlagCount(Integer.parseInt(tokens[52]));
            malware.setDownOrUpRatio(Integer.parseInt(tokens[53]));
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
            malware.setInitWinBytesFwd(Integer.parseInt(tokens[67]));
            malware.setInitWinBytesBwd(Integer.parseInt(tokens[68]));
            malware.setActDataPktFwd(Integer.parseInt(tokens[69]));
            malware.setMinSegSizeFwd(Integer.parseInt(tokens[70]));
            malware.setActiveMean(Integer.parseInt(tokens[71]));
            malware.setActiveStd(Integer.parseInt(tokens[72]));
            malware.setActiveMax(Integer.parseInt(tokens[73]));
            malware.setActiveMin(Integer.parseInt(tokens[74]));
            malware.setIdleMean(Integer.parseInt(tokens[75]));
            malware.setIdleStd(Integer.parseInt(tokens[76]));
            malware.setIdleMax(Integer.parseInt(tokens[77]));
            malware.setIdleMin(Integer.parseInt(tokens[78]));
            malware.setLabel(tokens[79]);

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
