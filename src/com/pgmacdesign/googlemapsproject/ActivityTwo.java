package com.pgmacdesign.googlemapsproject;

import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ActivityTwo extends FragmentActivity {

	//Global Variables
	GoogleMap map;
	
	String address;
	double startLat, startLon;
	static double endLat;
	static double endLng;
	
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
		
		//Get Current Location and set it to center of map
		GetCurrentLocation();

		
		
		
		//Popup a dialog after 5 seconds with option to start directions
		Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	        	 NavigationPopup(); 
	         } 
	    }, 5000);
	    
		
		
	}
	
	

	//Pops up an indicator that allows people to use the navigation8
	private void NavigationPopup() {
		
		//If the address was entered correctly, pop up a dialog window
		if (address.equals("")){
			//Nothing to see here... move along...
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTwo.this);
			builder.setCancelable(true);
			builder.setTitle("Navigation?");
			builder.setMessage("Now that you can see where you are currently, would you like driving directions"
					+ " to the address that you entered?");
			builder.setInverseBackgroundForced(true);
			builder.setPositiveButton("Dismiss",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                int which) {
                            dialog.dismiss();
                        }
                    });
			builder.setNegativeButton("Show Me", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
				        dialog.cancel();
				        Intent navigation = new Intent(
				                Intent.ACTION_VIEW,
				                Uri.parse("http://maps.google.com/maps?saddr="+startLat+","+startLon+"&daddr="+endLat+","+endLng));
				        startActivity(navigation);	
				   }
				});
            AlertDialog alert = builder.create();
            alert.show();	
		}
		
	}



	//Determines the current location via their location and zooms in to it
	private void GetCurrentLocation() {
		double[] d = Getlocation();
		
		startLat = d[0];
		startLon = d[1];
		
		LatLng mapCenter = new LatLng(d[0], d[1]);
		
		//Allows for the cross-hairs button which will zoom into you
		map.setMyLocationEnabled(true);
		
		//Adds an image to center of the location of the person
		map.addMarker(new MarkerOptions()
		//.icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_dot)) //Sets the icon where you are located
        .position(mapCenter) //Positions the dot at the center of the location of the user
        .flat(true)
        .rotation(245));
		
		
		//Other method for location and zoom in:
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(mapCenter)     // Sets the center of the location of the user
	    .zoom(17)                   // Sets the zoom
	    .bearing(0)                // Sets the orientation of the camera to North (90 for east, 180 for south)
	    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
	    .build();                   // Creates a CameraPosition from the builder
		
		//UiSettings.setMyLocationButtonEnabled(true).
		
		//Animate the change in camera view over 1 second
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2500, null);
		
		
		
	}


	//Initialize Variables
	private void Initialize(){
		//Shared Preferences Stuff
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		String part1, part2, part3, part4; 
		part1 = part2 = part3 = part4 = "";
		
		//Ensure the proper fields were entered
		
		//Part1
		if (sp.getString(settings, "address", "528 E Patton Ave").equalsIgnoreCase("")){
			part1 = "528 E Patton Ave";
		} else {
			part1 = sp.getString(settings, "address", "528 E Patton Ave");
		}
		
		//Part2
		if (sp.getString(settings, "city", "Montgomery").equalsIgnoreCase("")){
			part2 = "Montgomery";
		} else {
			part2 = sp.getString(settings, "city", "Montgomery");
		}
		
		//Part3
		if (sp.getString(settings, "state", "Alabama").equalsIgnoreCase("")){
			part3 = "Alabama";
		} else {
			part3 = sp.getString(settings, "state", "Alabama");
		}
		
		//Part4
		if (sp.getString(settings, "zipcode", "36111").equalsIgnoreCase("")){
			part4 = "36111";
		} else {
			part4 = sp.getString(settings, "zipcode", "36111");
		}
		
		address = part1+","+part2+","+part3+","+part4; 
				
		ConvertAddress(address);
	}
	
	//Convert the address string into latitude and longitude points
	public void ConvertAddress(String address) {
    	try {
    		
    		Geocoder geoCoder = new Geocoder(ActivityTwo.this, Locale.getDefault());
    		List<Address> addressList = geoCoder.getFromLocationName(this.address, 1) ;
    		
        	endLat = addressList.get(0).getLatitude();
            endLng = addressList.get(0).getLongitude();
            
        } catch (Exception e) {
            String error = e.toString();
            L.makeToast(this, error);
        } // end catch
        
    } // end if
	
	
	
	//Calculates the current position of the user and returns Latitude/ Longitude via a double array
	public double[] Getlocation() {
	    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    List<String> providers = lm.getProviders(true);

	    Location l = null;
	    for (int i = 0; i < providers.size(); i++) {
	        l = lm.getLastKnownLocation(providers.get(i));
	        if (l != null)
	            break;
	    }
	    double[] gps = new double[2];

	    if (l != null) {
	        gps[0] = l.getLatitude();
	        gps[1] = l.getLongitude();
	    }
	    return gps;
	}
	
	@Override
	protected void onPause() {

		super.onPause();
		finish();
	}

}
