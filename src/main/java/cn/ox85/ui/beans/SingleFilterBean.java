package cn.ox85.ui.beans;

import cn.ox85.ui.listeners.Filter;
import com.jgoodies.binding.beans.Model;

import java.util.Date;

/**
 * @author alec zhang
 */
public class SingleFilterBean extends Model {
    public static final String PROPERTYNAME_ITEM = "item";
    public static final String PROPERTYNAME_VALUE = "value";
    public static final String PROPERTYNAME_STARTDATE = "startDate";
    public static final String PROPERTYNAME_ENDDATE = "endDate";

    private Filter item;
    private String value;
    private Date startDate;
    private Date endDate;

    public Filter getItem() {
        return item;
    }

    public void setItem(Filter newItem) {
        Filter oldItem = item;
        item = newItem;
        firePropertyChange(PROPERTYNAME_ITEM, oldItem, newItem);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        String oldValue = value;
        this.value = newValue;
        firePropertyChange(PROPERTYNAME_VALUE, oldValue, newValue);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        Date oldDate = this.startDate;
        this.startDate = startDate;
        firePropertyChange(PROPERTYNAME_STARTDATE, oldDate, startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Date oldDate = this.endDate;
        this.endDate = endDate;
        firePropertyChange(PROPERTYNAME_ENDDATE, oldDate, endDate);
    }
}
