package cn.ox85.ui.beans;

import com.jgoodies.binding.beans.Model;

/**
 * @author alec zhang
 */
public class BBRBean extends Model {
    public static final String PROPERTYNAME_READERBARCODE = "readerBarCode";
    public static final String PROPERTYNAME_READERNAME = "readerName";
    public static final String PROPERTYNAME_READERTYPE = "readerType";
    public static final String PROPERTYNAME_BOOKBARCODE = "bookBarCode";
    public static final String PROPERTYNAME_BOOKNAME = "bookName";

    private String readerBarCode;
    private String bookBarCode;
    private String bookName;
    private String readerName;
    private String readerType;

    public String getReaderBarCode() {
        return readerBarCode;
    }

    public void setReaderBarCode(String readerBarCode) {
        String old = getReaderBarCode();
        this.readerBarCode = readerBarCode;
        firePropertyChange(PROPERTYNAME_READERBARCODE, old, readerBarCode);
    }

    public String getBookBarCode() {
        return bookBarCode;
    }

    public void setBookBarCode(String bookBarCode) {
        String old = getBookBarCode();
        this.bookBarCode = bookBarCode;
        firePropertyChange(PROPERTYNAME_BOOKBARCODE, old, bookBarCode);
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        String old = getBookName();
        this.bookName = bookName;
        firePropertyChange(PROPERTYNAME_BOOKNAME, old, bookName);
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        String old = getReaderName();
        this.readerName = readerName;
        firePropertyChange(PROPERTYNAME_READERNAME, old, readerName);
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        String old = getReaderType();
        this.readerType = readerType;
        firePropertyChange(PROPERTYNAME_READERTYPE, old, readerType);
    }
}
