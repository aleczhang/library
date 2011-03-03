import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.michaelbaranov.microba.calendar.*;
/*
 * Created by JFormDesigner on Sun Jan 16 14:43:49 CST 2011
 */



/**
 * @author Richard YU
 */
public class BookDialog extends JDialog {
	public BookDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public BookDialog(Dialog owner) {
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
		label3 = new JLabel();
		textField3 = new JTextField();
		label5 = new JLabel();
		textField12 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		label6 = new JLabel();
		textField11 = new JTextField();
		label4 = new JLabel();
		textField13 = new JTextField();
		label8 = new JLabel();
		spinner3 = new JSpinner();
		label7 = new JLabel();
		datePicker1 = new DatePicker();
		label12 = new JLabel();
		textField15 = new JTextField();
		label9 = new JLabel();
		spinner1 = new JSpinner();
		label13 = new JLabel();
		textField7 = new JTextField();
		label14 = new JLabel();
		textField10 = new JTextField();
		label10 = new JLabel();
		spinner2 = new JSpinner();
		label16 = new JLabel();
		scrollPane2 = new JScrollPane();
		textArea1 = new JTextArea();
		label11 = new JLabel();
		textField6 = new JTextField();
		label15 = new JLabel();
		scrollPane3 = new JScrollPane();
		textArea2 = new JTextArea();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("\u6dfb\u52a0\u4e66\u7c4d");
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
					"default, $lcgap, 50dlu:grow, $rgap, default, $lcgap, 50dlu:grow, $ugap, default, $lcgap, 50dlu:grow",
					"6*(default, $lgap), 20dlu:grow, $lgap, default, $lgap, 20dlu:grow"));

				//---- label1 ----
				label1.setText("\u4e66\u7c4d\u6761\u7801\u53f7\uff1a");
				contentPanel.add(label1, cc.xy(1, 1));
				contentPanel.add(textField1, cc.xy(3, 1));

				//---- label3 ----
				label3.setText("ISBN\uff1a");
				contentPanel.add(label3, cc.xy(5, 1));
				contentPanel.add(textField3, cc.xy(7, 1));

				//---- label5 ----
				label5.setText("\u4f5c\u8005\uff1a");
				contentPanel.add(label5, cc.xy(9, 1));
				contentPanel.add(textField12, cc.xy(11, 1));

				//---- label2 ----
				label2.setText("\u4e66\u540d\uff1a");
				contentPanel.add(label2, cc.xy(1, 3));
				contentPanel.add(textField2, cc.xywh(3, 3, 5, 1));

				//---- label6 ----
				label6.setText("\u8bd1\u8005\uff1a");
				contentPanel.add(label6, cc.xy(9, 3));
				contentPanel.add(textField11, cc.xy(11, 3));

				//---- label4 ----
				label4.setText("\u51fa\u7248\u793e\uff1a");
				contentPanel.add(label4, cc.xy(1, 5));
				contentPanel.add(textField13, cc.xywh(3, 5, 5, 1));

				//---- label8 ----
				label8.setText("\u5b9a\u4ef7\uff1a");
				contentPanel.add(label8, cc.xy(9, 5));

				//---- spinner3 ----
				spinner3.setModel(new SpinnerNumberModel(0.0, null, null, 1.0));
				contentPanel.add(spinner3, cc.xy(11, 5));

				//---- label7 ----
				label7.setText("\u51fa\u7248\u65e5\u671f\uff1a");
				contentPanel.add(label7, cc.xy(1, 7));
				contentPanel.add(datePicker1, cc.xy(3, 7));

				//---- label12 ----
				label12.setText("\u7d22\u4e66\u53f7\uff1a");
				contentPanel.add(label12, cc.xy(5, 7));
				contentPanel.add(textField15, cc.xy(7, 7));

				//---- label9 ----
				label9.setText("\u7248\u6b21\uff1a");
				contentPanel.add(label9, cc.xy(9, 7));

				//---- spinner1 ----
				spinner1.setModel(new SpinnerNumberModel(1, null, null, 1));
				contentPanel.add(spinner1, cc.xy(11, 7));

				//---- label13 ----
				label13.setText("\u4e1b\u4e66\u540d\uff1a");
				contentPanel.add(label13, cc.xy(1, 9));
				contentPanel.add(textField7, cc.xy(3, 9));

				//---- label14 ----
				label14.setText("\u5206\u518c\u53f7\uff1a");
				contentPanel.add(label14, cc.xy(5, 9));
				contentPanel.add(textField10, cc.xy(7, 9));

				//---- label10 ----
				label10.setText("\u9875\u6570\uff1a");
				contentPanel.add(label10, cc.xy(9, 9));
				contentPanel.add(spinner2, cc.xy(11, 9));

				//---- label16 ----
				label16.setText("\u5185\u5bb9\u7b80\u4ecb\uff1a");
				contentPanel.add(label16, cc.xy(1, 11));

				//======== scrollPane2 ========
				{
					scrollPane2.setViewportView(textArea1);
				}
				contentPanel.add(scrollPane2, cc.xywh(3, 11, 5, 3));

				//---- label11 ----
				label11.setText("\u5206\u7c7b\u53f7\uff1a");
				contentPanel.add(label11, cc.xy(9, 11));
				contentPanel.add(textField6, cc.xy(11, 11));

				//---- label15 ----
				label15.setText("\u5907\u6ce8\uff1a");
				contentPanel.add(label15, cc.xy(1, 15));

				//======== scrollPane3 ========
				{
					scrollPane3.setViewportView(textArea2);
				}
				contentPanel.add(scrollPane3, cc.xywh(3, 15, 5, 3));
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
	private JLabel label3;
	private JTextField textField3;
	private JLabel label5;
	private JTextField textField12;
	private JLabel label2;
	private JTextField textField2;
	private JLabel label6;
	private JTextField textField11;
	private JLabel label4;
	private JTextField textField13;
	private JLabel label8;
	private JSpinner spinner3;
	private JLabel label7;
	private DatePicker datePicker1;
	private JLabel label12;
	private JTextField textField15;
	private JLabel label9;
	private JSpinner spinner1;
	private JLabel label13;
	private JTextField textField7;
	private JLabel label14;
	private JTextField textField10;
	private JLabel label10;
	private JSpinner spinner2;
	private JLabel label16;
	private JScrollPane scrollPane2;
	private JTextArea textArea1;
	private JLabel label11;
	private JTextField textField6;
	private JLabel label15;
	private JScrollPane scrollPane3;
	private JTextArea textArea2;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
