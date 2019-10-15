package com.example.chartirvan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chartirvan.R;
import com.example.chartirvan.model.Malware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Malware> mData;

    // Tag for the intent extra.
    public static final String EXTRA_MESSAGE =
            "com.example.chartirvan.extra.MESSAGE";

    // The selected chart, displayed in the Toast and sent to the new Activity.
    private String mSelectedChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            readMalwareData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView bar = findViewById(R.id.iBar);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        BarChartActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mSelectedChart);
                startActivity(intent);
            }
        });

    }

    private void readMalwareData() throws IOException {
        mData = new ArrayList<>();
        InputStream is = getResources().openRawResource(R.raw.totalbeforepca);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;

        reader.readLine();  // pembacaan baris pertama
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

        }
    }


    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a message that the column chart image was clicked.
     */
    public void showBarChartSelected(View view) {
        mSelectedChart = getString(R.string.bar_message);
        displayToast(getString(R.string.bar_message));
    }

    /**
     * Shows a message that the heat map chart image was clicked.
     */
    public void showHeatMapChartSelected(View view) {
        mSelectedChart = getString(R.string.heat_map_message);
        displayToast(getString(R.string.heat_map_message));
    }

    /**
     * Shows a message that the scatter chart image was clicked.
     */
    public void showScatterMapChartSelected(View view) {
        mSelectedChart = getString(R.string.scatter_message);
        displayToast(getString(R.string.scatter_message));
    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param menu Menu to inflate.
     * @return Returns true if the menu inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles app bar item clicks.
     *
     * @param item Item clicked.
     * @return True if one of the defined items was clicked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // This comment suppresses the Android Studio warning about simplifying
        // the return statements.
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
        return super.onOptionsItemSelected(item);
    }


}
