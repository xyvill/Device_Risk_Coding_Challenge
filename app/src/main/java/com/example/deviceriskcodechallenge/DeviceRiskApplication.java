package com.example.deviceriskcodechallenge;


import android.app.Application;
import android.util.Log;

import com.iovation.mobile.android.FraudForceConfiguration;
import com.iovation.mobile.android.FraudForceManager;

public class DeviceRiskApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        FraudForceConfiguration configuration = new FraudForceConfiguration.Builder()
                .subscriberKey("0")
                .enableNetworkCalls(true) // Defaults to false if left out of configuration
                .build();

        FraudForceManager fraudForceManager = FraudForceManager.getInstance();
        fraudForceManager.initialize(configuration, this);

    }
}
