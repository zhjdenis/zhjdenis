package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JComboBox sourceComboBox;
	private JSlider wordLevelSlider;
	private JTextField wordNumTextField;
	private Map<String, Integer> sourceCount;

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
		setTitle("新建测试");
		setBounds(100, 100, 429, 240);
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
			JLabel lblNewLabel_2 = new JLabel("词库选择");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewLabel_2);
		}
		{
			sourceComboBox = new JComboBox();
			contentPanel.add(sourceComboBox);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("测试单词的个数");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewLabel_3);
		}
		{
			wordNumTextField = new JTextField();
			contentPanel.add(wordNumTextField);
			wordNumTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("选择测试单词等级");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNewLabel_1);
		}
		{
			wordLevelSlider = new JSlider();
			wordLevelSlider.setMinorTickSpacing(1);
			wordLevelSlider.setMajorTickSpacing(5);
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
				cancelButton.setActionCommand(Constants.ACTION_NEWROUND_CANCEL);
				buttonPane.add(cancelButton);
			}
		}
	}

	public void setSourceCount(final Map<String, Integer> sourceCount) {
		this.sourceCount = sourceCount;
		sourceComboBox.removeAllItems();
		sourceComboBox.addItem("");
		for (String key : this.sourceCount.keySet()) {
			sourceComboBox.addItem(key);
		}
		sourceComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (sourceCount.containsKey(e.getItem().toString())) {
					wordNumTextField.setText(sourceCount.get(
							e.getItem().toString()).toString());
				}
			}
		});
	}

	public void clear() {
		descriptionTextField.setText("");
		wordLevelSlider.setValue(0);
		sourceComboBox.setSelectedIndex(0);
		wordNumTextField.setText("");
	}

	public String getDescription() {
		return descriptionTextField.getText().trim();
	}

	public int getWordLevel() {
		return wordLevelSlider.getValue();
	}

	public int getWordCount() {
		if (wordNumTextField.getText() == null
				|| wordNumTextField.getText().trim().equals("")) {
			return sourceCount.get(sourceComboBox.getSelectedItem().toString());
		} else {
			try {
				return Integer.valueOf(wordNumTextField.getText().trim()) > sourceCount
						.get(sourceComboBox.getSelectedItem().toString()) ? sourceCount
						.get(sourceComboBox.getSelectedItem().toString())
						: Integer.valueOf(wordNumTextField.getText().trim());
			} catch (NumberFormatException e) {
				return sourceCount.get(sourceComboBox.getSelectedItem()
						.toString());
			}
		}
	}

	public String getSource() {
		return sourceComboBox.getSelectedItem().toString();
	}

}
