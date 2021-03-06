/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package facebook4j;

import facebook4j.internal.org.json.JSONObject;

/**
 * A data class representing geo location.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @author Ryuji Yamashita - roundrop at gmail.com
 * <ul>
 * <li>Added {@link GeoLocation#asParameterString() asParameterString()} method</li>
 * <li>Added {@link GeoLocation#asJSONObject() asJSONObject()} method</li>
 * <li>Added {@link GeoLocation#asJSONString() asJSONString()} method</li>
 * </ul>
 */
public class GeoLocation implements java.io.Serializable {

    private static final long serialVersionUID = -4847567157651889935L;

    /**
     * Creates a GeoLocation instance
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    public GeoLocation(double latitude, double longitude) {
        this.data.latitude = latitude;
        this.data.longitude = longitude;
    }
    
    /* For serialization purposes only. */
    /* package */ GeoLocation() {
    	
    }

    /**
     * returns the latitude of the geo location
     *
     * @return the latitude
     */
    public double getLatitude() {
        return data.latitude;
    }

    /**
     * returns the longitude of the geo location
     *
     * @return the longitude
     */
    public double getLongitude() {
        return data.longitude;
    }
    
    public String asParameterString() {
        return data.latitude + "," + data.longitude;
    }

    protected GeoLocationData data = new GeoLocationData(null);

	public JSONObject asJSONObject() {
        if (data.json == null) {
            data.json = new JSONObject(this);
        }
        return data.json;
    }
    
    public String asJSONString() {
        return asJSONObject().toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoLocation)) return false;

        GeoLocation that = (GeoLocation) o;

        if (Double.compare(that.getLatitude(), data.latitude) != 0) return false;
        if (Double.compare(that.getLongitude(), data.longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = data.latitude != +0.0d ? Double.doubleToLongBits(data.latitude) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = data.longitude != +0.0d ? Double.doubleToLongBits(data.longitude) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "latitude=" + data.latitude +
                ", longitude=" + data.longitude +
                '}';
    }
}
