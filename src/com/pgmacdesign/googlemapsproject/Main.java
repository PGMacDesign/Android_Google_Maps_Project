package com.pgmacdesign.googlemapsproject;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Main extends Activity{

	//Global Variables
		EditText address_street_address, address_city, address_zip_code;
		Spinner spinner_state;
	
	//Shared Preferences
		public static final String PREFS_NAME = "MapsData";	
		SharedPrefs sp = new SharedPrefs();
		SharedPreferences settings;
		SharedPreferences.Editor editor;
	
	//On Create
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);
		
		Initialize();
	}

	//Initializes all variables
	private void Initialize() {
		spinner_state = (Spinner) findViewById(R.id.spinner_state);
		address_street_address = (EditText) findViewById(R.id.address_street_address);
		address_city = (EditText) findViewById(R.id.address_city);
		address_zip_code = (EditText) findViewById(R.id.address_zip_code);
		
		//Shared Preferences Stuff
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
	}

	//Opens intent to new activity once submit button is clicked. Also runs calculations.
	public void SubmitInformation(View v){
		//Obtain the data from the text fields and spinners and input it into the Shared Preferences. Thread runs in case more calculations are needed
		Thread setInfo = new Thread (new Runnable(){
			@Override
			public void run(){
				try{
					//Call all 4 functions that calculate data for writing to shared preferences
					SetStreetAddress();
					SetCity();
					SetState();
					SetZipCode();
					
				} catch (Throwable t) {
					//Possibly add toast here for debugging
				}
			}
		});// 
			setInfo.start();
		
		//Switch to second activity via intent
		SwitchAvtivity(v);
	}
	
	//Sets the street address
	private void SetStreetAddress(){
		String streetAddress = "";
		
		//Confirm that the user typed something in the box
		if (address_street_address.getText().toString().equals("")){
			//Nothing because the user forgot to type here
		} else {
			//Get the text from the EditText field and convert it to a String
			streetAddress = address_street_address.getText().toString();
		}
		
		//Input the address into the shared prefs
		sp.putString(editor, "address", streetAddress);
		editor.commit();
		L.m(streetAddress); //For debugging
	}
	
	
	//Sets the street address
	private void SetCity(){
		String city = "";
		
		//Confirm that the user typed something in the box
		if (address_city.getText().toString().equals("")){
			//Nothing because the user forgot to type here
		} else {
			//Get the text from the EditText field and convert it to a String
			city = address_city.getText().toString();
		}
		
		//Input the address into the shared prefs
		sp.putString(editor, "city", city);
		editor.commit();
		L.m(city); //For debugging
	}
	
	
	//Sets the street address
	private void SetState(){
		String state = "";
		
		//Confirm that the user typed something in the box
		if (spinner_state.getSelectedItem().toString().equals("")){
			//Nothing because the user forgot to type here
		} else {
			//Get the text from the EditText field and convert it to a String
			state = spinner_state.getSelectedItem().toString();
		}
		
		//Input the address into the shared prefs
		sp.putString(editor, "state", state);
		editor.commit();
		L.m(state); //For debugging
	}
	
	
	//Sets the street address
	private void SetZipCode(){
		String zipCode = "";
		
		//Confirm that the user typed something in the box
		if (address_zip_code.getText().toString().equals("")){
			//Nothing because the user forgot to type here
		} else {
			//Get the text from the EditText field and convert it to a String
			zipCode = address_zip_code.getText().toString();
		}
		
		//Input the address into the shared prefs
		sp.putString(editor, "address", zipCode);
		editor.commit();
		L.m(zipCode); //For debugging
	}
		

	//Changes to next activity via intent
	private void SwitchAvtivity(View v) {
		
		//View v = getWindow().getDecorView().getRootView();
		/*
		 * 	getWindow().getDecorView().getRootView()
						
			anyview.getRootView();
			
			getWindow().getDecorView().findViewById(android.R.id.content)
		 */
		
		Intent intent = new Intent(v.getContext(), ActivityTwo.class);
        startActivity(intent);
	}
}
