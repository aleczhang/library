package cn.ox85.ui.components.bbr;

import cn.ox85.ui.beans.SingleFilterBean;
import cn.ox85.ui.components.NavigationPanel;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.listeners.FilterChangeListener;
import cn.ox85.ui.util.BundleUtil;
import com.jgoodies.binding.BindingUtils;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.Vector;

/**
 * @author alec zhang
 */
public class BBRNavigationPanel extends NavigationPanel {
    public BBRNavigationPanel(FilterChangeListener listener) {
        super(listener);
    }

    @Override
    protected String getDateString() {
        return BundleUtil.getString("LBL_BookDate");
    }

    @Override
    protected TreeModel getTreeModel() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_All"));
        DefaultMutableTreeNode statusNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_BBRStatus"));
        statusNode.add(new DefaultMutableTreeNode(BBRSingleFilter.Status.RETURNED));
        statusNode.add(new DefaultMutableTreeNode(BBRSingleFilter.Status.NORETURNED));
        rootNode.add(statusNode);
        return new DefaultTreeModel(rootNode);
    }

    @Override
    protected Vector<Filter> getItems() {
        Vector<Filter> items = new Vector<Filter>();
        items.add(new BBRSingleFilter.ReaderBarCode());
        items.add(new BBRSingleFilter.BookBarCode());
        return items;
    }

    @Override
    protected void doSearch() {
        SingleFilterBean filterModel = presentationModel_.getBean();
        String value = filterModel.getValue();
        BBRSingleFilter filter;
        if (BindingUtils.isBlank(value)) {
            filter = BBRSingleFilter.NONE;
        } else {
            filter = (BBRSingleFilter) filterModel.getItem();
            filter.setParameter(value);
        }
        listener_.filterChanged(filter);
    }

    @Override
    protected void doSearch(Object nodeInfo, boolean isLeaf) {
        BBRSingleFilter filter;
        if (isLeaf) {
            filter = (BBRSingleFilter) nodeInfo;
        } else {
            filter = BBRSingleFilter.NONE;
        }
        listener_.filterChanged(filter);
    }
}