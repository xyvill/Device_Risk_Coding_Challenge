package com.example.deviceriskcodechallenge;


import android.app.Application;
import android.util.Log;

import com.iovation.mobile.android.FraudForceConfiguration;
import com.iovation.mobile.android.FraudForceManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceRiskApplication extends Application {
    private ExecutorService blackboxExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void onCreate() {
        super.onCreate();

        FraudForceConfiguration configuration = new FraudForceConfiguration.Builder()
                .subscriberKey("0")
                .enableNetworkCalls(true)
                .build();

        FraudForceManager fraudForceManager = FraudForceManager.getInstance();
        fraudForceManager.initialize(configuration, this);

    }

    public ExecutorService GetBlackboxExecutor() {
        return blackboxExecutor;
    }
}
