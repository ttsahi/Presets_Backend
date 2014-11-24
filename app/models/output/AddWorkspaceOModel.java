package models.output;

/**
 * Created by tzachit on 24/11/14.
 */
public class AddWorkspaceOModel {

    private String id;

    public AddWorkspaceOModel(){}

    public AddWorkspaceOModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
