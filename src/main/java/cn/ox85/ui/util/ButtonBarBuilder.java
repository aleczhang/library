package cn.ox85.ui.util;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.Sizes;

import javax.swing.*;

/**
 * @author alec zhang
 */
public class ButtonBarBuilder extends ButtonBarBuilder2 {
    public static final ColumnSpec BUTTON_COLSPEC =
            new ColumnSpec(Sizes.bounded(Sizes.PREFERRED,
                    Sizes.dluX(40),
                    null));

    @Override
    public void addButton(JComponent button) {
        button.putClientProperty(NARROW_KEY, Boolean.TRUE);
        getLayout().appendColumn(BUTTON_COLSPEC);
        add(button);
        nextColumn();
    }
}
