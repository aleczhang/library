package cn.ox85.ui.beans;

import cn.ox85.models.Book;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BookMap;
import cn.ox85.ui.util.BundleUtil;
import com.jgoodies.binding.beans.Model;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * @author alec zhang
 */
public class BookBean extends Model {
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_BARCODE = "barCode";
    public static final String PROPERTYNAME_ISBN = "isbn";
    public static final String PROPERTYNAME_NAME = "name";
    public static final String PROPERTYNAME_SERIES = "series";
    public static final String PROPERTYNAME_VOLUME = "volume";
    public static final String PROPERTYNAME_PUBLISHER = "publisher";
    public static final String PROPERTYNAME_AUTHOR = "author";
    public static final String PROPERTYNAME_TRANSLATOR = "translator";
    public static final String PROPERTYNAME_CALLNUM = "callNum";
    public static final String PROPERTYNAME_STATUS = "status";
    public static final String PROPERTYNAME_LENTNUM = "lentNum";
    public static final String PROPERTYNAME_CLASSIFIER = "classifier";
    public static final String PROPERTYNAME_PUBDATE = "pubDate";
    public static final String PROPERTYNAME_PRICE = "price";
    public static final String PROPERTYNAME_EDITION = "edition";
    public static final String PROPERTYNAME_PAGENUM = "pageNum";
    public static final String PROPERTYNAME_REMARKS = "remarks";
    public static final String PROPERTYNAME_SUMMARY = "summary";
    public static final String PROPERTYNAME_ENTERDATE = "enterDate";

    private Book book_;

    public BookBean() {
        book_ = new Book();
    }

    public BookBean(Book book) {
        book_ = book;
    }

    public int insertBook() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        try {
            BookMap bookMap = session.getMapper(BookMap.class);
            int count = bookMap.insertBook(book_);
            session.commit();
            return count;
        } finally {
            session.close();
        }
    }

    public int updateBook() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        try {
            BookMap bookMap = session.getMapper(BookMap.class);
            int count = bookMap.updateBook(book_);
            session.commit();
            return count;
        } finally {
            session.close();
        }
    }

    public int getId() {
        return book_.getId();
    }

    public void setId(int id) {
        int old = getId();
        book_.setId(id);
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    public String getBarCode() {
        return book_.getBarCode();
    }

    public void setBarCode(String barCode) {
        String old = getBarCode();
        book_.setBarCode(barCode);
        firePropertyChange(PROPERTYNAME_BARCODE, old, barCode);
    }

    public String getIsbn() {
        return book_.getIsbn();
    }

    public void setIsbn(String isbn) {
        String old = getIsbn();
        book_.setIsbn(isbn);
        firePropertyChange(PROPERTYNAME_ISBN, old, isbn);
    }

    public String getName() {
        return book_.getName();
    }

    public void setName(String name) {
        String old = getName();
        book_.setName(name);
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    public String getSeries() {
        return book_.getSeries();
    }

    public void setSeries(String series) {
        String old = getSeries();
        book_.setSeries(series);
        firePropertyChange(PROPERTYNAME_SERIES, old, series);
    }

    public String getVolume() {
        return book_.getVolume();
    }

    public void setVolume(String volume) {
        String old = getVolume();
        book_.setVolume(volume);
        firePropertyChange(PROPERTYNAME_VOLUME, old, volume);
    }

    public String getPublisher() {
        return book_.getPublisher();
    }

    public void setPublisher(String publisher) {
        String old = getPublisher();
        book_.setPublisher(publisher);
        firePropertyChange(PROPERTYNAME_PUBLISHER, old, publisher);
    }

    public String getAuthor() {
        return book_.getAuthor();
    }

    public void setAuthor(String author) {
        String old = getAuthor();
        book_.setAuthor(author);
        firePropertyChange(PROPERTYNAME_AUTHOR, old, author);
    }

    public String getTranslator() {
        return book_.getTranslator();
    }

    public void setTranslator(String translator) {
        String old = getTranslator();
        book_.setTranslator(translator);
        firePropertyChange(PROPERTYNAME_TRANSLATOR, old, translator);
    }

    public String getCallNum() {
        return book_.getCallNum();
    }

    public void setCallNum(String callNum) {
        String old = getCallNum();
        book_.setCallNum(callNum);
        firePropertyChange(PROPERTYNAME_CALLNUM, old, callNum);
    }

    public Status getStatus() {
        return Status.values()[book_.getStatus()];
    }

    public void setStatus(Status status) {
        Status old = getStatus();
        book_.setStatus(status.ordinal());
        firePropertyChange(PROPERTYNAME_STATUS, old, status);
    }

    public int getLentNum() {
        return book_.getLentNum();
    }

    public void setLentNum(int lentNum) {
        int old = getLentNum();
        book_.setLentNum(lentNum);
        firePropertyChange(PROPERTYNAME_LENTNUM, old, lentNum);
    }

    public String getClassifier() {
        return book_.getClassifier();
    }

    public void setClassifier(String classifier) {
        String old = getClassifier();
        book_.setClassifier(classifier);
        firePropertyChange(PROPERTYNAME_CLASSIFIER, old, classifier);
    }

    public Date getPubDate() {
        return book_.getPubDate();
    }

    public void setPubDate(Date pubDate) {
        Date old = getPubDate();
        book_.setPubDate(pubDate);
        firePropertyChange(PROPERTYNAME_PUBDATE, old, pubDate);
    }

    public double getPrice() {
        return book_.getPrice();
    }

    public void setPrice(double price) {
        double old = getPrice();
        book_.setPrice(price);
        firePropertyChange(PROPERTYNAME_PRICE, old, price);
    }

    public int getEdition() {
        return book_.getEdition();
    }

    public void setEdition(int edition) {
        int old = getEdition();
        book_.setEdition(edition);
        firePropertyChange(PROPERTYNAME_EDITION, old, edition);
    }

    public int getPageNum() {
        return book_.getPageNum();
    }

    public void setPageNum(int pageNum) {
        int old = getPageNum();
        book_.setPageNum(pageNum);
        firePropertyChange(PROPERTYNAME_PAGENUM, old, pageNum);
    }

    public String getRemarks() {
        return book_.getRemarks();
    }

    public void setRemarks(String remarks) {
        String old = getRemarks();
        book_.setRemarks(remarks);
        firePropertyChange(PROPERTYNAME_REMARKS, old, remarks);
    }

    public String getSummary() {
        return book_.getSummary();
    }

    public void setSummary(String summary) {
        String old = getSummary();
        book_.setSummary(summary);
        firePropertyChange(PROPERTYNAME_SUMMARY, old, summary);
    }

    public Date getEnterDate() {
        return book_.getEnterDate();
    }

    public void setEnterDate(Date enterDate) {
        Date old = getEnterDate();
        book_.setEnterDate(enterDate);
        firePropertyChange(PROPERTYNAME_ENTERDATE, old, enterDate);
    }

    public static enum Status {
        IN(BundleUtil.getString("ITEM_StatusIn")),
        OUT(BundleUtil.getString("ITEM_StatusOut"));

        private String name_;

        private Status(String name) {
            name_ = name;
        }

        @Override
        public String toString() {
            return name_;
        }
    }
}
