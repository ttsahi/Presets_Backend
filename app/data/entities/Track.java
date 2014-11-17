package data.entities;

/**
 * Created by tzachit on 17/11/14.
 */
public class Track implements ITrack {

    private String _id;
    private String _status;
    private ILocation _location;

    public Track(){
        this(null, null, null);
    }

    public Track(String id, String status, ILocation location){
        this._id = id;
        this._status = status;
        this._location = location;
    }

    @Override
    public String getId() {
        return this._id;
    }

    @Override
    public void setId(String id) {
        this._id = id;
    }

    @Override
    public String getStatus() {
        return this._status;
    }

    @Override
    public void setStatus(String status) {
        this._status = status;
    }

    @Override
    public ILocation getLocation() {
        return this._location;
    }

    @Override
    public void setLocation(ILocation location) {
        this._location = location;
    }
}
