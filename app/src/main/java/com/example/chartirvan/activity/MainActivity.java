package com.example.chartirvan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chartirvan.R;
import com.example.chartirvan.adapter.ChartAdapter;
import com.example.chartirvan.helper.INodeJs;
import com.example.chartirvan.helper.RetrofitClient;
import com.example.chartirvan.model.ListChart;
import com.example.chartirvan.model.Malware;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Malware> mData;
    private static final String TAG = "MainActivity";
    private static final int FILE_REQUEST_CODE = 10;
    public static final String PACKET_DATA = "packet_data";

    // Tag for the intent extra.
    public static final String EXTRA_MESSAGE =
            "com.example.chartirvan.extra.MESSAGE";

    // The selected chart, displayed in the Toast and sent to the new Activity.
    private String mSelectedChart;
    private Button mLoad;
    INodeJs myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RecyclerView mRecyclerView;
    private ChartAdapter mAdapter;
    private ArrayList<ListChart> mChartList;



    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJs.class);
        mLoad = findViewById(R.id.buttonLoad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            readMalwareData();
            setData();
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
            mRecyclerView.setAdapter(new ChartAdapter(getApplicationContext(), mChartList, new ChartAdapter.OnClicked() {
                @Override
                public void onClick(ListChart listChart) {
                    if(listChart.getIndex() == 1){
                        Intent intent = new Intent(MainActivity.this, ScatterChartActivity.class);
                        intent.putExtra(PACKET_DATA, listChart);
                        startActivity(intent);
                    }
                    else if(listChart.getIndex() == 2){
                        Intent intent = new Intent(MainActivity.this, ScatterChartActivity.class);
                        intent.putExtra(PACKET_DATA, listChart);
                        startActivity(intent);
                    }
                    else if(listChart.getIndex() == 3){
                        Intent intent = new Intent(MainActivity.this, HeatMapActivity.class);
                        intent.putExtra(PACKET_DATA, listChart);
                        startActivity(intent);
                    }
                    else if(listChart.getIndex() == 4){
                        Intent intent = new Intent(MainActivity.this, LineChartActivity.class);
                        intent.putExtra(PACKET_DATA, listChart);
                        startActivity(intent);
                    }
                    else if(listChart.getIndex() == 5){
                        Intent intent = new Intent(MainActivity.this, BarChartActivity.class);
                        intent.putExtra(PACKET_DATA, listChart);
                        startActivity(intent);
                    }
                    else if(listChart.getIndex() == 6){
                        Intent intent = new Intent(MainActivity.this, LineChartActivity.class);
                        intent.putExtra(PACKET_DATA, listChart);
                        startActivity(intent);
                    }
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void setData() {
        mChartList = new ArrayList<>();
        mChartList.add(new ListChart(1,"Source port","Fwd Packet/s","Bubble Chart ini menunjukkan hubungan antara source port dengan forward packet/s. Source port  merupakan port awal di mana paket data dikirimkan, sedangkan forward packet/s merupakan jumlah paket yang dikirimkan setiap detiknya. \n \n" +
                "Malware tipe Benign hanya berada di kisaran port 30.000 sampai 60.000. Paket data yang dikirimkan Benign memiliki ukuran yang cukup besar. Hal ini dikarenakan Benign merupakan tipe aplikasi normal yang biasa digunakan seperti Google Play, Instagram, atau aplikasi sejenisnya, walaupun ada beberapa kasus di mana ada beberapa aplikasi yang sengaja dibuat untuk merusak perangkat mobile. \n \n" +
                "Lain halnya dengan adware dan general malware, kedua jenis malware ini memiliki forward paket/s yang kecil. Hal ini berarti pembuat malware  ingin menyamarkan perilaku dari malware agar tidak mudah diketahui oleh pengguna perangkat mobile. Bahkan untuk malware dengan tipe adware, paket data yang dikirimkan dari source port berkumpul di bawah atau dapat disimpulkan jauh lebih kecil dibandingkan kedua kelas lainnya.\n \n" +
                "Bubble chart ini memiliki mode selective. Mode ini memungkinkan pengguna untuk dapat memilih kelas malware yang diinginkan. Pemilihan kelas malware dapat membantu pengguna untuk menganalisis lebih jauh. Mode selective ini dapat berdiri sendiri atau digabungkan antar kelas malware."));
        mChartList.add(new ListChart(2,"Source Port","Flow packet/s","Bubble chart ini menunjukkan antara flow packet/s dengan source port. Flow packet/s merupakan banyak paket data yang berada di dalam lalu lintas jaringan perangkat mobile. Malware tipe kelas Benign berada di kisaran port awal dan port puluhan ribu. Dalam gambar ini, Benign juga terlihat memiliki flow packet/s yang cukup besar, hal ini dikarenakan Benign merupakan aplikasi yang sering digunakan sehingga arus data paket di dalam jaringan perangkat mobile menjadi cukup besar."));
        mChartList.add(new ListChart(3,"Iteration","Clustering Malware","Heatmap chart ini menampilkan antara kelas malware atau hasil clustering malware dari penelitian sebelumnya dengan iterasi yang dilakukan. Penelitian sebelumnya melakukan clustering malware dengan menggunakan gabungan metode K-mean dan SVM. Dilakukan beberapa iterasi untuk menentukan jenis malware berdasarkan tiga kelas yang telah ada, yaitu adware, benign dan general malware. \n \n" +
                "Grafik heatmap ini menunjukkan bahwa iterasi terbesar untuk menunjukkan kelas adware adalah iterasi kedua dan kelima. Kelas general malware dapat ditunjukkan pada iterasi kedua dan keempat, sedangkan untuk kelas benign dapat ditunjukkan pada iterasi pertama dan kedua. Hal ini menunjukkan bahwa iterasi semakin banyak belum tentu semakin baik untuk menentukan kelas malaware. Kesimpulan lain yang diperoleh adalah iterasi kedua merupakan iterasi yang paling baik untuk mengklasifikasikan jenis malware dari ketiga kelas yang sudah ada."));
        mChartList.add(new ListChart(4,"Total fwd packet","Total bwd packet","Line chart ini membandingkan antara total fwd packet/s dan total bwd packet/s dengan source port. Grafik line chart ini menunjukkan bahwa terdapat beberapa port yang memiliki perbedaan jumlah seluruh paket yang dikirim dan seluruh paket yang diterima. Jumlah seluruh paket yang diterima lebih besar dari jumlah seluruh paket yang diterima, hal ini berarti paket-paket tersebut telah disusupi atau telah terinfeksi dari malware."));
        mChartList.add(new ListChart(5,"Destination port","Flow Packet/s","Bar chart menunjukkan antara FIN flag count dengan destination port berdasarkan flow packet/s. FIN flag count merupakan tanda bahwa paket data yang dikirimkan mendapat respons dari penerima. Saat source port mengirimkan paket data, source port akan mengirimkannya disertai dengan ACK. ACK kepanjangan dari acknowledgment, yang digunakan untuk menerima persetujuan koneksi yang akan dibuat oleh yang me-request koneksi, ACK akan di set selalu bernilai 1 jika telah terjadi koneksi, kecuali pada saat pertama pembuatan sesi koneksi pada TCP. FIN adalah sebuah flag yang digunakan untuk menyelesaikan sebuah koneksi ketika pengiriman data telah selesai, FIN akan bernilai 1 setelah koneksi selesai.\n \n" +
                "Grafik ini memperlihatkan bahwa FIN flag yang bernilai 1 berasal dari destination port puluhan ribu atau port besar. Dari data yang diperoleh juga, semua FIN flag yang bernilai satu merupakan kelas general malware dengan flow packet/s yang cukup tinggi. Hal ini dapat disimpulkan bahwa salah satu serangan dari general malware menggunakan FIN flag untuk menyerang perangkat mobile korbannya."));
        mChartList.add(new ListChart(6,"Port"," The most attacked","Line chart ini merupakan kesimpulan dari seluruh grafik-grafik yang telah dibuat sebelumnya."));
    }

    private void readMalwareData() throws IOException {
        mData = new ArrayList<>();
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

            mData.add(malware);
            Log.d(TAG, "readMalwareData: " + malware);
        }

        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gson.toJson(mData);
                Log.d(TAG, "onClick: " + jsonString);
                String jsonTest = "[{\"abc\": 0}]";
                Log.d(TAG, "onClick: " + jsonTest);
                compositeDisposable.add(myAPI.predict(jsonString)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                                       @Override
                                       public void accept(String s) throws Exception {
                                           Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                                           Log.d(TAG, "accept: Sukses brow lop u");
                                           Log.d(TAG, "accept: " + s);
                                       }


                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           Log.d(TAG, "accept: " + throwable);
                                       }
                                   }
                        ));

            }
        });
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
        startActivity(new Intent(this, ScatterChartActivity.class));
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


    public void showHeat(View view) {
        startActivity(new Intent(this, HeatMapActivity.class));

    }

    public void loadCSVFile(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowFiles(true)
                .setShowImages(false)
                .setShowVideos(false)
                .setShowAudios(false)
                .setSuffixes("csv")
                .build());
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FILE_REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

                    Log.d(TAG, "onActivityResult: "+files.get(0).getName());
                    Log.d(TAG, "onActivityResult: "+files.get(0).getUri());
                    Log.d(TAG, "onActivityResult: " + files);

                    Toast.makeText(this, "File loaded", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
