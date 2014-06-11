package bg.foosoft.project.dao;

import bg.foosoft.project.model.MenuItem;
import bg.foosoft.project.model.Order;
import bg.foosoft.project.util.UserInfo;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gharizanov on 10.6.2014 г..
 */
public class OrdersDAO extends BasicDAO<Order, String> {

    @Autowired
    private ItemsDAO mItemsDAO;

    @Autowired
    private UserInfo mUserInfo;

    protected OrdersDAO(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    //public boolean addOrder(Order aNewOrder, String itemID) {
    public boolean addOrder(String aTable, List<String> aItems) {
        try {
            Order order = new Order();
            order.setId(new ObjectId().toString());
            order.setWaiter(mUserInfo.getUserEntity());
            order.setTable(aTable);

            for(String itemID : aItems){
                MenuItem item = mItemsDAO.findItemByID(itemID);

                if(item == null){
                    return false;
                }

                order.addItem(item);
            }
            ds.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Order> getAllOrdersForTable(String table){
        return ds.find(Order.class, "table", table).asList();
    }

    private boolean updateItem(Order aItem) {
/*        Query<User> updateQuery = ds.createQuery(User.class).field("_id").equal(aItem.getId());
        UpdateOperations<User> ops = null;
        try {
            ops = ds.createUpdateOperations(User.class).set("label",aItem.getLabel())
                    .set("price", aItem.getPrice());
            ds.update(updateQuery, ops);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
        return true;
    }

    public boolean removeOrder(String id) {

        ds.delete(Order.class, id);
        return true;
    }

    public List<Order> findAll(){
        List<Order> result = null;

        try {
            result = ds.find(Order.class).asList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public Order findOrderByID(String id) {

        Order result = null;
        result = ds.get(Order.class, id);

        return result;
    }
}