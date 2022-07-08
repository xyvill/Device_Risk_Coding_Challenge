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

public class MainActivity extends AppCompatActivity {
    private DeviceRiskViewModel model;
    private ActivityMainBinding binding;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                model.getBlackbox(this);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Initialize model object
        model = new ViewModelProvider(this).get(DeviceRiskViewModel.class);

        //Attach bluetooth request to blackbox button
        BluetoothRequestInit(binding.BlackboxButton);

        //Observe current blackbox data
        ObserveBlackBoxData(model);

    }

    private void BluetoothRequestInit(Button blackboxButton){

        blackboxButton.setOnClickListener( view -> {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.BLUETOOTH) ==
                    PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this, Manifest.permission.BLUETOOTH_CONNECT) ==
                    PackageManager.PERMISSION_GRANTED) {
                model.getBlackbox(this);
            }
            else {
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                    requestPermissionLauncher.launch(
                            Manifest.permission.BLUETOOTH);
                }
                else{
                    requestPermissionLauncher.launch(
                            Manifest.permission.BLUETOOTH_CONNECT);
                }
            }
        });
    }

    private void ObserveBlackBoxData(DeviceRiskViewModel model)
    {
        model.blackbox.observe(this, blackboxData -> {
            binding.BlackboxField.setText(blackboxData);
        });
    }
}