package services;

import data.entities.ILocation;
import data.entities.Location;

import java.util.Random;

/**
 * Created by tzachit on 17/11/14.
 */
public class LocationGenerator implements ILocationGenerator {

    private Random _random;

    public LocationGenerator(){
        this._random = new Random();
    }

    @Override
    public ILocation generate() {
        return new Location(
                (-90) + 180 * this._random.nextDouble(),
                (-180) + 360 * this._random.nextDouble()
        );
    }
}
