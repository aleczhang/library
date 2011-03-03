package cn.ox85.ui.components.book;

import cn.ox85.models.Book;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BookMap;
import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.components.ResultTable;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author alec zhang
 */
public class BookTable extends ResultTable {
    static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_Id"),
            BundleUtil.getString("CLMN_BookBarCode"),
            BundleUtil.getString("CLMN_BookISBN"),
            BundleUtil.getString("CLMN_BookName"),
            BundleUtil.getString("CLMN_BookSeries"),
            BundleUtil.getString("CLMN_BookVolume"),
            BundleUtil.getString("CLMN_BookPublihser"),
            BundleUtil.getString("CLMN_BookAuthor"),
            BundleUtil.getString("CLMN_BookTranslator"),
            BundleUtil.getString("CLMN_BookCallNum"),
            BundleUtil.getString("CLMN_BookStatus"),
            BundleUtil.getString("CLMN_BookLentNum"),
            BundleUtil.getString("CLMN_BookClassifier"),
            BundleUtil.getString("CLMN_BookPubDate"),
            BundleUtil.getString("CLMN_BookPrice"),
            BundleUtil.getString("CLMN_BookEdition"),
            BundleUtil.getString("CLMN_BookPageNum"),
            BundleUtil.getString("CLMN_BookRemarks"),
            BundleUtil.getString("CLMN_BookSummary"),
            BundleUtil.getString("CLMN_BookEnterDate")
    };
    static final int[] PREF_WIDTH = {
            60, 100, 120, 160, 90, 60, 160, 100, 100, 120,
            40, 60, 60, 100, 70, 40, 60, 160, 160, 100
    };

    public BookTable() {
        super(new BookTableModel(), BookSingleFilter.NONE);
        TableColumnModel cm = getColumnModel();
        for (int i = 0; i < PREF_WIDTH.length; i++) {
            cm.getColumn(i).setPreferredWidth(PREF_WIDTH[i]);
        }
    }

    @Override
    protected Object[][] getResults() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<Book> books = new ArrayList<Book>();
        try {
            BookMap bookDAO = session.getMapper(BookMap.class);
            int count = filter_.getCount(bookDAO);
            pageMaxIndex_ = count / PAGESIZE;
            RowBounds bounds = new RowBounds(pageCurrentIndex_ * PAGESIZE, PAGESIZE);
            books = filter_.getResults(bookDAO, bounds);
            session.commit();
        } finally {
            session.close();
        }
        return convertTo2DArray(books);
    }

    private static Object[][] convertTo2DArray(java.util.List<Book> books) {
        int rowSize = books.size();
        int columnSize = COLUMNS.length;
        Object[][] data = new Object[rowSize][columnSize];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < rowSize; i++) {
            BookBean bookModel = new BookBean(books.get(i));
            data[i][0] = bookModel.getId();
            data[i][1] = bookModel.getBarCode();
            data[i][2] = bookModel.getIsbn();
            data[i][3] = bookModel.getName();
            data[i][4] = bookModel.getSeries();
            data[i][5] = bookModel.getVolume();
            data[i][6] = bookModel.getPublisher();
            data[i][7] = bookModel.getAuthor();
            data[i][8] = bookModel.getTranslator();
            data[i][9] = bookModel.getCallNum();
            data[i][10] = bookModel.getStatus();
            data[i][11] = bookModel.getLentNum();
            data[i][12] = bookModel.getClassifier();
            Date date = bookModel.getPubDate();
            data[i][13] = date == null ? "" : format.format(date);
            data[i][14] = bookModel.getPrice();
            data[i][15] = bookModel.getEdition();
            data[i][16] = bookModel.getPageNum();
            data[i][17] = bookModel.getRemarks();
            data[i][18] = bookModel.getSummary();
            date = bookModel.getEnterDate();
            data[i][19] = date == null ? "" : format.format(date);
        }
        return data;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            int result = JOptionPane.showConfirmDialog(null,
                    BundleUtil.getString("MSG_ToDeleteBook"),
                    BundleUtil.getString("TITLE_DeleteBook"),
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int[] rows = this.getSelectedRows();
                java.util.List<Integer> ids = new ArrayList<Integer>();
                for (int row : rows) {
                    ids.add((Integer) dataModel_.getValueAt(row, 0));
                }
                SqlSession session = ConnectionFactory.getSession().openSession();
                int count;
                try {
                    BookMap bookMap = session.getMapper(BookMap.class);
                    count = bookMap.deleteBooksById(ids);
                    session.commit();
                } finally {
                    session.close();
                }
                if (count > 0) {
                    new ShowResultWork().execute();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int row = this.getSelectedRow();
            int id = (Integer) dataModel_.getValueAt(row, 0);
            Container parent = getParent();
            while (!(parent instanceof Frame)) {
                parent = parent.getParent();
            }
            BookEditDialog bookEditDialog = new BookEditDialog((Frame) parent, id);
            bookEditDialog.setVisible(true);
        }
    }
}