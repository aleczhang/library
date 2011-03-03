package cn.ox85.ui.components.reader;

import cn.ox85.ui.components.ManagePanel;
import cn.ox85.ui.components.ResultTable;

/**
 * @author alec zhang
 */
public class ReaderManagePanel extends ManagePanel {

    @Override
    protected ResultTable buildResultTable() {
        return new ReaderTable();
    }
}