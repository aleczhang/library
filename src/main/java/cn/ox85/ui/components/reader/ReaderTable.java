package cn.ox85.ui.components.reader;

import cn.ox85.models.Reader;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.ReaderMap;
import cn.ox85.ui.beans.ReaderBean;
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

/**
 * @author alec zhang
 */
public class ReaderTable extends ResultTable {
    static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_Id"),
            BundleUtil.getString("CLMN_ReaderBarCode"),
            BundleUtil.getString("CLMN_ReaderName"),
            BundleUtil.getString("CLMN_ReaderType"),
            BundleUtil.getString("CLMN_ReaderSex"),
            BundleUtil.getString("CLMN_ReaderIdentityNum"),
            BundleUtil.getString("CLMN_ReaderPhone"),
            BundleUtil.getString("CLMN_ReaderStatus"),
            BundleUtil.getString("CLMN_ReaderEmail"),
            BundleUtil.getString("CLMN_ReaderDate")
    };
    static final int[] PREF_WIDTH = {60, 100, 80, 80, 40, 150, 120, 40, 150, 100};

    public ReaderTable() {
        super(new ReaderTableModel(), ReaderSingleFilter.NONE);
        TableColumnModel cm = getColumnModel();
        for (int i = 0; i < PREF_WIDTH.length; i++) {
            cm.getColumn(i).setPreferredWidth(PREF_WIDTH[i]);
        }
    }

    @Override
    protected Object[][] getResults() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<Reader> readers = new ArrayList<Reader>();
        try {
            ReaderMap readerDAO = session.getMapper(ReaderMap.class);
            int count = filter_.getCount(readerDAO);
            pageMaxIndex_ = count / PAGESIZE;
            RowBounds bounds = new RowBounds(pageCurrentIndex_ * PAGESIZE, PAGESIZE);
            readers = filter_.getResults(readerDAO, bounds);
            session.commit();
        } finally {
            session.close();
        }
        return convertTo2DArray(readers);
    }

    private static Object[][] convertTo2DArray(java.util.List<Reader> readers) {
        int rowSize = readers.size();
        int columnSize = COLUMNS.length;
        Object[][] data = new Object[rowSize][columnSize];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < rowSize; i++) {
            ReaderBean reader = new ReaderBean(readers.get(i));
            data[i][0] = reader.getId();
            data[i][1] = reader.getBarCode();
            data[i][2] = reader.getName();
            data[i][3] = reader.getType().getName();
            data[i][4] = reader.getSex();
            data[i][5] = reader.getIdentityNum();
            data[i][6] = reader.getPhone();
            data[i][7] = reader.getStatus();
            data[i][8] = reader.getEmail();
            data[i][9] = format.format(reader.getDate());
        }
        return data;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            int result = JOptionPane.showConfirmDialog(null,
                    BundleUtil.getString("MSG_ToDeleteReader"),
                    BundleUtil.getString("TITLE_DeleteReader"),
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
                    ReaderMap readerDAO = session.getMapper(ReaderMap.class);
                    count = readerDAO.deleteReadersById(ids);
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
            ReaderEditDialog readerEdit = new ReaderEditDialog((Frame) parent, id);
            readerEdit.setVisible(true);
        }
    }
}
