package com.example.hw_02.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw_02.CallBack_map;
import com.example.hw_02.R;
import com.example.hw_02.fragments.Fragment_list;
import com.example.hw_02.fragments.Fragment_map;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TopTenActivity extends AppCompatActivity implements CallBack_map {

    private Fragment_map f_m;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        Log.d("hw_02_app_Top10:", "onCreate");
        findViews();
        initViews();

        initFragments();
    }

    private void initFragments() {
        Fragment_list f_l = new Fragment_list();
        f_l.setCallBack_map(this);
        getSupportFragmentManager().beginTransaction().add(R.id.topten_FLAY_list, f_l).commit();

        f_m = new Fragment_map();
        getSupportFragmentManager().beginTransaction().add(R.id.topten_FLAY_map, f_m).commit();
    }

    private void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        back = this.findViewById(R.id.topten_BTN_back);
    }

    @Override
    public void getLocation(double lng, double lat, String name) {
        LatLng latLng = new LatLng(lat, lng);
        Log.d("hw_02_app_Top10:", "getLocation" + name + " " + lng + " " + lat);
        f_m.getGoogleMap().addMarker(new MarkerOptions().position(latLng).title(name));
        f_m.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));
        f_m.getGoogleMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.3f));

    }

    @Override
    protected void onStart() {
        Log.d("hw_02_app_Top10:", "onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        Log.d("hw_02_app_Top10:", "onResume");
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
        Log.d("hw_02_app_Top10:", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("hw_02_app_Top10:", "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("hw_02_app_Top10:", "onDestroy");
        super.onDestroy();
    }

}