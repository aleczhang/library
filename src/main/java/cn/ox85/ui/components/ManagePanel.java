package cn.ox85.ui.components;

import cn.ox85.ui.listeners.FilterChangeListener;
import cn.ox85.ui.util.IconLoader;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author alec zhang
 */
public abstract class ManagePanel extends JPanel implements ActionListener, PropertyChangeListener {
    private static final Logger logger_ = LoggerFactory.getLogger(ManagePanel.class);
    private JButton searchButton_;
    private JButton firstPageButton_;
    private JButton previousPageButton_;
    private JTextField currentPageTextField_;
    private JLabel totalPageLabel_;
    private JButton nextPageButton_;
    private JButton lastPageButton_;
    protected ResultTable resultTable_;

    public ManagePanel() {
        initComponents();
        wireEvents();
    }

    protected abstract ResultTable buildResultTable();

    private void initComponents() {
        searchButton_ = new JButton();
        firstPageButton_ = new JButton();
        currentPageTextField_ = new JTextField();
        totalPageLabel_ = new JLabel();
        previousPageButton_ = new JButton();
        nextPageButton_ = new JButton();
        lastPageButton_ = new JButton();
        JScrollPane pane = new JScrollPane();
        resultTable_ = buildResultTable();
        resultTable_.addPropertyChangeListener(ResultTable.PROPERTYNAME_MODEL, this);
        pane.setViewportView(resultTable_);

        searchButton_.setIcon(IconLoader.getIcon16("search_button.png"));
        searchButton_.addActionListener(this);
        firstPageButton_.setIcon(IconLoader.getIcon16("first_page.png"));
        firstPageButton_.addActionListener(this);
        previousPageButton_.setIcon(IconLoader.getIcon16("previous_page.png"));
        previousPageButton_.addActionListener(this);
        nextPageButton_.setIcon(IconLoader.getIcon16("next_page.png"));
        nextPageButton_.addActionListener(this);
        lastPageButton_.setIcon(IconLoader.getIcon16("last_page.png"));
        lastPageButton_.addActionListener(this);

        FormLayout layout = new FormLayout(
                "default:grow",
                "$lgap, fill:default, $lgap, fill:default:grow");
        setLayout(layout);
        CellConstraints cc = new CellConstraints();
        add(buildNavigationPanel(), cc.xy(1, 2));
        add(pane, cc.xy(1, 4));
    }

    private JPanel buildNavigationPanel() {
        FormLayout layout = new FormLayout(
                "$lcgap, default, $lcgap, default:grow, " +
                        "$ugap, default, $ugap, 2*(default, $lcgap), " +
                        "30dlu, $lcgap, 3*(default, $lcgap)",
                "fill:default:grow");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(buildCustomWidget(), cc.xy(2, 1));
        builder.add(searchButton_, cc.xy(6, 1));
        builder.add(firstPageButton_, cc.xy(8, 1));
        builder.add(previousPageButton_, cc.xy(10, 1));
        builder.add(currentPageTextField_, cc.xy(12, 1));
        builder.add(new JLabel("/"), cc.xy(13, 1));
        builder.add(totalPageLabel_, cc.xy(14, 1));
        builder.add(nextPageButton_, cc.xy(16, 1));
        builder.add(lastPageButton_, cc.xy(18, 1));
        return builder.getPanel();
    }

    private void wireEvents() {
        currentPageTextField_.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String value = currentPageTextField_.getText();
                    try {
                        int page = Integer.valueOf(value);
                        resultTable_.toPage(page - 1);
                    } catch (NumberFormatException ex) {
                        logger_.error("Can't convert to an integer.", ex);
                    }
                }
            }
        });
    }

    protected JComponent buildCustomWidget() {
        return new JPanel();
    }

    private void updateButtonStatus() {
        int index = resultTable_.getCurrentPageIndex();
        int maxIndex = resultTable_.getMaxPageIndex();
        firstPageButton_.setEnabled(index > 0);
        previousPageButton_.setEnabled(index > 0);
        nextPageButton_.setEnabled(index < maxIndex);
        lastPageButton_.setEnabled(index < maxIndex);
        currentPageTextField_.setText(String.valueOf(index + 1));
        totalPageLabel_.setText(String.valueOf(maxIndex + 1));
    }

    public FilterChangeListener getFilterChangeListener() {
        return resultTable_;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == firstPageButton_) {
            resultTable_.toFirstPage();
        } else if (e.getSource() == previousPageButton_) {
            resultTable_.toPreviousPage();
        } else if (e.getSource() == nextPageButton_) {
            resultTable_.toNextPage();
        } else if (e.getSource() == lastPageButton_) {
            resultTable_.toLastPage();
        } else if (e.getSource() == searchButton_) {
            resultTable_.toPage(resultTable_.getCurrentPageIndex());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == resultTable_) {
            updateButtonStatus();
        }
    }
}