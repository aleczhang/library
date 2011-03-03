package cn.ox85.ui.components;

import cn.ox85.ui.beans.SingleFilterBean;
import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.listeners.FilterChangeListener;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author alec zhang
 */
public abstract class NavigationPanel extends JPanel implements ActionListener, KeyListener, TreeSelectionListener {
    private JTree navigationTree_;
    private JComboBox itemComboBox_;
    protected JTextField searchTextField_;
    protected JButton searchButton_;
    private DatePicker dateStartPicker_;
    private DatePicker dateEndPicker_;


    protected FilterChangeListener listener_;
    protected final PresentationModel<SingleFilterBean> presentationModel_ =
            new PresentationModel<SingleFilterBean>(new SingleFilterBean());

    public NavigationPanel(FilterChangeListener listener) {
        initComponents();
        listener_ = listener;
    }

    private void initComponents() {
        JScrollPane navigationScrollPane = new JScrollPane();
        navigationTree_ = new JTree(getTreeModel());
        navigationTree_.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        navigationTree_.addTreeSelectionListener(this);
        for (int i = navigationTree_.getRowCount() - 1; i >= 0; i--) {
            navigationTree_.expandRow(i);
        }
        JLabel itemLabel = new JLabel();
        ValueModel itemModel = presentationModel_.getModel(SingleFilterBean.PROPERTYNAME_ITEM);
        itemComboBox_ = BasicComponentFactory.createComboBox(
                new SelectionInList<Filter>(getItems(), itemModel));
        itemComboBox_.setSelectedIndex(0);
        JLabel searchLabel = new JLabel();
        searchTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(SingleFilterBean.PROPERTYNAME_VALUE), false);
        searchTextField_.addKeyListener(this);
        searchButton_ = new JButton();
        JLabel dateStartLabel = new JLabel();
        dateStartPicker_ = new DatePicker();
        JLabel dateEndLabel = new JLabel();
        dateEndPicker_ = new DatePicker();
        CellConstraints cc = new CellConstraints();

        setLayout(new FormLayout(
                "default, $lcgap, default:grow, $lcgap, default",
                "fill:default:grow, $ugap, 5*(default, $lgap), default"));

        navigationScrollPane.setViewportView(navigationTree_);
        add(navigationScrollPane, cc.xywh(1, 1, 5, 1));

        itemLabel.setText(BundleUtil.getString("LBL_Item"));
        add(itemLabel, cc.xy(1, 3));
        add(itemComboBox_, cc.xywh(3, 3, 3, 1));

        searchLabel.setText(BundleUtil.getString("LBL_Search"));
        add(searchLabel, cc.xy(1, 5));
        add(searchTextField_, cc.xy(3, 5));

        searchButton_.setIcon(IconLoader.getIcon16("search_button.png"));
        searchButton_.addActionListener(this);
        add(searchButton_, cc.xy(5, 5));
//TODO: Use the date in search
//        dateStartLabel.setText(BundleUtil.getString("LBL_DateStart", getDateString()));
//        add(dateStartLabel, cc.xywh(1, 7, 3, 1));
//        add(dateStartPicker_, cc.xywh(1, 9, 5, 1));
//
//        dateEndLabel.setText(BundleUtil.getString("LBL_DateEnd"));
//        add(dateEndLabel, cc.xywh(1, 11, 3, 1));
//        add(dateEndPicker_, cc.xywh(1, 13, 5, 1));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource() == searchTextField_) {
                doSearch();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton_) {
            doSearch();
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) navigationTree_.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        doSearch(node.getUserObject(), node.isLeaf());
    }

    protected abstract String getDateString();

    protected abstract TreeModel getTreeModel();

    protected abstract Vector<Filter> getItems();

    protected abstract void doSearch();

    protected abstract void doSearch(Object nodeInfo, boolean isLeaf);
}
