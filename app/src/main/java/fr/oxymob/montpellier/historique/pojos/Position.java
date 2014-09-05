package fr.oxymob.montpellier.historique.pojos;

import org.json.JSONException;
import org.json.JSONObject;
import android.net.Uri;

public class Position implements Comparable<Position>{
	public String fid; 						// r�f�rence
	public String monument;					// nom du Monument
	public double lat;						// latitude
	public double lg;						// longitude
	public String desc;						// contenu �ditorial - description
	public String adresse;					// adresse du monument (ou POI)
	public double distance;
	public String epoque = "";
	private double currentLat, currentLg;  
	public Uri imageURI;

	private Point point;

	private class Point
	{
		private double latitude;
		private double longitude;
		private double altitude;

		public Point(double lat, double lon) {
			this.latitude = lat;
			this.longitude = lon;
			this.altitude = 50.0001d;
		}

		public JSONObject toJSONString() throws JSONException {
			JSONObject object = new JSONObject();
			object.put("latitude", this.latitude);
			object.put("longitude", this.longitude);
			object.put("altitude", this.altitude);
			return object;
		}
	}

	public static double HaverSineDistance(double lat1, double lng1, double lat2, double lng2) {
	    lat1 = Math.toRadians(lat1);
	    lng1 = Math.toRadians(lng1);
	    lat2 = Math.toRadians(lat2);
	    lng2 = Math.toRadians(lng2);

	    double dlon = lng2 - lng1;
	    double dlat = lat2 - lat1;

	    double a = Math.pow((Math.sin(dlat/2)),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	    return 6368000.0 * c;
	}   
	
	public void init_distance(double mylat, double mylg) {
		currentLat = mylat;
		currentLg = mylg;
		distance = HaverSineDistance(currentLat, currentLg, lat, lg);
		this.point = new Point(lat, lg);
	}

	@Override
	public int compareTo(Position another) {
		return (int) (this.distance - another.distance);
	}

	public JSONObject toJSONObject() throws JSONException	{
		JSONObject object = new JSONObject();
		object.put("id", this.fid);
		object.put("name", this.monument);
		object.put("description", this.desc);
		object.put("type", 1);
		object.put("Point", this.point.toJSONString());
		return object;
	}

	public String toString() {
		String ret = "";

		ret = fid + "\n";
		ret = monument + "\n";
		ret = desc + "\n";
		ret = adresse + "\n";

		return ret;
	}
	
	public String getStrDistance() {
		String strDistance="";
		if (distance!=0) {
			if (distance > 5000)
				strDistance = String.format("%5.1f km", distance/1000);
			else
				strDistance = String.format("%4.0f mètres", distance);
		}
		return strDistance;
	}


}
