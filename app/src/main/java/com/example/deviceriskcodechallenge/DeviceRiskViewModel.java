package com.example.deviceriskcodechallenge;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iovation.mobile.android.FraudForceManager;

import java.util.concurrent.ExecutorService;

public class DeviceRiskViewModel extends ViewModel {
    private FraudForceManager fraudForceManager = FraudForceManager.getInstance();
    private MutableLiveData<String> _blackbox = new MutableLiveData("Press Button To Generate Blackbox");
    public LiveData<String> blackbox = _blackbox;

    public void getBlackbox(Context context, ExecutorService blackboxExecutor) {
        if(context == null || blackboxExecutor == null){
            return;
        }
        blackboxExecutor.execute(() -> {
            fraudForceManager.refresh(context);
            String generatedBlackbox = fraudForceManager.getBlackbox(context);
            _blackbox.postValue(generatedBlackbox);
            Log.d("Blackbox: ", generatedBlackbox);
        });
    }

}
