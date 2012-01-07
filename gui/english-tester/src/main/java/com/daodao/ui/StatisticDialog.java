package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.daodao.model.HistoryExamWordDO;
import com.daodao.other.Constants;

public class StatisticDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel rightPanel;
	private JPanel leftPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StatisticDialog dialog = new StatisticDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public StatisticDialog(ActionListener listener) {
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
					leftPanel = new JPanel();
					scrollPane.setViewportView(leftPanel);
					leftPanel.setLayout(new GridLayout(0, 2, 5, 5));

				}
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setViewportBorder(new TitledBorder(null,
						"Correct Words", TitledBorder.LEADING,
						TitledBorder.TOP, null, null));
				splitPane.setRightComponent(scrollPane);
				{
					rightPanel = new JPanel();
					scrollPane.setViewportView(rightPanel);
					rightPanel.setLayout(new GridLayout(0, 2, 5, 5));
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("关闭");
				okButton.setActionCommand(Constants.ACTION_STATISTIC_CLOSE);
				okButton.addActionListener(listener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
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

	public void setData(List<HistoryExamWordDO> correctWords,
			List<HistoryExamWordDO> wrongWords) {
		for (HistoryExamWordDO correctWord : correctWords) {
			leftPanel.add(new JLabel(correctWord.getEn()));
			leftPanel.add(new JLabel(correctWord.getZh()));
		}
		for (HistoryExamWordDO wrongWord : wrongWords) {
			leftPanel.add(new JLabel(wrongWord.getEn()));
			leftPanel.add(new JLabel(wrongWord.getZh()));
		}
	}

}
