package com.example.hw_02.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.hw_02.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Fragment_map extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("hw_02_app_map:", "OncreateView-fragment map");

        View view = inflater.inflate(R.layout.fragment_lay_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.f_m_FRG_map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    public GoogleMap getGoogleMap() {
        return mMap;
    }
}