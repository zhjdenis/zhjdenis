package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretListener;

import com.daodao.model.DictionaryDO;
import com.daodao.other.Constants;
import com.daodao.ui.VocabularyDialog.VocabularySearchOption.Sort;

public class VocabularyDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField searchTextField;
	private JPanel wordPanel;
	private JComboBox sourceComboBox;
	private JComboBox sortComboBox;
	private int startPos;
	private Map<String, Integer> sourceCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VocabularyDialog dialog = new VocabularyDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VocabularyDialog(EventListener listener) {
		setTitle("单词表");
		setBounds(100, 100, 500, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new GridLayout(1, 0, 5, 0));
			{
				JLabel lblNewLabel = new JLabel("按词搜索");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel);
			}
			{
				searchTextField = new JTextField();
				searchTextField.addCaretListener((CaretListener) listener);
				panel.add(searchTextField);
				searchTextField.setColumns(10);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("选择词典");
				lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel_1);
			}
			{
				sourceComboBox = new JComboBox();
				sourceComboBox.setName(Constants.JCOMBOBOX_VOCABULARY_SORT);
				sourceComboBox.addItemListener((ItemListener) listener);
				panel.add(sourceComboBox);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("排序");
				lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel_2);
			}
			{
				sortComboBox = new JComboBox();
				sortComboBox.addItem(Sort.ACCURATE);
				sortComboBox.addItem(Sort.ALPHABET);
				sortComboBox.setName(Constants.JCOMBOBOX_VOCABULARY_SOURCE);
				sortComboBox.addItemListener((ItemListener) listener);
				panel.add(sortComboBox);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				wordPanel = new JPanel();
				scrollPane.setViewportView(wordPanel);
				wordPanel.setLayout(new GridLayout(0, 3, 5, 5));
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.SOUTH);
			{
				JButton btnNewButton = new JButton("Pre Page");
				btnNewButton.addActionListener((ActionListener) listener);
				btnNewButton
						.setActionCommand(Constants.ACTION_VOCABULARY_PRE_PAGE);
				panel.add(btnNewButton);
			}
			{
				JButton btnNewButton_1 = new JButton("Next Page");
				btnNewButton_1.addActionListener((ActionListener) listener);
				btnNewButton_1
						.setActionCommand(Constants.ACTION_VOCABULARY_NEXT_PAGE);
				panel.add(btnNewButton_1);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("关闭");
				cancelButton
						.setActionCommand(Constants.ACTION_VOCABULARY_CLOSE);
				cancelButton.addActionListener((ActionListener) listener);
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * 传入显示参数
	 * 
	 * @param dictionaryDOs
	 */
	public void setData(List<DictionaryDO> dictionaryDOs) {
		wordPanel.removeAll();
		for (DictionaryDO dictionaryDO : dictionaryDOs) {
			wordPanel.add(new JLabel(dictionaryDO.getEn()));
			wordPanel.add(new JLabel(dictionaryDO.getZh()));
			wordPanel.add(new JLabel(dictionaryDO.getAccurate().toString()));
		}
		wordPanel.updateUI();
	}

	/**
	 * 获得对单词的过滤条件
	 * 
	 * @return
	 * @throws Exception
	 */
	public VocabularySearchOption getOption() throws Exception {
		if (sourceComboBox.getSelectedItem() == null
				|| "".equals(sourceComboBox.getSelectedItem().toString().trim())) {
			throw new Exception("请至少选择一本词典");
		} else {
			return new VocabularySearchOption(searchTextField.getText(),
					sourceComboBox.getSelectedItem().toString(),
					(Sort) sortComboBox.getSelectedItem());
		}
	}

	/**
	 * 设置词典
	 * 
	 * @param sources
	 */
	public void setSource(Map<String, Integer> sourceCount) {
		this.sourceCount = sourceCount;
		sourceComboBox.removeAllItems();
		sourceComboBox.addItem("");
		for (String source : sourceCount.keySet()) {
			sourceComboBox.addItem(source);
		}
	}

	public static class VocabularySearchOption {

		public static enum Sort {
			ALPHABET("按照字母排序", "en"), ACCURATE("按照准确率排序", "accurate");
			private String label;
			private String value;

			private Sort(String label, String value) {
				this.label = label;
				this.value = value;
			}

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return label;
			}

			public String getValue() {
				return this.value;
			}

			public String getOrderType() {
				return "asc";
			}

		}

		public String word = "";
		public String source;
		public Sort sort;
		public Integer startPos = 0;
		public Integer pageSize = Constants.SIZE_OF_VOCABULARY;

		public VocabularySearchOption(String word, String source, Sort sort) {
			this.word = word;
			this.source = source;
			this.sort = sort;
		}
	}

	public boolean setStartPos(int startPos) {
		if (startPos + Constants.SIZE_OF_VOCABULARY > sourceCount
				.get(sourceComboBox.getSelectedItem().toString())
				|| startPos < 0) {
			return false;
		}
		this.startPos = startPos;
		return true;
	}

	public int getStartPos() {
		return startPos;
	}

}
