package com.example.test_action_battery_changed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView batteryPercent;
    int level;

    private void getBatteryPercentage() {
        BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                level = -1;
                if (currentLevel >= 0 && scale > 0) {
                    level = (currentLevel * 100) / scale;
                }
                batteryPercent.setText("Pin hiện tại của bạn là: " + level + "%");


                CountDownTimer cdt = new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {

                        Toast.makeText(getApplicationContext(),"Level "+Integer.toString(level),
                                Toast.LENGTH_LONG).show();
                        ;
                    }

                }
                        .start();

            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        batteryPercent = findViewById(R.id.batteryPercent);
        getBatteryPercentage();
    }
}
