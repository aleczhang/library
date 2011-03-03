import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Mon Dec 27 23:18:08 CST 2010
 */



/**
 * @author Richard YU
 */
public class Login extends JFrame {
	public Login() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Richard YU
		JLabel loginLabel = new JLabel();
		JLabel userLabel = new JLabel();
		userTextField_ = new JTextField();
		JLabel passwordLabel = new JLabel();
		passwordField_ = new JPasswordField();
		JPanel buttonPanel = new JPanel();
		loginButton_ = new JButton();
		cancelButton_ = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("\u7cfb\u7edf\u767b\u5f55");
		setResizable(false);
		setIconImage(new ImageIcon("D:\\dev\\azhang\\library\\src\\main\\resources\\cn\\ox85\\ui\\resources\\icon32\\main.png").getImage());
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(
			"$ugap, default, $lcgap, default:grow, $ugap",
			"default, $pgap, default, $lgap, default, $pgap, fill:default:grow, $ugap"));

		//---- loginLabel ----
		loginLabel.setIcon(new ImageIcon("D:\\dev\\azhang\\library\\tools\\resources\\login.png"));
		contentPane.add(loginLabel, cc.xywh(1, 1, 5, 1));

		//---- userLabel ----
		userLabel.setText("\u7528\u6237\u540d\uff1a");
		contentPane.add(userLabel, cc.xy(2, 3));
		contentPane.add(userTextField_, cc.xy(4, 3));

		//---- passwordLabel ----
		passwordLabel.setText("\u5bc6\u7801\uff1a");
		contentPane.add(passwordLabel, cc.xy(2, 5));
		contentPane.add(passwordField_, cc.xy(4, 5));

		//======== buttonPanel ========
		{

			// JFormDesigner evaluation mark
			buttonPanel.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), buttonPanel.getBorder())); buttonPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			buttonPanel.setLayout(new FormLayout(
				"default:grow, 2*($lcgap, 30dlu)",
				"default"));

			//---- loginButton_ ----
			loginButton_.setText("\u767b\u5f55");
			buttonPanel.add(loginButton_, cc.xy(3, 1));

			//---- cancelButton_ ----
			cancelButton_.setText("\u53d6\u6d88");
			buttonPanel.add(cancelButton_, cc.xy(5, 1));
		}
		contentPane.add(buttonPanel, cc.xywh(1, 7, 4, 1));
		setSize(305, 200);
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Richard YU
	private JTextField userTextField_;
	private JPasswordField passwordField_;
	private JButton loginButton_;
	private JButton cancelButton_;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
