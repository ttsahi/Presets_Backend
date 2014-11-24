package controllers.api;

import com.google.inject.Inject;
import data.pub.reposotories.ITilesRepository;
import play.mvc.*;

/**
 * Created by tzachit on 24/11/14.
 */
public class TilesController extends Controller {

    private final ITilesRepository tilesRepo;

    @Inject
    public TilesController(ITilesRepository tilesRepository){
        this.tilesRepo = tilesRepository;
    }


}
