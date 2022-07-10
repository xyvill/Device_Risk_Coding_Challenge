package com.example.deviceriskcodechallenge;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DeviceRiskViewModelTest {
    public DeviceRiskViewModel deviceRiskViewModel = new DeviceRiskViewModel();
    public Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    public ExecutorService blackboxExecutor = Executors.newSingleThreadExecutor();

    @Test
    public void GetBlackboxTestNullContext() { deviceRiskViewModel.getBlackbox(null, blackboxExecutor); }
    @Test
    public void GetBlackboxTestNullExecutor()
    {
        deviceRiskViewModel.getBlackbox(context, null);
    }
    @Test
    public void GetBlackboxTestNullArg()
    {
        deviceRiskViewModel.getBlackbox(null, null);
    }
    @Test
    public void GetBlackboxTestExpectedArg() { deviceRiskViewModel.getBlackbox(context, blackboxExecutor); }

}
