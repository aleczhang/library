package cn.ox85.ui.components.book;

import cn.ox85.models.Book;
import cn.ox85.sql.maps.BookMap;
import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public abstract class BookSingleFilter<P> implements Filter<BookMap, Book> {
    protected P parameter_;

    @Override
    public abstract int getCount(BookMap mapper);

    @Override
    public abstract List<Book> getResults(BookMap mapper, RowBounds bounds);

    public void setParameter(P parameter) {
        parameter_ = parameter;
    }

    public P getParameter() {
        return parameter_;
    }

    public static final BookSingleFilter NONE = new BookSingleFilter<String>() {

        @Override
        public int getCount(BookMap bookMap) {
            return bookMap.selectBookCount();
        }

        @Override
        public List<Book> getResults(BookMap bookMap, RowBounds bounds) {
            return bookMap.selectBooks(bounds);
        }
    };

    static class Status extends BookSingleFilter<BookBean.Status> {
        public static final Status IN = new Status(BookBean.Status.IN);
        public static final Status OUT = new Status(BookBean.Status.OUT);

        private Status(BookBean.Status status) {
            parameter_ = status;
        }

        @Override
        public int getCount(BookMap bookMap) {
            return bookMap.selectBookCountByStatus(getParameter().ordinal());
        }

        @Override
        public List<Book> getResults(BookMap bookMap, RowBounds bounds) {
            return bookMap.searchBookByStatus(getParameter().ordinal(), bounds);
        }

        @Override
        public String toString() {
            return parameter_.toString();
        }
    }

    static class BarCode extends BookSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITME_BookBarCode");

        @Override
        public int getCount(BookMap bookMap) {
            return bookMap.selectBookCountByBarCode(parameter_);
        }

        @Override
        public List<Book> getResults(BookMap bookMap, RowBounds bounds) {
            return bookMap.searchBookByBarCode(parameter_, bounds);
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class Name extends BookSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_BookName");

        @Override
        public int getCount(BookMap bookMap) {
            return bookMap.selectBookCountByName(parameter_);
        }

        @Override
        public List<Book> getResults(BookMap bookMap, RowBounds bounds) {
            return bookMap.searchBookByName(parameter_, bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class Publisher extends BookSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_BookPublisher");

        @Override
        public int getCount(BookMap bookMap) {
            return bookMap.selectBookCountByPublisher(parameter_);
        }

        @Override
        public List<Book> getResults(BookMap bookMap, RowBounds bounds) {
            return bookMap.searchBookByPublisher(parameter_, bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }

    static class Author extends BookSingleFilter<String> {
        private static final String NAME = BundleUtil.getString("ITEM_BookAuthor");

        @Override
        public int getCount(BookMap bookMap) {
            return bookMap.selectBookCountByAuthor(parameter_);
        }

        @Override
        public List<Book> getResults(BookMap bookMap, RowBounds bounds) {
            return bookMap.searchBookByAuthor(parameter_, bounds);
        }

        @Override
        public void setParameter(String parameter) {
            parameter_ = "%" + parameter + "%";
        }

        @Override
        public String toString() {
            return NAME;
        }
    }
}