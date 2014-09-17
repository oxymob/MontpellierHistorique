package fr.oxymob.montpellier.historique;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.wikitude.architect.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView;

import fr.oxymob.montpellier.historique.activities.ADetail;
import fr.oxymob.montpellier.historique.pojos.Position;
import fr.oxymob.montpellier.historique.utils.DatasHelper;

/**
 * 
 * @author Wikitude
 * @date   JAN 2012
 * 
 * @class SimpleARBrowserActivity
 * 
 *	sample application to show how to use the ARchitect SDK
 * 	loads simple pois via javascript into the ARchitect world and displays them accordingly
 *  displays a bubble with information about the selected poi on the screen and displays a detail page when the bubble is clicked
 *  uses Android's LocationManager to get updates on the user's location
 * 
 *  important is that the methods of the activity lifecycle are forwarded to the ArchitectView
 *  Important methods:  	onPostCreate()
 * 							onResume()
 *							onPause()
 * 							onDestroy()
 * 							onLowMemory()	
 * 
 * 	Please also have a look at the application's Manifest and layout xml-file to see the permissions and requirements 
 * 	an activity using the SDK has to possess. (REF: ARchitect Documentation)		  	  
 */
public class ASimpleARBrowser extends Activity implements ArchitectUrlListener, LocationListener{

	private static final String TAG = ASimpleARBrowser.class.getSimpleName();

	/*private final static float  TEST_LATITUDE =  43.61f;
	private final static float  TEST_LONGITUDE = 3.88f;
	private final static float 	TEST_ALTITUDE = 150;*/

	private String apiKey = "Q6OL016fcw6pVzha7gBKMj/Ht5PMl7bgc9wa6/r4ZmUdkJpuuHzMNHz2qsRB9XQ0c135w6CDFXL1JsDVKg02oSJK1hLQtCh3uJ2hwVl72UesvnfqslO4L9uSyGmVm+BebXmpDC0RhRjTUppgmCaear96Io0TH1u6VsgA4062gNtTYWx0ZWRfXzCE2hxY1ktOwnfAz3zDMg7bbwO8UkBk5tLg/YZtETsdRwlJDmDjhZusyejqLHmMnydH6jloFISoGAizGodNifyzG/kE3njZWXfllQqfm0qsZ2ICwXxewc/q9oAEqmy6xPA6ECiZ9huLhixNTHF+jbL1C1mOox/PAooelxZIj9/E8pi/ZLVKGrBHLZsWY1UWquHNUPAkyLwuo/Bd5MCbJ3GrY7Cr1m1JSfzUEcNI4S+W2MLSXAO2DVygb3YsuiSOFMhTWU77UgVT9rURl1nZTU58pDzGcBgtF4wtFL1BQX+GT5vmQ5V0zr87Hu+qTuaMajl5grSGRdCTWvGxvMLkCAwILUTPUjLzMDHMYXKOkMFLELTw7pOqm0V5SjoMhCBwYjB/+/FzPnyG7IP+ojGcm8JTFZ1QCUaZ1yl2oEeui9KIXf2Pm6XlhhZxdhL34qYS9XfTmy8Jlh86Yu9iGHNurlkHtWlBt12z1w==";

	private ArchitectView architectView;
	private LocationManager locManager;

	private Location loc;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

		if(!ArchitectView.isDeviceSupported(this)) {
			Toast.makeText(this, "minimum requirements not fulfilled", Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}
		setContentView(R.layout.activity_ar);

		//set the devices' volume control to music to be able to change the volume of possible soundfiles to play
		this.setVolumeControlStream( AudioManager.STREAM_MUSIC );
		this.architectView = (ArchitectView) this.findViewById(R.id.architectView);
		//onCreate method for setting the license key for the SDK
		architectView.onCreate(apiKey);
		
		locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, this);
		loc = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		//IMPORTANT: creates ARchitect core modules
		if(this.architectView != null)
			this.architectView.onPostCreate();

		//register this activity as handler of "architectsdk://" urls
		this.architectView.registerUrlListener(this);

		try {
			loadWorld();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.architectView.onResume();
		if (loc != null)
			this.architectView.setLocation((float)(loc.getLatitude()), (float)(loc.getLongitude()), loc.getAccuracy());
		architectView.setCullingDistance(1050.0f);
		//this.architectView.setLocation(TEST_LATITUDE, TEST_LONGITUDE, TEST_ALTITUDE,1f);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if(this.architectView != null)
			this.architectView.onPause();
		locManager.removeUpdates(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if(this.architectView != null)
			this.architectView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

		if(this.architectView != null)
			this.architectView.onLowMemory();
	}

	/**
	 * <p>
	 * interface method of {@link ArchitectUrlListener} class
	 * called when an url with host "architectsdk://" is discovered
	 * 
	 * can be parsed and allows to react to events triggered in the ARchitect World
	 * </p>
	 */
	@Override
	public boolean urlWasInvoked(String url) {
		//parsing the retrieved url string
		List<NameValuePair> queryParams = URLEncodedUtils.parse(URI.create(url), "UTF-8");

		String id = "";
		// getting the values of the contained GET-parameters
		for(NameValuePair pair : queryParams) {
			if(pair.getName().equals("id"))	{
				id = pair.getValue();
			}
		}

		Intent intent = new Intent();
		intent.setClass(this, ADetail.class);
		intent.putExtra("EXTRA_ID", id);
		startActivity(intent);		
		finish();
		return true;
	}

	/**
	 * loads a sample architect world and
	 * creates a definable amount of pois in beancontainers 
	 * and converts them into a jsonstring that can be sent to the framework
	 * @throws java.io.IOException exception thrown while loading an Architect world
	 */
	private void loadWorld() throws IOException {
		this.architectView.load("tutorial1.html");

        DatasHelper datasHelper = new DatasHelper(this);
		JSONArray array = new JSONArray();

		try {
			for (Position poi : datasHelper.getAllPosition()) {
				array.put(poi.toJSONObject());
			}
			this.architectView.callJavascript("newData(" + array.toString() + ");");
		} catch (JSONException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * listener method called when the location of the user has changed
	 * used for informing the ArchitectView about a new location of the user
	 */
	@Override
	public void onLocationChanged(Location loc) {
		if(this.architectView != null)
			this.architectView.setLocation((float)(loc.getLatitude()), (float)(loc.getLongitude()), loc.getAccuracy());
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}