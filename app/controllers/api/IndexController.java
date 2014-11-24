package controllers.api;

import com.google.inject.Inject;
import data.pub.entities.tiles.IFile;
import data.pub.reposotories.ITilesRepository;
import data.pub.reposotories.IWorkspacesRepository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tzachit on 19/11/14.
 */
public class IndexController extends Controller {

    private IWorkspacesRepository workspacesRepo;
    private ITilesRepository tilesRepository;

    @Inject
    public IndexController(IWorkspacesRepository workspacesRepository, ITilesRepository tilesRepository){
        this.workspacesRepo = workspacesRepository;
        this.tilesRepository = tilesRepository;
    }

    public Result index() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 14);
        Date expired = calendar.getTime();
        //this.workspacesRepo.add("Tsahi", "Tsahi tal", expired);

        //this.tilesRepository.addFile("5472f065e47f66275755893a", "C:/a.txt", 3);
        //this.tilesRepository.addMap("5472f065e47f66275755893a", "gggg", 2);

        IFile file = (IFile)this.tilesRepository.findById("5472f065e47f66275755893a","5472f098e47f66275755893b");
        file.setPosition(2);
        file.setPath("D:/b.txt");
        this.tilesRepository.update("5472f065e47f66275755893a", file);

        return ok(Json.toJson(this.workspacesRepo.findAll()));
    }
}
