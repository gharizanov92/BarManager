package bg.foosoft.project.dao;

import bg.foosoft.project.model.MenuItem;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.PasswordHash;
import bg.foosoft.project.util.Roles;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Created by gharizanov on 10.6.2014 г..
 */
public class ItemsDAO extends BasicDAO<MenuItem, String> {

    protected ItemsDAO(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    public boolean addItem(MenuItem aNewItem) {

        if(aNewItem == null){
            return false;
        }

        if(ds.get(MenuItem.class, aNewItem.getId()) != null){
            return updateItem(aNewItem);
        }

        try {
            aNewItem.setId(new ObjectId().toString());
            ds.save(aNewItem);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean updateItem(MenuItem aItem) {
        Query<User> updateQuery = ds.createQuery(User.class).field("_id").equal(aItem.getId());
        UpdateOperations<User> ops = null;
        try {
            ops = ds.createUpdateOperations(User.class).set("label",aItem.getLabel())
                    .set("price", aItem.getPrice());
            ds.update(updateQuery, ops);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeItem(String id) {

        ds.delete(MenuItem.class, id);/*
        try {
            ds.delete(MenuItem.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
        return true;
    }

    public List<MenuItem> findAll(){
        List<MenuItem> result = null;

        try {
            result = ds.find(MenuItem.class).asList();

            for(MenuItem item : result){
                item.setQuantity(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public MenuItem findItemByID(String id) {

        MenuItem result = null;
        result = ds.get(MenuItem.class, id);

        return result;
    }

    public MenuItem findItemByLabel(String label) {

        MenuItem result = null;
        result = ds.get(MenuItem.class, label);

        return result;
    }
}
