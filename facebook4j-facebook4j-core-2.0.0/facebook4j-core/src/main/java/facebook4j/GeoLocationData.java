package facebook4j;

import facebook4j.internal.org.json.JSONObject;

public class GeoLocationData {
	public double latitude;
	public double longitude;
	public JSONObject json;

	public GeoLocationData(JSONObject json) {
		this.json = json;
	}
}