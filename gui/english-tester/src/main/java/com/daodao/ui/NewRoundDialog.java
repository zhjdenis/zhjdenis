package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.daodao.other.Constants;

public class NewRoundDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField descriptionTextField;
	private JSlider wordLevelSlider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewRoundDialog dialog = new NewRoundDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewRoundDialog(ActionListener listener) {
		setBounds(100, 100, 429, 160);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 5, 5));
		{
			JLabel lblNewLabel = new JLabel("关于这次考试的描述，以备日后查询");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewLabel);
		}
		{
			descriptionTextField = new JTextField();
			contentPanel.add(descriptionTextField);
			descriptionTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("选择测试单词等级");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewLabel_1);
		}
		{
			wordLevelSlider = new JSlider();
			wordLevelSlider.setPaintTicks(true);
			wordLevelSlider.setPaintLabels(true);
			wordLevelSlider.setMinimum(-10);
			wordLevelSlider.setMaximum(10);
			wordLevelSlider.setValue(0);
			contentPanel.add(wordLevelSlider);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确认");
				okButton.setActionCommand(Constants.ACTION_NEWROUND_OK);
				okButton.addActionListener(listener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(listener);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public String getDescription() {
		return descriptionTextField.getText().trim();
	}

	public int getWordLevel() {
		return wordLevelSlider.getValue();
	}

}
