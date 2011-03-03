package cn.ox85.ui.components;

import cn.ox85.ui.listeners.Filter;
import cn.ox85.ui.listeners.FilterChangeListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutionException;

/**
 * @author alec zhang
 */
public abstract class ResultTable extends JTable implements KeyListener, MouseListener,
        FilterChangeListener {
    public static final String PROPERTYNAME_MODEL = "model";

    protected static final int PAGESIZE = 50;
    protected static final int FIRST_PAGE_INDEX = 0;

    protected int pageMaxIndex_;
    protected int pageCurrentIndex_ = FIRST_PAGE_INDEX;
    protected DefaultTableModel dataModel_;
    protected Filter filter_;

    public ResultTable(DefaultTableModel dataModel, Filter filter) {
        super();
        dataModel_ = dataModel;
        filter_ = filter;
        setModel(dataModel);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        addKeyListener(this);
        addMouseListener(this);
        new ShowResultWork().execute();
    }

    public class ShowResultWork extends SwingWorker<Object[][], Object> {
        @Override
        protected Object[][] doInBackground() throws Exception {
            return getResults();
        }

        @Override
        protected void done() {
            cleanDataModel();
            Object[][] data = new Object[0][];
            try {
                data = get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            for (Object[] aData : data) {
                addRowToDataModel(aData);
            }
            ResultTable.this.firePropertyChange(PROPERTYNAME_MODEL, null, null);
        }
    }

    public int getCurrentPageIndex() {
        return pageCurrentIndex_;
    }

    public int getMaxPageIndex() {
        return pageMaxIndex_;
    }

    public void toFirstPage() {
        pageCurrentIndex_ = 0;
        new ShowResultWork().execute();
    }

    public void toPreviousPage() {
        if (pageCurrentIndex_ > 0)
            pageCurrentIndex_--;
        new ShowResultWork().execute();
    }

    public void toNextPage() {
        if (pageCurrentIndex_ < pageMaxIndex_)
            pageCurrentIndex_++;
        new ShowResultWork().execute();
    }

    public void toLastPage() {
        pageCurrentIndex_ = pageMaxIndex_;
        new ShowResultWork().execute();
    }

    public void toPage(int page) {
        if (page < 0) page = 0;
        if (page > pageMaxIndex_) page = pageMaxIndex_;
        pageCurrentIndex_ = page;
        new ShowResultWork().execute();
    }

    protected abstract Object[][] getResults();

    protected void cleanDataModel() {
        int rowCount = this.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dataModel_.removeRow(i);
        }
    }

    protected void addRowToDataModel(Object[] rowData) {
        dataModel_.addRow(rowData);
    }

    @Override
    public void filterChanged(Filter filter) {
        filter_ = filter;
        toFirstPage();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
