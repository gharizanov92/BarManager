package bg.foosoft.project.dao;

import bg.foosoft.project.model.Order;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.PasswordHash;
import bg.foosoft.project.util.Roles;
import bg.foosoft.project.util.UserInfo;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by Kayne on 14-1-13.
 */
public class UserDAO extends BasicDAO<User, String> {

    public static String USERNAME = "manager";
    public static String PASSWORD = "manager";

    @Autowired
    private OrdersDAO mOrderDAO;

    public UserDAO(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    public List<User> findAll(){
        return ds.find(User.class).asList();
    }

    public User findUserByUsername(String username){
        return ds.find(User.class).field("username").equal(username).get();
    }

    public List<User> findUsersByRole(String role){
        return ds.find(User.class).field("role").equal(role).asList();
    }

    public User findUserByID(String id){
        return ds.get(User.class, id);
    }

    public boolean registerUser(User aUser){

        if(aUser == null || aUser.getUsername() == null){
            return false;
        }

        if(findUserByUsername(aUser.getUsername()) != null ||
                (aUser.getId() != null && findUserByID(aUser.getId()) != null)){
            updateUser(aUser);
            return true;
        }

        try {
            aUser.setPassword(PasswordHash.generateStorngPasswordHash(aUser.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        aUser.setId(new ObjectId().toString());
        ds.save(aUser);
        return true;
    }

    public void updateUser(User aNewUser){
        Query<User> updateQuery = ds.createQuery(User.class).field("_id").equal(aNewUser.getId());
        UpdateOperations<User> ops = null;
        try {
            ops = ds.createUpdateOperations(User.class).set("username",aNewUser.getUsername())
                    .set("password", PasswordHash.generateStorngPasswordHash(aNewUser.getPassword()))
                    .set("firstName", aNewUser.getFirstName())
                    .set("lastName", aNewUser.getLastName())
                    .set("email", aNewUser.getEmail())
                    .set("role", aNewUser.getRole() == null ? Roles.BARTENDER : aNewUser.getRole());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        ds.update(updateQuery, ops);
    }

    public void changePassword(String userID, String password) {
        try {
            password = PasswordHash.generateStorngPasswordHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        Query query = ds.createQuery(User.class).field("_id").equal(userID);
        UpdateOperations<User> operations = ds.createUpdateOperations(User.class).set("password", password);
        ds.update(query, operations);
    }

    public void createManagerAccount(){

        User manager = new User();
        manager.setUsername(USERNAME);
        manager.setPassword(PASSWORD);
        manager.setRole(Roles.MANAGER);

        if(findAll().size() != 0) {

            if (findUserByUsername(manager.getUsername()) != null) {
                return;
            }

            if (findUsersByRole(manager.getRole()).size() != 0) {
                return;
            }

        }

        registerUser(manager);
    }

    public void takeOrder(String userID, String orderID){
        Query<User> updateQuery = ds.createQuery(User.class).field("_id").equal(userID);
        UpdateOperations<User> ops = null;
        ops = ds.createUpdateOperations(User.class).set("order", orderID);
        ds.update(updateQuery, ops);
    }

    public void cancelOrder(String userID, String orderID){
        Query<User> updateQuery = ds.createQuery(User.class).field("_id").equal(userID);
        UpdateOperations<User> ops = null;
        ops = ds.createUpdateOperations(User.class).set("order", "");
        ds.update(updateQuery, ops);

        mOrderDAO.updateOrderStatus(orderID, Order.STATUS_WAITING);
    }

    public void removeUser(String id) {
        ds.delete(User.class, id);
    }
}
