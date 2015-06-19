package com.pointrestapp.pointrest;

import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceTransitionsIntentService extends IntentService {

	Context c;
	public GeofenceTransitionsIntentService() {
		super("");
		// TODO Auto-generated constructor stub
	}

	protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = "errorrrrrre";
            Log.e("errrr", errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
        		
            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
        	
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            for (Geofence fence : triggeringGeofences) {
            	new LocalNotification(getApplicationContext(),
            			Integer.parseInt(fence.getRequestId())).execute();
			}
            //Toast.makeText(getApplicationContext(), "geofenceserviceintentcalling", Toast.LENGTH_LONG);
        } else {
            // Log the error.
        }
    }

}