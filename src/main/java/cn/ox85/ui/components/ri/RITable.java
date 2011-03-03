package cn.ox85.ui.components.ri;

import cn.ox85.models.RI;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.RIMap;
import cn.ox85.ui.components.ResultTable;
import cn.ox85.ui.util.BundleUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import javax.swing.table.TableColumnModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author alec zhang
 */
public class RITable extends ResultTable {
    static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_Id"),
            BundleUtil.getString("CLMN_ReaderBarCode"),
            BundleUtil.getString("CLMN_ReaderName"),
            BundleUtil.getString("CLMN_InDatetime")
    };
    static final int[] PREF_WIDTH = {60, 100, 80, 130};

    public RITable() {
        super(new RITableModel(), RISingleFilter.NONE);
        TableColumnModel cm = getColumnModel();
        for (int i = 0; i < PREF_WIDTH.length; i++) {
            cm.getColumn(i).setPreferredWidth(PREF_WIDTH[i]);
        }
    }

    @Override
    protected Object[][] getResults() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<RI> ri = new ArrayList<RI>();
        try {
            RIMap riDAO = session.getMapper(RIMap.class);
            int count = filter_.getCount(riDAO);
            pageMaxIndex_ = count / PAGESIZE;
            RowBounds bounds = new RowBounds(pageCurrentIndex_ * PAGESIZE, PAGESIZE);
            ri = filter_.getResults(riDAO, bounds);
            session.commit();
        } finally {
            session.close();
        }
        return convertTo2DArray(ri);
    }

    private static Object[][] convertTo2DArray(java.util.List<RI> riList) {
        int rowSize = riList.size();
        int columnSize = COLUMNS.length;
        Object[][] data = new Object[rowSize][columnSize];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < rowSize; i++) {
            RI ri = riList.get(i);
            data[i][0] = ri.getId();
            data[i][1] = ri.getReaderBarCode();
            data[i][2] = ri.getReaderName();
            data[i][3] = format.format(ri.getDatetime());
        }
        return data;
    }
}
