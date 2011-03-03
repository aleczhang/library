package cn.ox85.models;

import java.util.Date;

/**
 * @author alec zhang
 */
public class RI {
    private int id;
    private int readerId;
    private String readerBarCode;
    private String readerName;
    private Date datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getReaderBarCode() {
        return readerBarCode;
    }

    public void setReaderBarCode(String readerBarCode) {
        this.readerBarCode = readerBarCode;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
