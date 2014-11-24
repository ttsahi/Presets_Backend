package data.prv.repositories;

import com.google.inject.Inject;
import data.prv.context.IPresetsDbContext;
import data.prv.entities.Workspace;
import data.prv.entities.tiles.File;
import data.prv.entities.tiles.Map;
import data.prv.entities.tiles.WebPage;
import data.prv.entities.tiles.WorkspaceDescriptor;
import data.pub.entities.tiles.ITile;
import data.pub.reposotories.ITilesRepository;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzachit on 20/11/14.
 */
public class TilesRepository implements ITilesRepository {

    private final MongoCollection collection;

    @Inject
    public TilesRepository(IPresetsDbContext context){
        this.collection = context.getCollection("workspaces");
    }

    @Override
    public ITile findById(String workspaceId, String tileId) {

        Workspace workspace = this.collection
                .findOne("{_id: #}, {tiles: {$elemMatch: {id: #}}}", new ObjectId(workspaceId), new ObjectId(tileId))
                .as(Workspace.class);

        if(workspace == null || workspace.getTiles() == null){
            return null;
        }

        return workspace.getTiles().get(0);
    }

    @Override
    public List<ITile> findByWorkspace(String workspaceId) {

        Workspace workspace = this.collection
                .findOne("{_id: #}", new ObjectId(workspaceId))
                .as(Workspace.class);

        if(workspace == null || workspace.getTiles() == null){
            return null;
        }

        List<ITile> tiles = new ArrayList<>();
        workspace.getTiles().forEach(tiles::add);
        return tiles;
    }

    @Override
    public String addFile(String workspaceId, String path, int position) {

        if(position > Workspace.MAX_TILES){
            return null;
        }

        File file = new File(path, position);

        return this.collection
                .update("{_id: #, 'tiles.position': {$ne: #}}", new ObjectId(workspaceId), position)
                .with("{$push: {tiles: #}}", file)
                .getN() != 0 ? file.getId() : null;
    }

    @Override
    public String addWebPage(String workspaceId, String url, int position) {

        if(position > Workspace.MAX_TILES){
            return null;
        }

        WebPage webPage = new WebPage(url, position);

        return this.collection
                .update("{_id: #, 'tiles.position': {$ne: #}}", new ObjectId(workspaceId), position)
                .with("{$push: {tiles: #}}",  webPage)
                .getN() != 0 ? webPage.getId() : null;
    }

    @Override
    public String addWorkspaceDescriptor(String workspaceId, int position) {

        Workspace workspace = this.collection
                .findOne("{_id: #}", new ObjectId(workspaceId))
                .as(Workspace.class);

        if(workspace == null || position > Workspace.MAX_TILES){
            return null;
        }

        WorkspaceDescriptor workspaceDescriptor = new WorkspaceDescriptor(
                workspace.getName(),
                workspace.getDescription(),
                workspace.getModified(),
                workspace.getExpired(),
                position
        );

        return this.collection
                .update("{_id: #, 'tiles.position': {$ne: #}}", new ObjectId(workspaceId), position)
                .with("{$push: {tiles: #}}", workspaceDescriptor)
                .getN() != 0 ? workspaceDescriptor.getId() : null;
    }

    @Override
    public String addMap(String workspaceId, String name, int position) {

        if(position > Workspace.MAX_TILES){
            return null;
        }

        Map map = new Map(name, position);

        return this.collection
                .update("{_id: #, 'tiles.position': {$ne: #}}", new ObjectId(workspaceId), position)
                .with("{$push: {tiles: #}}",  map)
                .getN() != 0 ? map.getId() : null;
    }

    @Override
    public boolean update(String workspaceId, ITile tile) {

        Workspace workspace = this.collection
                .findOne("{_id: #}", new ObjectId(workspaceId))
                .as(Workspace.class);

        if(workspace == null || workspace.getTiles() == null){
            return false;
        }

        ITile t = workspace.getTiles().get(0);

        if(t.getPosition() != tile.getPosition()){

            if(tile.getPosition() > Workspace.MAX_TILES){
                return false;
            }

            return this.collection
                    .update("{_id: #, 'tiles.id': #, 'tiles.position': {$ne: #}}",
                            new ObjectId(workspaceId), new ObjectId(tile.getId()), tile.getPosition())
                    .with("{$set: {'tiles.$': #}}", tile)
                    .getN() != 0;
        }

        return this.collection
                .update("{_id: #, 'tiles.id': #}", new ObjectId(workspaceId), new ObjectId(tile.getId()))
                .with("{$set: {'tiles.$': #}}", tile)
                .getN() != 0;
    }

    @Override
    public boolean remove(String workspaceId, String tileId) {

        return this.collection
                .update("{_id: #", new ObjectId(workspaceId))
                .with("{$pull: {tiles: {id: #}}}", new ObjectId(tileId))
                .getN() != 0;
    }
}
