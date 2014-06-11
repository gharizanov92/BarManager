package bg.foosoft.project.util;


import bg.foosoft.project.model.User;

public class UserInfo {

    private String id;

    private User mUserEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUserEntity() {
        return mUserEntity;
    }

    public void setUserEntity(User mUserEntity) {
        this.mUserEntity = mUserEntity;
    }

}
