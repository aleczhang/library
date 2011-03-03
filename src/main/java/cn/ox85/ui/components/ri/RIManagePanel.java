package cn.ox85.ui.components.ri;

import cn.ox85.models.RI;
import cn.ox85.models.Reader;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.RIMap;
import cn.ox85.sql.maps.ReaderMap;
import cn.ox85.ui.beans.BBRBean;
import cn.ox85.ui.components.ManagePanel;
import cn.ox85.ui.components.ResultTable;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconFeedbackPanel;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

/**
 * @author alec zhang
 */
public class RIManagePanel extends ManagePanel {
    private static final Logger logger_ = LoggerFactory.getLogger(RIManagePanel.class);
    private JTextField barCodeTextField_;

    private ValidationResultModel validationResultModel_;

    @Override
    protected ResultTable buildResultTable() {
        return new RITable();
    }

    @Override
    protected JComponent buildCustomWidget() {
        FormLayout layout = new FormLayout(
                "$lcgap, default, $lcgap, 80dlu, $lcgap, default",
                "fill:default:grow");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        barCodeTextField_ = new JTextField();
        barCodeTextField_.addKeyListener(new RecordKeyAdapter());
        ValidationComponentUtils.setMandatory(barCodeTextField_, true);
        ValidationComponentUtils.setMessageKey(barCodeTextField_, "readerBarCode");
        builder.add(new JLabel(BundleUtil.getString("LBL_ReaderBarCode")), cc.xy(2, 1));
        builder.add(barCodeTextField_, cc.xy(4, 1));
        builder.add(new JButton(new RecordAction()), cc.xy(6, 1));
        validationResultModel_ = new DefaultValidationResultModel();
        return new IconFeedbackPanel(validationResultModel_, builder.getPanel());
    }

    private void addRI() {
        String barCode = barCodeTextField_.getText();
        if (barCode == null || barCode.trim().length() == 0) {
            return;
        }
        SqlSession session = ConnectionFactory.getSession().openSession();
        RI ri = new RI();
        ri.setReaderBarCode(barCode);
        ri.setDatetime(Calendar.getInstance().getTime());
        try {
            ReaderMap readerMap = session.getMapper(ReaderMap.class);
            Reader reader = readerMap.selectReaderByBarCode(barCode);
            ValidationResult result = new ValidationResult();
            if (reader == null) {
                SimpleValidationSupport.addWarning(result,
                        BBRBean.PROPERTYNAME_READERBARCODE,
                        BundleUtil.getString("MSG_ReaderNotExist"));
                validationResultModel_.setResult(result);
                return;
            }
            validationResultModel_.setResult(result);
            ri.setReaderId(reader.getId());
            RIMap riDAO = session.getMapper(RIMap.class);
            int count = riDAO.insertRI(ri);
            logger_.debug(count + " RI inserted.");
            session.commit();
        } finally {
            session.close();
        }
        resultTable_.toFirstPage();
    }

    private class RecordAction extends AbstractAction {

        private RecordAction() {
            super(BundleUtil.getString("BTN_INRecord"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addRI();
        }
    }

    private class RecordKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                addRI();
            }
        }
    }
}
