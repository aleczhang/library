package cn.ox85.ui.components.book;

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
public class BookNavigationPanel extends NavigationPanel {
    public BookNavigationPanel(FilterChangeListener listener) {
        super(listener);
    }

    @Override
    protected String getDateString() {
        return BundleUtil.getString("LBL_BookDate");
    }

    @Override
    protected TreeModel getTreeModel() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_All"));

        DefaultMutableTreeNode statusNode = new DefaultMutableTreeNode(BundleUtil.getString("NODE_BookStatus"));
        statusNode.add(new DefaultMutableTreeNode(BookSingleFilter.Status.IN));
        statusNode.add(new DefaultMutableTreeNode(BookSingleFilter.Status.OUT));
        rootNode.add(statusNode);
        return new DefaultTreeModel(rootNode);
    }

    @Override
    protected Vector<Filter> getItems() {
        Vector<Filter> items = new Vector<Filter>();
        items.add(new BookSingleFilter.BarCode());
        items.add(new BookSingleFilter.Name());
        items.add(new BookSingleFilter.Publisher());
        items.add(new BookSingleFilter.Author());
        return items;
    }

    @Override
    protected void doSearch() {
        SingleFilterBean filterModel = presentationModel_.getBean();
        String value = filterModel.getValue();
        BookSingleFilter filter;
        if (BindingUtils.isBlank(value)) {
            filter = BookSingleFilter.NONE;
        } else {
            filter = (BookSingleFilter) filterModel.getItem();
            filter.setParameter(value);
        }
        listener_.filterChanged(filter);
    }

    @Override
    protected void doSearch(Object nodeInfo, boolean isLeaf) {
        BookSingleFilter filter;
        if (isLeaf) {
            filter = (BookSingleFilter) nodeInfo;
        } else {
            filter = BookSingleFilter.NONE;
        }
        listener_.filterChanged(filter);
    }
}
