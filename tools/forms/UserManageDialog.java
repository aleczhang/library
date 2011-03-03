import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Mon Jan 03 21:00:15 CST 2011
 */



/**
 * @author Richard YU
 */
public class UserManageDialog extends JDialog {
	public UserManageDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public UserManageDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Richard YU
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
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
				contentPanel.setBorder(new TitledBorder("Users"));
				contentPanel.setLayout(new FormLayout(
					"default:grow, $lcgap, 80px",
					"3*(default, $lgap), default:grow"));

				//---- button1 ----
				button1.setText("Add");
				contentPanel.add(button1, cc.xy(3, 1));

				//---- button2 ----
				button2.setText("Modify");
				contentPanel.add(button2, cc.xy(3, 3));

				//---- button3 ----
				button3.setText("Delete");
				contentPanel.add(button3, cc.xy(3, 5));

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(table1);
				}
				contentPanel.add(scrollPane1, cc.xywh(1, 1, 1, 7));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Richard YU
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JScrollPane scrollPane1;
	private JTable table1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
