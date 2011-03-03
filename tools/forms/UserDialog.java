import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Mon Jan 03 22:46:51 CST 2011
 */



/**
 * @author Richard YU
 */
public class UserDialog extends JDialog {
	public UserDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public UserDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Richard YU
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		JLabel usernameLabel = new JLabel();
		usernameTextField_ = new JTextField();
		JLabel passwordLabel = new JLabel();
		userpasswordField_ = new JPasswordField();
		JLabel userTypeLabel = new JLabel();
		userTypeComboBox_ = new JComboBox();
		JLabel passConfirmLabel = new JLabel();
		confirmPasswordField_ = new JPasswordField();
		JPanel buttonBar = new JPanel();
		okButton_ = new JButton();
		cancelButton_ = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setModal(true);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FormLayout(
					"default, $lcgap, 60dlu:grow, $lcgap, default, $lcgap, 60dlu:grow",
					"default, $lgap, default"));

				//---- usernameLabel ----
				usernameLabel.setText("\u7528\u6237\u540d\uff1a");
				contentPanel.add(usernameLabel, cc.xy(1, 1));
				contentPanel.add(usernameTextField_, cc.xy(3, 1));

				//---- passwordLabel ----
				passwordLabel.setText("\u7528\u6237\u5bc6\u7801\uff1a");
				contentPanel.add(passwordLabel, cc.xy(5, 1));
				contentPanel.add(userpasswordField_, cc.xy(7, 1));

				//---- userTypeLabel ----
				userTypeLabel.setText("\u7528\u6237\u7c7b\u578b\uff1a");
				contentPanel.add(userTypeLabel, cc.xy(1, 3));
				contentPanel.add(userTypeComboBox_, cc.xy(3, 3));

				//---- passConfirmLabel ----
				passConfirmLabel.setText("\u786e\u8ba4\u5bc6\u7801\uff1a");
				contentPanel.add(passConfirmLabel, cc.xy(5, 3));
				contentPanel.add(confirmPasswordField_, cc.xy(7, 3));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					"$glue, $button, $rgap, $button",
					"pref"));

				//---- okButton_ ----
				okButton_.setText("OK");
				buttonBar.add(okButton_, cc.xy(2, 1));

				//---- cancelButton_ ----
				cancelButton_.setText("Cancel");
				buttonBar.add(cancelButton_, cc.xy(4, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(435, 135);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Richard YU
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JTextField usernameTextField_;
	private JPasswordField userpasswordField_;
	private JComboBox userTypeComboBox_;
	private JPasswordField confirmPasswordField_;
	private JButton okButton_;
	private JButton cancelButton_;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
