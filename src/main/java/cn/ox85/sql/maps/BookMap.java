package cn.ox85.sql.maps;

import cn.ox85.models.Book;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author alec zhang
 */
public interface BookMap {
    public int insertBook(Book book);

    public int updateBook(Book book);

    public int setBookBorrowed(int id);

    public Book selectBookById(int id);

    public Book selectBookByBarCode(String barCode);

    public int selectBookCountByBarCode(String barCode);

    public List<Book> searchBookByBarCode(String barCode, RowBounds bounds);

    public int selectBookCountByName(String name);

    public List<Book> searchBookByName(String name, RowBounds bounds);

    public int selectBookCountByPublisher(String publisher);

    public List<Book> searchBookByPublisher(String publisher, RowBounds bounds);

    public int selectBookCountByAuthor(String author);

    public List<Book> searchBookByAuthor(String author, RowBounds bounds);

    public int selectBookCountByStatus(int status);

    public List<Book> searchBookByStatus(int status, RowBounds bounds);

    public int selectBookCount();

    public List<Book> selectBooks(RowBounds bounds);

    public int deleteBooksById(List<Integer> ids);
}
