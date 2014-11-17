/**
 * Created by tzachit on 16/11/14.
 */
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import data.ITracksRepository;
import data.TracksRepository;
import data.entities.Track;
import play.Application;
import play.GlobalSettings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import services.*;

public class Global extends GlobalSettings {

    private static final int NUMBER_OF_TRACKS = 20;
    private static final TracksRepository tracksRepository = new TracksRepository(NUMBER_OF_TRACKS);
    private static final SocketsManager socketsManager = new SocketsManager();
    private static final LocationGenerator locationGenerator = new LocationGenerator();
    private static final Injector INJECTOR = createInjector();

    @Override
    public void onStart(Application app) {
        for(int i = 0; i < NUMBER_OF_TRACKS; i++){
            tracksRepository.add(new Track("" + i, "Hey i'm track no#: " + i, locationGenerator.generate()));
        }
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return INJECTOR.getInstance(controllerClass);
    }

    private static Injector createInjector() {
        return Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {

                bind(ITracksRepository.class).toInstance(tracksRepository);
                bind(ISocketsManager.class).toInstance(socketsManager);
                bind(ILocationGenerator.class).toInstance(locationGenerator);

                DistributionService.CHUNKS_SIZE = 50;
                bind(IDistributionService.class).to(DistributionService.class).in(Singleton.class);
            }
        });
    }

}