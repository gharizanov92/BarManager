package bg.foosoft.project.model;

import bg.foosoft.project.util.Roles;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by Kayne on 14-1-13.
 */
@Entity("users")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class User implements Comparable<User> {

    @Id
    private String mId;

    private String mRoleLabel;

    @Property("username")
    private String mUsername;

    @Property("firstName")
    private String mFirstName;

    @Property("lastName")
    private String mLastName;

    @Property("email")
    private String mEmail;

    @Property("password")
    private String mPassword;

    @Property("lang_key")
    private String langKey;

    @Property("role")
    private String mRole;

    @Embedded("order")
    private String mTakenOrder = "";

    public String getRoleLabel(){
        if(getRole() == null){
            return "";
        }
        if(getRole().equals(Roles.BARTENDER)){
            return Roles.BARTENDER_LABEL;
        }
        if(getRole().equals(Roles.WAITER)){
            return Roles.WAITER_LABEL;
        }
        return Roles.MANAGER_LABEL;
    }

    public void setRoleLabel(String aRoleLabel){

    }

    public User() {
    }

    @JsonIgnore
    public boolean isManager(){
        return mRole.equals(Roles.MANAGER);
    }

    public String getId() {
        return mId;
    }

    public void setId(String aId) {
        mId = aId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String aUsername) {
        mUsername = aUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String aPassword) {
        mPassword = aPassword;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String aLangKey) {
        langKey = aLangKey;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        this.mRole = role;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getTakenOrder() {
        return mTakenOrder;
    }

    public void setTakenOrder(String takenOrder) {
        mTakenOrder = takenOrder;
    }

    @Override
    public int compareTo(User other) {
        //TODO: implement
        return 0;
    }


}
