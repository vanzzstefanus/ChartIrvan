package com.example.chartirvan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chartirvan.R;
import com.example.chartirvan.model.ListChart;
import com.example.chartirvan.model.Malware;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {
    private static final String TAG = "LineChartActivity";
    private LineChart mChart;
    private ArrayList<Malware> mDataList;
    private float mParameterY1;
    private float mParameterY2;
    private String mNameDataSet1;
    private String mNameDataSet2;

    private int mParameterX1;
    private int mParameterX2;

    private ListChart mObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map_chart);

        try {
            readMalwareData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mChart = findViewById(R.id.lineChart);

//        mChart.setOnChartGestureListener(LineChartActivity.this);
//        mChart.setOnChartValueSelectedListener(LineChartActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);


        setData(mDataList.size());

    }

    private void readMalwareData() throws IOException {
        mDataList = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.totalbeforepca_compressed);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;

        reader.readLine();  // pembacaan baris pertama
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
            ;
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
            Log.d(TAG, "readMalwareData: " + malware);
        }
    }

    private void setData(int size) {
        ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            mObject = intent.getParcelableExtra(BarChartListActivity.DATA_PACKET);
            mNameDataSet1 = mObject.getParameterX();
            mNameDataSet2 = mObject.getParameterY();
        }

        for (int i = 0; i < size; i++) {
            mParameterX1 = i;

            if(mObject.getIndex() == 1){
                mParameterY1 = (float) mDataList.get(i).getFwdPacketPerSecond();
            }
            else if (mObject.getIndex() == 2){
                mParameterY1 = (float) mDataList.get(i).getSourcePort();
            }
            else if(mObject.getIndex() ==3 ){
                mParameterY1 = (float) mDataList.get(i).getFwdIATStd();
            }
            else if(mObject.getIndex() == 4){
                mParameterY1 = (float) mDataList.get(i).getBwdIATStd();
            }
            yValues1.add(new Entry(i, mParameterY1));
        }

        for (int j = 0; j < size; j++) {
            mParameterX2 = j;

            if(mObject.getIndex() == 1){
                mParameterY2 = (float) mDataList.get(j).getBwdPacketPerSecond();
            }
            else if (mObject.getIndex() == 2){
                mParameterY2 = (float) mDataList.get(j).getDestinationPort();
            }
            else if(mObject.getIndex() ==3 ){
                mParameterY2 = (float) mDataList.get(j).getSourcePort();
            }
            else if(mObject.getIndex() == 4){
                mParameterY2 = (float) mDataList.get(j).getDestinationPort();
            }
            yValues2.add(new Entry(j, mParameterY2));
        }

        LineDataSet set1 = new LineDataSet(yValues1, mNameDataSet1);
        set1.setFillAlpha(110);
        set1.setColor(Color.BLUE);

        LineDataSet set2 = new LineDataSet(yValues2, mNameDataSet2);
        set2.setFillAlpha(110);
        set2.setColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

    }
}
