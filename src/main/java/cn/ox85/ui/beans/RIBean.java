package cn.ox85.ui.beans;

import cn.ox85.models.Reader;
import com.jgoodies.binding.beans.Model;

import java.util.Date;

/**
 * @author alec zhang
 */
public class RIBean extends Model {
    public static final String PROPERTYNAME_READER = "reader";
    public static final String PROPERTYNAME_DATETIME = "dateTime";

    private Reader reader;
    private Date dateTime;

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader newReader) {
        Reader oldReader = getReader();
        reader = newReader;
        firePropertyChange(PROPERTYNAME_READER, oldReader, newReader);
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date newDateTime) {
        Date oldDateTime = getDateTime();
        dateTime = newDateTime;
        firePropertyChange(PROPERTYNAME_DATETIME, oldDateTime, newDateTime);
    }
}
