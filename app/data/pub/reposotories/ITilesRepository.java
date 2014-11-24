package data.pub.reposotories;

import data.pub.entities.tiles.ITile;

import java.util.List;

/**
 * Created by tzachit on 20/11/14.
 */
public interface ITilesRepository {
    ITile findById(String workspaceId, String tileId);
    List<ITile> findByWorkspace(String workspaceId);
    boolean addFile(String workspaceId, String path, int position);
    boolean addWebPage(String workspaceId, String url, int position);
    boolean addWorkspaceDescriptor(String workspaceId, int position);
    boolean addMap(String workspaceId, String name, int position);
    boolean update(String workspaceId, ITile tile);
    boolean remove(String workspaceId, String tileId);
}
