package com.example.flashlightexample;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.IBinder;

public class KonumDinlemeServis extends Service implements LocationListener {
	private final Context mContext;
	 boolean isGPSEnabled = false;//private yi sil
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;

	Location location;
	double latitude;
	double longitude;
	// EN AZ KAÇ METRE MESAFE DEÐÝÞTÝÐÝNDE KONUMUNU GÜNCELLEMESÝ ÝÇÝN
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	// EN AZ MÝLÝSANÝYE CÝNSÝNDEN SÜRE GEÇTÝKTEN SONRA GÜNCELLEMESÝ ÝÇÝN
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

	// YER TÖNETÝCÝSÝ BÝLDÝRME
	protected LocationManager locationManager;

	public KonumDinlemeServis(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
getLocation();//bunu ekleyin
	}

	public Location getLocation() {
		locationManager = (LocationManager) mContext
				.getSystemService(LOCATION_SERVICE);
		isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!isGPSEnabled && !isNetworkEnabled) {

		} else {
			this.canGetLocation = true;
			if (isNetworkEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
if(locationManager!=null){
	location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	
}
if(location!=null){
	latitude=location.getLatitude();
	longitude=location.getLongitude();
}
			}
		}
if(isGPSEnabled){
	if(location==null){
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		if(locationManager!=null){
			location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location!=null){
				latitude=location.getLatitude();
				longitude=location.getLongitude();
			}
		}
	}
}
		return location;

	}
	
	public  void stopUsingGPS() {
		// TODO Auto-generated method stub
if(locationManager!=null){
	locationManager.removeUpdates(KonumDinlemeServis.this);
}
	}
	


	public double getLatitude() {
		if(location!=null){
			latitude=location.getLatitude();
		}
		return latitude;
	}

	public double getLongitude() {
		if(location!=null){
			longitude=location.getLongitude();
		}
		return longitude;
		
	}
	public boolean canGetLocation(){
		return this.canGetLocation;
	}
	public void showSettingsAlertDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
		
		alertDialog.setTitle("GPS is settings");
		
		alertDialog.setMessage("GPS is not enable.Do you want to go to setting menu?");
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent i=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS	);
				mContext.startActivity(i);
			}
		});
	alertDialog.setNegativeButton("Cansel",  new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
		dialog.cancel()	;	
		}
	});
	
	alertDialog.show();
		
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
