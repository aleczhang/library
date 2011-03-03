package cn.ox85.ui.components.ri;

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
public class RINavigationPanel extends NavigationPanel {

    public RINavigationPanel(FilterChangeListener listener) {
        super(listener);
    }

    @Override
    protected String getDateString() {
        return BundleUtil.getString("LBL_ReaderDate");
    }

    @Override
    protected TreeModel getTreeModel() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(RISingleFilter.NONE);
        return new DefaultTreeModel(rootNode);
    }

    @Override
    protected Vector<Filter> getItems() {
        Vector<Filter> items = new Vector<Filter>();
        items.add(new RISingleFilter.ReaderBarCode());
        items.add(new RISingleFilter.ReaderName());
        return items;
    }

    @Override
    protected void doSearch() {
        SingleFilterBean filterModel = presentationModel_.getBean();
        String value = filterModel.getValue();
        RISingleFilter filter;
        if (BindingUtils.isBlank(value)) {
            filter = RISingleFilter.NONE;
        } else {
            filter = (RISingleFilter) filterModel.getItem();
            filter.setParameter(value);
        }
        listener_.filterChanged(filter);
    }

    @Override
    protected void doSearch(Object nodeInfo, boolean isLeaf) {
        RISingleFilter filter;
        if (isLeaf) {
            filter = (RISingleFilter) nodeInfo;
        } else {
            filter = RISingleFilter.NONE;
        }
        listener_.filterChanged(filter);
    }
}