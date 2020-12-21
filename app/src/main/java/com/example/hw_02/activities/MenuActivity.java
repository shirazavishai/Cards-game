package com.example.hw_02.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.hw_02.R;
import com.example.hw_02.utils.Signals;


public class MenuActivity extends AppCompatActivity {

    private static final int REQUESTED_PERMISSION = 44;
    private ImageView menu_IMG_background;
    private Button start_button;
    private Button top10_button;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hw_02_app_Menu:", "onCreate");
        setContentView(R.layout.activity_menu);

        initialView();

        checkPermission();

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_activity();
            }
        });

        top10_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top10_activity();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkPermission() {
        LocationManager lm = (LocationManager) MenuActivity.this.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS settings")
                    .setMessage("Open the GPS")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            Toast.makeText(MenuActivity.this, "Permission denied. Get default location", Toast.LENGTH_LONG).show();
                            Log.d("hw_02 app Menu:", "Cancel");
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            MenuActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            Log.d("hw_02 app Menu::", "OK");
                        }
                    });
            builder.show();
        }
        checkManifest();
    }

    private void checkManifest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUESTED_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUESTED_PERMISSION) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission denied. Deafult location", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void game_activity() {
        Intent myIntent = new Intent(this, GameActivity.class);
        Signals.getInstance().audio(R.raw.start_boxing_bell);
        startActivity(myIntent);
    }

    public void top10_activity() {
        Intent myIntent = new Intent(this, TopTenActivity.class);
        startActivity(myIntent);
    }

    private void initialView() {
        start_button = this.findViewById(R.id.menu_BTN_start);
        top10_button = this.findViewById(R.id.menu_BTN_top10);
        exit = this.findViewById(R.id.menu_BTN_exit);

        menu_IMG_background = this.findViewById(R.id.menu_IMG_background);
        updateBackground(R.drawable.panda_lion);
    }

    public void updateBackground(int id) {
        Glide
                .with(this)
                .load(id)
                .into(menu_IMG_background);
    }


    @Override
    protected void onStart() {
        Log.d("hw_02_app_Menu:", "onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("hw_02_app_Menu:", "onResume");
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            throw new AssertionError();
        else actionBar.hide();
    }

    @Override
    protected void onPause() {
        Log.d("hw_02_app_Menu:", "onPause");
        super.onPause();

    }

    @Override
    protected void onStop() {
        Log.d("hw_02_app_Menu:", "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("hw_02_app_Menu:", "onDestroy");
        super.onDestroy();
    }
}
