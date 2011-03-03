package cn.ox85.ui.components.reader;

import cn.ox85.models.ReaderType;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.ReaderTypeMap;
import cn.ox85.ui.beans.SingleFilterBean;
import cn.ox85.ui.components.NavigationPanel;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.listeners.FilterChangeListener;
import cn.ox85.ui.util.BundleUtil;
import com.jgoodies.binding.BindingUtils;
import org.apache.ibatis.session.SqlSession;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author alec zhang
 */
public class ReaderNavigationPanel extends NavigationPanel {

    public ReaderNavigationPanel(FilterChangeListener listener) {
        super(listener);
    }

    @Override
    protected String getDateString() {
        return BundleUtil.getString("LBL_ReaderDate");
    }

    @Override
    protected TreeModel getTreeModel() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_All"));

        DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_ReaderType"));
        java.util.List<ReaderType> readerTypes = getReaderTypes();
        for (ReaderType type : readerTypes) {
            typeNode.add(new DefaultMutableTreeNode(new ReaderSingleFilter.Type(type)));
        }
        rootNode.add(typeNode);

        DefaultMutableTreeNode statusNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_ReaderStatus"));
        statusNode.add(new DefaultMutableTreeNode(ReaderSingleFilter.Status.NROMAL));
        statusNode.add(new DefaultMutableTreeNode(ReaderSingleFilter.Status.LOST));
        rootNode.add(statusNode);

        DefaultMutableTreeNode sexNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_ReaderSex"));
        sexNode.add(new DefaultMutableTreeNode(ReaderSingleFilter.Sex.MALE));
        sexNode.add(new DefaultMutableTreeNode(ReaderSingleFilter.Sex.FEMALE));
        rootNode.add(sexNode);
        return new DefaultTreeModel(rootNode);
    }

    @Override
    protected Vector<Filter> getItems() {
        Vector<Filter> items = new Vector<Filter>();
        items.add(new ReaderSingleFilter.BarCode());
        items.add(new ReaderSingleFilter.Name());
        items.add(new ReaderSingleFilter.IdentityNum());
        items.add(new ReaderSingleFilter.Phone());
        items.add(new ReaderSingleFilter.Email());
        return items;
    }

    @Override
    protected void doSearch() {
        SingleFilterBean filterModel = presentationModel_.getBean();
        String value = filterModel.getValue();
        ReaderSingleFilter filter;
        if (BindingUtils.isBlank(value)) {
            filter = ReaderSingleFilter.NONE;
        } else {
            filter = (ReaderSingleFilter) filterModel.getItem();
            filter.setParameter(value);
        }
        listener_.filterChanged(filter);
    }

    @Override
    protected void doSearch(Object nodeInfo, boolean isLeaf) {
        ReaderSingleFilter filter;
        if (isLeaf) {
            filter = (ReaderSingleFilter) nodeInfo;
        } else {
            filter = ReaderSingleFilter.NONE;
        }
        listener_.filterChanged(filter);
    }

    private java.util.List<ReaderType> getReaderTypes() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<ReaderType> readerTypes = new ArrayList<ReaderType>();
        try {
            ReaderTypeMap readerTypeDAO = session.getMapper(ReaderTypeMap.class);
            readerTypes = readerTypeDAO.selectReaderTypes();
            session.commit();
        } finally {
            session.close();
        }
        return readerTypes;
    }
}
