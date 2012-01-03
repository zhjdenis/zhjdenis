package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.daodao.model.ExamDO;
import com.daodao.other.Constants;

public class ContinueTestDialog extends JDialog {

	private List<ExamDO> examDOs;
	private ButtonGroup group;
	private JPanel centerPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ContinueTestDialog dialog = new ContinueTestDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ContinueTestDialog(ActionListener listener) {
		setTitle("未完成的测试");
		setBounds(100, 100, 600, 250);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确认");
				okButton.setActionCommand(Constants.ACTION_CONTINUETEST_OK);
				okButton.addActionListener(listener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton
						.setActionCommand(Constants.ACTION_CONTINUETEST_CANCEL);
				cancelButton.addActionListener(listener);
				buttonPane.add(cancelButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			JPanel panel = new JPanel();
			group = new ButtonGroup();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				scrollPane.setViewportView(panel);
			}
			panel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1, BorderLayout.NORTH);
				panel_1.setLayout(new GridLayout(0, 5, 0, 0));
				{
					JLabel lblNewLabel_1 = new JLabel("Date");
					panel_1.add(lblNewLabel_1);
					lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
					lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					JLabel lblNewLabel = new JLabel("Description");
					panel_1.add(lblNewLabel);
					lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					JLabel lblNewLabel_2 = new JLabel("Exam Status");
					panel_1.add(lblNewLabel_2);
					lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 16));
					lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
				}
				{
					JLabel lblNewLabel_4 = new JLabel("Options");
					lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 16));
					lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
					panel_1.add(lblNewLabel_4);
				}
				{
					JLabel lblNewLabel_3 = new JLabel("Operation");
					panel_1.add(lblNewLabel_3);
					lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 16));
					lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
				}
			}
			{
				centerPanel = new JPanel();
				panel.add(centerPanel, BorderLayout.CENTER);
				centerPanel.setLayout(new GridLayout(0, 5, 5, 5));
			}
		}
	}

	public void setExams(List<ExamDO> examDOs) {
		this.examDOs = examDOs;
		refreshPanel();
	}

	private void refreshPanel() {
		centerPanel.removeAll();
		group.clearSelection();
		for (ExamDO examDO : examDOs) {
			JLabel labelOne = new JLabel(Constants.dateFormat(examDO.getDate()));
			labelOne.setHorizontalAlignment(SwingConstants.CENTER);
			centerPanel.add(labelOne);
			JLabel labelTwo = new JLabel(examDO.getDescription());
			centerPanel.add(labelTwo);
			JLabel labelThree = new JLabel("remain:" + examDO.getRemain());
			centerPanel.add(labelThree);
			JLabel labelFour = new JLabel(examDO.getOptions());
			centerPanel.add(labelFour);
			JRadioButton opButton = new JRadioButton("Continue");
			opButton.setActionCommand(examDO.getId().toString());
			group.add(opButton);
			opButton.setHorizontalAlignment(SwingConstants.CENTER);
			centerPanel.add(opButton);
		}
	}

	public Long getSelectExamId() {
		if (group.getSelection() == null) {
			return null;
		}
		return Long.valueOf(group.getSelection().getActionCommand());
	}

}
