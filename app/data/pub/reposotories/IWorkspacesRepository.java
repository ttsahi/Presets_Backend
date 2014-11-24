package data.pub.reposotories;

import data.pub.entities.IWorkspace;

import java.util.Date;
import java.util.List;

/**
 * Created by tzachit on 19/11/14.
 */
public interface IWorkspacesRepository {
    List<IWorkspace> findAll();
    IWorkspace findById(String id);
    String add(String name, String description, Date expired);
    boolean updateName(String id, String name);
    boolean updateDescription(String id, String name);
    boolean update(String id, String name, String description);
    boolean remove(String id);
    long count();
}
