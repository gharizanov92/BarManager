package bg.foosoft.project.controller.rest.body;

import bg.foosoft.project.model.MenuItem;

import java.util.List;

/**
 * Created by gharizanov on 11.6.2014 г..
 */
public class OrderRequestBody {

    private String mTable;
    private List<MenuItem> mItems;

    public OrderRequestBody() {
    }

    public String getTable() {
        return mTable;
    }

    public void setTable(String table) {
        mTable = table;
    }

    public List<MenuItem> getItems() {
        return mItems;
    }

    public void setItems(List<MenuItem> orders) {
        mItems = orders;
    }
}
