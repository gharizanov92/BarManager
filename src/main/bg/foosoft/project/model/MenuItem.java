package bg.foosoft.project.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by gharizanov on 10.6.2014 г..
 */
@Entity("items")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class MenuItem {

    @Id
    private String mId;

    @Property("label")
    private String mLabel;

    @Property("price")
    private Double mPrice;

    //for making orders
    private Integer quantity;

    public MenuItem() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
