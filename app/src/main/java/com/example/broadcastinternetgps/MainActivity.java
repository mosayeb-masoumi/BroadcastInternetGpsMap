package com.example.broadcastinternetgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txt_internet , txt_gps;
    BroadcastReceiver connectivityReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txt_internet = findViewById(R.id.txt_internet);
        txt_gps = findViewById(R.id.txt_gps);





        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (!internetConnection()) {
                    Log.i("TAG", "NO INTERNET");
                    txt_internet.setText("NO INTERNET");

                } else {
                    Log.i("TAG", "YES INTERNET");
                    txt_internet.setText("YES INTERNET");
                }

                if (!checkGpsOn()) {
                    Log.i("TAG", "NO LOCATION");
                    txt_gps.setText("NO LOCATION");
                } else {
                    Log.i("TAG", "YES LOCATION");
                    txt_gps.setText("YES LOCATION");
                }


            }
        };
    }


    private boolean internetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    private boolean checkGpsOn() {
        final LocationManager manager = (LocationManager) (MainActivity.this).getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    @Override
    protected void onResume() {
        super.onResume();


        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);

        registerReceiver(connectivityReceiver, filter);
//        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }
}