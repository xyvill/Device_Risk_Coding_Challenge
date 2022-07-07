package com.example.deviceriskcodechallenge;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iovation.mobile.android.FraudForceManager;

public class DeviceRiskViewModel extends ViewModel {
    private MutableLiveData<String> _blackbox = new MutableLiveData("Press Button To Generate Blackbox");
    public LiveData<String> blackbox = _blackbox;
    public void getBlackbox(Context context) {
        String generatedBlackbox = FraudForceManager.getInstance().getBlackbox(context);
        _blackbox.setValue(generatedBlackbox);
        Log.d("Blackbox: ", generatedBlackbox);
    }

}
