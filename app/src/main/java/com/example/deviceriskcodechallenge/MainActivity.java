package com.example.deviceriskcodechallenge;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.deviceriskcodechallenge.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {
    private DeviceRiskViewModel model;
    private ActivityMainBinding binding;
    private ExecutorService executorService;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                model.getBlackbox(this, executorService);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create ViewBinding to reference UI components
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        //Initialize executor service
        executorService = ((DeviceRiskApplication) getApplication()).GetBlackboxExecutor();

        //Initialize model object
        model = new ViewModelProvider(this).get(DeviceRiskViewModel.class);

        //Attach bluetooth request to blackbox button
        BluetoothRequestInit();

        //Observe current blackbox data
        ObserveBlackBoxData();

    }

    private void BluetoothRequestInit() {
        binding.BlackboxButton.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
                model.getBlackbox(this, executorService);
            }
            else if(Build.VERSION.SDK_INT > Build.VERSION_CODES.R && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED){
                model.getBlackbox(this, executorService);
            }
            else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
            }
        });
    }

    private void ObserveBlackBoxData() {
        model.blackbox.observe(this, blackboxData -> {
            binding.BlackboxField.setText(blackboxData);
        });
    }
}