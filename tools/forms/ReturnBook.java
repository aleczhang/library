import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sat Jan 15 22:08:35 CST 2011
 */



/**
 * @author Richard YU
 */
public class ReturnBook extends JDialog {
	public ReturnBook(Frame owner) {
		super(owner);
		initComponents();
	}

	public ReturnBook(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Richard YU
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label5 = new JLabel();
		textField2 = new JTextField();
		label2 = new JLabel();
		textField3 = new JTextField();
		label3 = new JLabel();
		textField4 = new JTextField();
		label4 = new JLabel();
		textField5 = new JTextField();
		label6 = new JLabel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("\u8fd8\u4e66");
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
					"default, $lcgap, 80dlu, $lcgap, default, $lcgap, 80dlu",
					"4*(default, $lgap), 80dlu:grow"));

				//---- label1 ----
				label1.setText("\u4e66\u7c4d\u6761\u7801\u53f7\uff1a");
				contentPanel.add(label1, cc.xy(1, 1));
				contentPanel.add(textField1, cc.xy(3, 1));

				//---- label5 ----
				label5.setText("\u4e66\u540d\uff1a");
				contentPanel.add(label5, cc.xy(5, 1));

				//---- textField2 ----
				textField2.setEditable(false);
				contentPanel.add(textField2, cc.xy(7, 1));

				//---- label2 ----
				label2.setText("\u4f1a\u5458\u6761\u7801\u53f7\uff1a");
				contentPanel.add(label2, cc.xy(1, 3));

				//---- textField3 ----
				textField3.setEditable(false);
				contentPanel.add(textField3, cc.xy(3, 3));

				//---- label3 ----
				label3.setText("\u4f1a\u5458\u59d3\u540d\uff1a");
				contentPanel.add(label3, cc.xy(5, 3));

				//---- textField4 ----
				textField4.setEditable(false);
				contentPanel.add(textField4, cc.xy(7, 3));

				//---- label4 ----
				label4.setText("\u4f1a\u5458\u7c7b\u578b\uff1a");
				contentPanel.add(label4, cc.xy(1, 5));

				//---- textField5 ----
				textField5.setEditable(false);
				contentPanel.add(textField5, cc.xy(3, 5));

				//---- label6 ----
				label6.setText("\u4e66\u7c4d\u5217\u8868\uff1a");
				contentPanel.add(label6, cc.xy(1, 7));

				//======== scrollPane1 ========
				{

					//---- table1 ----
					table1.setModel(new DefaultTableModel(
						new Object[][] {
							{null, null, null, null, null, null, null, null},
							{null, null, null, null, null, null, null, null},
						},
						new String[] {
							"ID", "\u8bfb\u8005\u6761\u5f62\u7801", "\u59d3\u540d", "\u4e66\u7c4d\u6761\u7801\u53f7", "\u4e66\u540d", "\u501f\u51fa\u65e5\u671f", "\u5f52\u8fd8\u65e5\u671f", "\u5929\u6570"
						}
					));
					scrollPane1.setViewportView(table1);
				}
				contentPanel.add(scrollPane1, cc.xywh(1, 9, 7, 1));
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					"$glue, $button, $rgap, $button",
					"pref"));

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, cc.xy(2, 1));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, cc.xy(4, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
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
	private JLabel label1;
	private JTextField textField1;
	private JLabel label5;
	private JTextField textField2;
	private JLabel label2;
	private JTextField textField3;
	private JLabel label3;
	private JTextField textField4;
	private JLabel label4;
	private JTextField textField5;
	private JLabel label6;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
