package com.pgmacdesign.googlemapsproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.support.v4.app.FragmentActivity;


public class ActivityTwo extends FragmentActivity implements View.OnClickListener {

	//Gobal Variables
	GoogleMap map;
	
	//Shared Preferences
	public static final String PREFS_NAME = "MapsData";	
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	
	//Main - When the activity starts
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);

		//Initialize Variables
		Initialize();
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		//map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//map.setMapType(GoogleMap.MAP_TYPE_NONE);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		
		
		/*
		//Goes to your location and zooms in:
		CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(40.76793169992044, -73.98180484771729));
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
		map.moveCamera(center);
		map.animateCamera(zoom);
		*/
		
		
		//Other method for location and zoom in:
		CameraPosition cameraPosition = new CameraPosition.Builder()
	    .target(new LatLng(40.76793169992044, -73.98180484771729))      // Sets the center of the map to Mountain View
	    .zoom(17)                   // Sets the zoom
	    .bearing(0)                // Sets the orientation of the camera to North
	    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
	    .build();                   // Creates a CameraPosition from the builder
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	

	//Initialize Variables
	private void Initialize(){
		//Shared Preferences Stuff
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
	}
	
	//On Click Method
	@Override
	public void onClick(View arg0) {
		/*
		switch (arg0.getId()){
		
		case R.id.button_ID_That_Was_Clicked:
			
			break;
			
		case R.id.button_ID_That_Was_Clicked:
			
			break;
			
		}
		*/
	}
	
	@Override
	protected void onPause() {

		super.onPause();
		finish();
	}

}
