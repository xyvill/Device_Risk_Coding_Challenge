package com.example.deviceriskcodechallenge;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DeviceRiskViewModel model;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                model.getBlackbox(this);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(DeviceRiskViewModel.class);

        //Viewbinding
        TextView blackboxView = ((TextView)findViewById(R.id.BlackboxField));
        Button   blackboxButton = ((Button)findViewById(R.id.BlackboxButton));

        blackboxButton.setOnClickListener( view -> {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.BLUETOOTH) ==
                    PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this, Manifest.permission.BLUETOOTH_CONNECT) ==
                    PackageManager.PERMISSION_GRANTED) {
                model.getBlackbox(this);
            }
            else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
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

        model.blackbox.observe(this, blackboxData -> {
            blackboxView.setText(blackboxData);
        });

    }

}