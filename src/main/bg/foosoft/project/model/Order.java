package bg.foosoft.project.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gharizanov on 11.6.2014 г..
 */
@Entity("orders")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Order {

    private static final int STATUS_WAITING = 0;
    private static final int STATUS_TAKEN = 1;
    private static final int STATUS_FINISHED = 2;
    private static final int STATUS_OVERDUE = 3;
    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = 60 * ONE_SECOND;
    private static final int FIVE_MINUTES = 5 * ONE_MINUTE;

    @Id
    private String mId;

    @Embedded("waiter")
    private User mWaiter;

    @Property("table")
    private String mTable;

    @Property("date_taken")
    private Date mTakenDate;

    @Property("status")
    private int mStatus = STATUS_WAITING;

    @Embedded("items")
    private List<MenuItem> mItems;

    @JsonIgnore
    private Long mTimeAdded;

    @JsonIgnore
    private Long mDeadline;

    private boolean mExpired;

    public Long getRemainingTime(){
        /*
        mExpired = false;

        Long now = System.currentTimeMillis();
        Long remaining = mDeadline - now;
        if(remaining <= 0){
            mExpired = true;
            return "0";
        }
        Long minutes = remaining / ONE_MINUTE;
        Long seconds = (remaining % ONE_MINUTE) / ONE_SECOND;
        //String minutes = mins.toString();
        return minutes.toString() + ":" + seconds.toString();*/
        Long now = System.currentTimeMillis();
        Long remaining = mDeadline - now;

        if(remaining <= 0) {
            return 0l;
        }

        return remaining / ONE_SECOND;
    }

    //huehuehuehuehuehuehuehue
    public Order() {
        mTimeAdded = System.currentTimeMillis();
        mDeadline = mTimeAdded + FIVE_MINUTES;
        mItems = new LinkedList<MenuItem>();
    }

    public void addItem(MenuItem aItem){
        if(mItems != null){
            mItems.add(aItem);
        } else {
            throw new NullPointerException("List of items is null");
        }
    }

    public boolean isExpired() {
        return mExpired;
    }

    public void setExpired(boolean mExpired) {
        this.mExpired = mExpired;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getWaiterId() {
        return mWaiter.getId();
    }

    public void setWaiter(User waiter) {
        mWaiter = waiter;
    }

    public String getTable() {
        return mTable;
    }

    public void setTable(String table) {
        mTable = table;
    }

    public Date getTakenDate() {
        return mTakenDate;
    }

    public void setTakenDate(Date takenDate) {
        mTakenDate = takenDate;
    }

    public Long getTimeAdded() {
        return mTimeAdded;
    }

    public void setTimeAdded(Long timeAdded) {
        mTimeAdded = timeAdded;
    }

    public Long getDeadline() {
        return mDeadline;
    }

    public void setDeadline(Long deadline) {
        mDeadline = deadline;
    }

    public List<MenuItem> getItems() {
        return mItems;
    }

    public void setItems(List<MenuItem> items) {
        mItems = items;
    }

    public Integer getStatus() {
        return mStatus;
    }

    public void setStatus(Integer status) {
        mStatus = status;
    }

    public User getWaiter() {
        return mWaiter;
    }
}
