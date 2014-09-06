package fr.oxymob.montpellier.historique;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyLocationManager extends Observable implements LocationListener {
	LocationManager mlocManager ;

	private double lg;
	private double lat;
	private static MyLocationManager instance;
	public Geocoder geoCoder;
	private boolean manually;
	List<String> providers;

	public static MyLocationManager getInstance() {
		if (null == instance) { 
			instance = new MyLocationManager();
		}
		return instance;
	}

	private MyLocationManager() {
		geoCoder = new Geocoder(MontpellierHistorique.getInstance(), Locale.FRANCE);
		mlocManager = (LocationManager) MontpellierHistorique.getInstance().getSystemService(Context.LOCATION_SERVICE);
		providers = mlocManager.getAllProviders();
		mlocManager.requestLocationUpdates(providers.get(0), 0, 0, this);
		forceUpdateLocation();
		//Log.e("MyLocationManager", "MyLocationManager()");
	}

	private void forceUpdateLocation() {
		Location location = null;
		Iterator<String> i = providers.iterator();
		while(i.hasNext()){
			String provider = (String) i.next();
			location = mlocManager.getLastKnownLocation(provider);
			if (location!=null)
				break;
		}

		if (location != null) {
			setLat(location.getLatitude());
			setLg(location.getLongitude());
		}
	}

	public void reset() {
		//Log.e("MyLocationManager", "reset ");
		manually = false;
		forceUpdateLocation();
	}

	@Override
	public void addObserver(Observer obs) {
		super.addObserver(obs);
		//Log.d("Mark&Go", "Observer ajout n = " + countObservers());
	}

	@Override
	public void onLocationChanged(Location loc)	{
		//Log.e("MyLocationManager", "onLocationChanged " + loc);
		if (manually)
			return;
		setLat(loc.getLatitude());
		setLg(loc.getLongitude());
		notifyObservers();
	}

	@Override
	public void onProviderDisabled(String provider)	{
		//Log.e("MyLocationManager", "onProviderDisabled " + provider);
	}


	@Override
	public void onProviderEnabled(String provider)	{
		//Log.e("MyLocationManager", "onProviderEnabled " + provider);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//Log.d("MyLocationManager", "onStatusChanged " + provider + "status : " + status);
	}

	public void setLg(double lg) {
		this.lg = lg;
	}

	public double getLg() {
		return lg;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return lat;
	}

}

/*Criteria crit = new Criteria();
crit.setAccuracy(Criteria.ACCURACY_FINE);
String provider = mlocManager.getBestProvider(crit, true*/



/*List<Address> addr = null;
try {
	addr = geoCoder.getFromLocation(lat, lg, 1);
} catch (IOException e) {
	e.printStackTrace();
}
if (addr!=null) {
	currentCity = addr.get(0).getLocality();
}*/
