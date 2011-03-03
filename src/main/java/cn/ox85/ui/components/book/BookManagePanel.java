package cn.ox85.ui.components.book;

import cn.ox85.ui.components.ManagePanel;
import cn.ox85.ui.components.ResultTable;

/**
 * @author alec zhang
 */
public class BookManagePanel extends ManagePanel {
    @Override
    protected ResultTable buildResultTable() {
        return new BookTable();
    }
}