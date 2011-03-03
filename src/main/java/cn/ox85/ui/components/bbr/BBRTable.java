package cn.ox85.ui.components.bbr;

import cn.ox85.models.BBR;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BBRMap;
import cn.ox85.ui.components.ResultTable;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import javax.swing.table.TableColumnModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author alec zhang
 */
public class BBRTable extends ResultTable {
    static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_Id"),
            BundleUtil.getString("CLMN_ReaderBarCode"),
            BundleUtil.getString("CLMN_ReaderName"),
            BundleUtil.getString("CLMN_BookBarCode"),
            BundleUtil.getString("CLMN_BookName"),
            BundleUtil.getString("CLMN_BookBDate"),
            BundleUtil.getString("CLMN_BookRDate")
    };
    static final int[] PREF_WIDTH = {60, 100, 80, 100, 120, 100, 100};

    public BBRTable() {
        super(new BBRTableModel(), BBRSingleFilter.NONE);
        TableColumnModel cm = getColumnModel();
        for (int i = 0; i < PREF_WIDTH.length; i++) {
            cm.getColumn(i).setPreferredWidth(PREF_WIDTH[i]);
        }
    }

    @Override
    protected Object[][] getResults() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<BBR> bbr = new ArrayList<BBR>();
        try {
            BBRMap bbrDAO = session.getMapper(BBRMap.class);
            int count = filter_.getCount(bbrDAO);
            pageMaxIndex_ = count / PAGESIZE;
            RowBounds bounds = new RowBounds(pageCurrentIndex_ * PAGESIZE, PAGESIZE);
            bbr = filter_.getResults(bbrDAO, bounds);
            session.commit();
        } finally {
            session.close();
        }
        return convertTo2DArray(bbr);
    }

    private static Object[][] convertTo2DArray(java.util.List<BBR> bbrList) {
        int rowSize = bbrList.size();
        int columnSize = COLUMNS.length;
        Object[][] data = new Object[rowSize][columnSize];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < rowSize; i++) {
            BBR bbr = bbrList.get(i);
            data[i][0] = bbr.getId();
            data[i][1] = bbr.getReaderBarCode();
            data[i][2] = bbr.getReaderName();
            data[i][3] = bbr.getBookBarCode();
            data[i][4] = bbr.getBookName();
            data[i][5] = format.format(bbr.getBdate());
            Date date = bbr.getRdate();
            data[i][6] = date != null ? format.format(date) : "";
        }
        return data;
    }
}