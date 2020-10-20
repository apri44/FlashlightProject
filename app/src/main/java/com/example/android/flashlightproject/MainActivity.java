package com.example.android.flashlightproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean flashOn;

    CameraManager camManager;
    String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout layout = findViewById(R.id.layout);
        final TextView textView = findViewById(R.id.textView);
        final SwitchCompat theme_switch = findViewById(R.id.theme_switch);
        final ImageView img_switch = findViewById(R.id.img_switch);
        flashOn = false;

        camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (theme_switch.isChecked()) {
                    textView.setText(getResources().getString(R.string.light_theme));
                    textView.setTextColor(getResources().getColor(R.color.dark));
                    layout.setBackgroundColor(getResources().getColor(R.color.white));
                }

                else {
                    textView.setText(getResources().getString(R.string.dark_theme));
                    textView.setTextColor(getResources().getColor(R.color.white));
                    layout.setBackgroundColor(getResources().getColor(R.color.dark));
                }
            }
        });

        img_switch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!flashOn) {
                    img_switch.setImageResource(R.drawable.power_on);
                    flashOn();
                }
                else {
                    img_switch.setImageResource(R.drawable.power_off);
                    flashOff();
                }
            }
        });
    }

    private void flashOn() {
        flashOn = true;

        try {
            cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
            camManager.setTorchMode(cameraId, true);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void flashOff() {
        flashOn = false;

        try {
            cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
            camManager.setTorchMode(cameraId, false);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}