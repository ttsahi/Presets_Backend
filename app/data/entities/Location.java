package data.entities;

/**
 * Created by tzachit on 17/11/14.
 */
public class Location implements ILocation {

    private double _lat;
    private double _lon;

    public Location(){
        this(0,0);
    }

    public Location(double lat, double lon){
        this._lat = lat;
        this._lon = lon;
    }

    @Override
    public double getLat() {
        return this._lat;
    }

    @Override
    public boolean setLat(double lat) {
        if(lat > 90 || lat < (-90)){
            return false;
        }

        this._lat = lat;
        return true;
    }

    @Override
    public double getLon() {
        return this._lon;
    }

    @Override
    public boolean setLon(double lon) {
        if(lon > 180 || lon < (-180)){
            return false;
        }

        this._lon = lon;
        return true;
    }
}
