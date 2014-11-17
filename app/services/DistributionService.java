package services;

import akka.actor.Cancellable;
import data.entities.ITrack;
import play.libs.Akka;
import play.libs.Json;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.google.inject.Inject;

import data.ITracksRepository;

/**
 * Created by tzachit on 17/11/14.
 */

public class DistributionService implements IDistributionService {

    private boolean active = false;
    private Cancellable cancelObject;
    private final ISocketsManager _socketsManager;
    private final ITracksRepository _tracksRepository;
    private final ILocationGenerator _locationGenerator;

    public static int CHUNKS_SIZE = 50;

    @Inject
    public DistributionService(ISocketsManager socketsManager,
                               ITracksRepository tracksRepository,
                               ILocationGenerator locationGenerator)
    {
        this._socketsManager = socketsManager;
        this._tracksRepository = tracksRepository;
        this._locationGenerator = locationGenerator;
    }

    @Override
    public synchronized void start() {

        if(active) return;

        active = true;

        cancelObject = Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS),
                Duration.create(500, TimeUnit.MILLISECONDS),
                (Runnable) () -> {

                    Iterator<ITrack> tracks = this._tracksRepository.getAll().iterator();
                    List<ITrack> chunk = new ArrayList<>(CHUNKS_SIZE);

                    ITrack track = null;
                    while ((track = tracks.next()) != null){
                        track.setLocation(this._locationGenerator.generate());
                        chunk.add(track);

                        if(chunk.size() == CHUNKS_SIZE){
                            this._socketsManager.notifyAll(Json.toJson(chunk));
                            chunk.clear();
                        }
                    }

                    if(chunk.size() > 0){
                        this._socketsManager.notifyAll(Json.toJson(chunk));
                        chunk.clear();
                    }
                },
                Akka.system().dispatcher()
        );
    }

    @Override
    public synchronized void stop() {

        if(!active) return;

        active = false;

        cancelObject.cancel();
    }
}
