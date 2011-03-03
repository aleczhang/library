package cn.ox85.ui.components;

import cn.ox85.ui.beans.LoginBean;
import cn.ox85.ui.util.BundleUtil;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    public StatusBar() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(10, 23));

        JLabel userLabel = new JLabel(BundleUtil.getString("LBL_CurrentUser",
                LoginBean.getCurrentUser().getUsername()));
        FormLayout layout = new FormLayout(
                "default:grow, $lcgap, default",
                "default:grow");
        CellConstraints cc = new CellConstraints();
        PanelBuilder pbuilder = new PanelBuilder(layout);
        pbuilder.add(userLabel, cc.xy(3, 1));
        add(pbuilder.getPanel(), BorderLayout.CENTER);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int y = 0;
        g.setColor(new Color(156, 154, 140));
        g.drawLine(0, y, getWidth(), y);
        y++;
        g.setColor(new Color(196, 194, 183));
        g.drawLine(0, y, getWidth(), y);
        y++;
        g.setColor(new Color(218, 215, 201));
        g.drawLine(0, y, getWidth(), y);
        y++;
        g.setColor(new Color(233, 231, 217));
        g.drawLine(0, y, getWidth(), y);

        y = getHeight() - 3;
        g.setColor(new Color(233, 232, 218));
        g.drawLine(0, y, getWidth(), y);
        y++;
        g.setColor(new Color(233, 231, 216));
        g.drawLine(0, y, getWidth(), y);
        y = getHeight() - 1;
        g.setColor(new Color(221, 221, 220));
        g.drawLine(0, y, getWidth(), y);
    }
}