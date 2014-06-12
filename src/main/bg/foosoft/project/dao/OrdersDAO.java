package bg.foosoft.project.dao;

import bg.foosoft.project.model.MenuItem;
import bg.foosoft.project.model.Order;
import bg.foosoft.project.util.UserInfo;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gharizanov on 10.6.2014 г..
 */
public class OrdersDAO extends BasicDAO<Order, String> {

    @Autowired
    private UserInfo mUserInfo;

    @Autowired
    private UserDAO mUserDAO;

    protected OrdersDAO(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    //public boolean addOrder(Order aNewOrder, String itemID) {
    public boolean addOrder(List<MenuItem> aItems) {
        try {
            Order order = new Order();
            order.setId(new ObjectId().toString());
            order.setWaiter(mUserInfo.getUserEntity());

            for(MenuItem item : aItems){
                if(item.getQuantity() != 0){
                    order.addItem(item);
                }
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

            for(Order order : result){
                if(order.getStatus() != Order.STATUS_WAITING){
                    if(order.isExpired()){
                        order.setStatus(Order.STATUS_OVERDUE);
                    }
                }
            }

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

    public boolean updateOrderStatus(String id, Integer status){

        if(status != Order.STATUS_OVERDUE &&
                status != Order.STATUS_TAKEN &&
                status != Order.STATUS_FINISHED &&
                status != Order.STATUS_WAITING){
            return false;
        }

        Query<Order> updateQuery = ds.createQuery(Order.class).field("_id").equal(id);
        UpdateOperations<Order> ops = null;
        try {
            ops = ds.createUpdateOperations(Order.class).set("status",status);
            ds.update(updateQuery, ops);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean setOrderTaken(String id) {
        Query<Order> updateQuery = ds.createQuery(Order.class).field("_id").equal(id);
        UpdateOperations<Order> ops = null;
        Order order = new Order();

        //we don't want to embed these fields
        order.setWaiter(null);
        order.updateDeadline();
        try {
            ops = ds.createUpdateOperations(Order.class).set("status",Order.STATUS_TAKEN)
                    .set("deadline", order.getDeadline());
            ds.update(updateQuery, ops);

            mUserDAO.takeOrder(mUserInfo.getId(), id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Order getTakenOrder() {
        String orderID = mUserDAO.findUserByID(mUserInfo.getId()).getTakenOrder();
        if(orderID == ""){
            return null;
        }
        Order result = ds.get(Order.class, orderID);
        return result;
    }
}
