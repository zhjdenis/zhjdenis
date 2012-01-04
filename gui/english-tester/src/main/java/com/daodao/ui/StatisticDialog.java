package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class StatisticDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StatisticDialog dialog = new StatisticDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public StatisticDialog() {
		setTitle("测试统计");
		setBounds(100, 100, 500, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setOneTouchExpandable(true);
			splitPane.setResizeWeight(0.5);
			contentPanel.add(splitPane);
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setViewportBorder(new TitledBorder(null,
						"Wrong Words", TitledBorder.LEADING, TitledBorder.TOP,
						null, null));
				splitPane.setLeftComponent(scrollPane);
				{
					JPanel panel = new JPanel();
					scrollPane.setViewportView(panel);
					panel.setLayout(new GridLayout(0, 2, 5, 5));
					{
						JLabel lblNewLabel = new JLabel("New label");
						panel.add(lblNewLabel);
					}
					{
						JLabel lblNewLabel_1 = new JLabel("New label");
						panel.add(lblNewLabel_1);
					}
				}
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setViewportBorder(new TitledBorder(null,
						"Correct Words", TitledBorder.LEADING,
						TitledBorder.TOP, null, null));
				splitPane.setRightComponent(scrollPane);
				{
					JPanel panel = new JPanel();
					scrollPane.setViewportView(panel);
					panel.setLayout(new GridLayout(0, 2, 5, 5));
					{
						JLabel lblNewLabel_2 = new JLabel("New label");
						panel.add(lblNewLabel_2);
					}
					{
						JLabel lblNewLabel_3 = new JLabel("New label");
						panel.add(lblNewLabel_3);
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				JLabel testDescriptionLabel = new JLabel("测试描述");
				panel.add(testDescriptionLabel);
			}
			{
				JLabel testDateLabel = new JLabel("时间");
				panel.add(testDateLabel);
			}
			{
				JLabel correctLabel = new JLabel("正确:");
				panel.add(correctLabel);
			}
			{
				JLabel wrongLabel = new JLabel("错误:");
				panel.add(wrongLabel);
			}
		}
	}

}
