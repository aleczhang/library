package cn.ox85.ui.components.bbr;

import cn.ox85.ui.components.ManagePanel;
import cn.ox85.ui.components.ResultTable;

/**
 * @author alec zhang
 */
public class BBRManagePanel extends ManagePanel {
    @Override
    protected ResultTable buildResultTable() {
        return new BBRTable();
    }
}