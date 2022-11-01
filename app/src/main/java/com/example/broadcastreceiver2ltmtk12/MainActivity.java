package com.example.broadcastreceiver2ltmtk12;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Intent filer -> loc su kien
    private IntentFilter mIntentFilter;

    // Broadcast receiver -> xử lý sự kiện
    private BroadcastReceiver mBroadcastReceiver;

    private final static String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    private final static String ACTION_POWER_DISCONECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";

    // View
    private ConstraintLayout rootView;

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return  true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(R.id.rootView);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("myTest");
        mIntentFilter.addAction(ACTION_POWER_CONNECTED);
        mIntentFilter.addAction(ACTION_POWER_DISCONECTED);
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "myTest":
                        Toast.makeText(MainActivity.this, "Tạo bời người dùng", Toast.LENGTH_SHORT).show();
                        break;
                    case ACTION_POWER_CONNECTED:
                        Toast.makeText(MainActivity.this, "Power connected", Toast.LENGTH_SHORT).show();
                        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                        break;
                    case ACTION_POWER_DISCONECTED:
                        Toast.makeText(MainActivity.this, "Power disconnected", Toast.LENGTH_SHORT).show();
                        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                        break;
                    case "android.net.conn.CONNECTIVITY_CHANGE":
                        if(isConnected()){
                            Toast.makeText(MainActivity.this,"Internet available",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
            }
        };

    }

    public void sendMyBroadcast(View view) {
        sendBroadcast(new Intent("myTest"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }
}