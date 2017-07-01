package com.androidtutorialpoint.mynavigationdrawer.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialpoint.mynavigationdrawer.MapsActivity;
import com.androidtutorialpoint.mynavigationdrawer.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import common.LocationGPS;

/**
 * Created by NguyenQuang on 5/12/2017.
 */

public class MyLocationFragment extends Fragment implements OnMapReadyCallback {
    private TextView tvMap, tvCurrentAddress;
    private GoogleMap mMap;

    private Double latitude;
    private Double longtitude;
    private String currentAddress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_location_fragment, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapsInitializer.initialize(this.getActivity());
        //get location
        LocationGPS locationGPS = new LocationGPS();

        latitude = locationGPS.checkAndGetLocation(getContext())[0];
        longtitude = locationGPS.checkAndGetLocation(getContext())[1];
        init(view);
        return view;
    }



    public void init(View view) {
        tvMap = (TextView) view.findViewById(R.id.tvMap);
        tvCurrentAddress = (TextView) view.findViewById(R.id.tvCurrentAddress);
        getAddress(latitude, longtitude);
        tvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + latitude + ">,<" + longtitude + ">?q=<" + latitude + ">,<" + longtitude + ">(" + currentAddress + ")"));

//                Intent intent = new Intent(getActivity(), MapsActivity.class);


                try {
                    getActivity().startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "Need install Google Map App", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            tvCurrentAddress.setText(add);
            currentAddress = add;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationGPS locationGPS = new LocationGPS();

        latitude = locationGPS.checkAndGetLocation(getContext())[0];
        longtitude = locationGPS.checkAndGetLocation(getContext())[1];
//         Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longtitude);




        mMap.addMarker(new MarkerOptions().position(sydney).title("My location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
    }
}
