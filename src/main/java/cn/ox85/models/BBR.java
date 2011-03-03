package cn.ox85.models;

import java.util.Date;

/**
 * @author alec zhang
 */
public class BBR {
    private int id;
    private Book book;
    private Reader reader;
    private boolean returned;
    private Date bdate;
    private Date rdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public int getReaderId() {
        return reader.getId();
    }

    public void setReaderId(int readerId) {
        if (reader == null) {
            reader = new Reader();
        }
        reader.setId(readerId);
    }

    public String getReaderBarCode() {
        return reader.getBarCode();
    }

    public void setReaderBarCode(String readerBarCode) {
        if (reader == null) {
            reader = new Reader();
        }
        reader.setBarCode(readerBarCode);
    }

    public String getReaderName() {
        return reader.getName();
    }

    public void setReaderName(String readerName) {
        if (reader == null) {
            reader = new Reader();
        }
        reader.setName(readerName);
    }

    public int getBookId() {
        return book.getId();
    }

    public void setBookId(int bookId) {
        if (book == null) {
            book = new Book();
        }
        book.setId(bookId);
    }

    public String getBookBarCode() {
        return book.getBarCode();
    }

    public void setBookBarCode(String bookBarCode) {
        if (book == null) {
            book = new Book();
        }
        book.setBarCode(bookBarCode);
    }

    public String getBookName() {
        return book.getName();
    }

    public void setBookName(String bookName) {
        if (book == null) {
            book = new Book();
        }
        book.setName(bookName);
    }
}
