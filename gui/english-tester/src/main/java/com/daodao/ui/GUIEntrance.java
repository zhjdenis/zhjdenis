package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class GUIEntrance {

	private Map<String, String> questions;
	private JFrame frmEnglishTester;
	private JTextField qOneAnswer;
	private JTextField qTwoAnswer;
	private JTextField qThreeAnswer;
	private JTextField qFourAnswer;
	private JLabel qFiveWord;
	private JLabel qOneWord;
	private JLabel qTwoWord;
	private JLabel qThreeWord;
	private JLabel qFourWord;
	private JLabel qOneStatus;
	private JLabel qTwoStatus;
	private JLabel qThreeStatus;
	private JLabel qFourStatus;
	private JTextField qFiveAnswer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIEntrance window = new GUIEntrance();
					window.frmEnglishTester.setVisible(true);
					window.frmEnglishTester
							.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIEntrance() {
		initializeData();
		initializeGUI();
	}

	private void initializeData() {
		questions = new HashMap<String, String>();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUI() {
		frmEnglishTester = new JFrame();
		BorderLayout borderLayout = (BorderLayout) frmEnglishTester
				.getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		frmEnglishTester.setTitle("English Tester");
		frmEnglishTester.setBounds(100, 100, 602, 300);
		frmEnglishTester.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmEnglishTester.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Test");
		menuBar.add(mnNewMenu);

		JMenuItem continueButton = new JMenuItem("Continue...");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		mnNewMenu.add(continueButton);

		JMenuItem restartButton = new JMenuItem("Restart");
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		mnNewMenu.add(restartButton);

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		JMenuItem exitButton = new JMenuItem("Exit");
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);

			}
		});
		mnNewMenu.add(exitButton);

		JMenu mnNewMenu_1 = new JMenu("Statistic");
		menuBar.add(mnNewMenu_1);

		JMenuItem historyButton = new JMenuItem("History");
		mnNewMenu_1.add(historyButton);

		JMenu mnNewMenu_2 = new JMenu("Vocabulary");
		menuBar.add(mnNewMenu_2);

		JMenuItem listButton = new JMenuItem("List");
		mnNewMenu_2.add(listButton);

		JPanel panel = new JPanel();
		frmEnglishTester.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 2, 5, 5));

		qOneWord = new JLabel("");
		panel.add(qOneWord);

		qOneAnswer = new JTextField();
		panel.add(qOneAnswer);
		qOneAnswer.setColumns(10);

		qTwoWord = new JLabel("");
		panel.add(qTwoWord);

		qTwoAnswer = new JTextField();
		panel.add(qTwoAnswer);
		qTwoAnswer.setColumns(10);

		qThreeWord = new JLabel("");
		panel.add(qThreeWord);

		qThreeAnswer = new JTextField();
		panel.add(qThreeAnswer);
		qThreeAnswer.setColumns(10);

		qFourWord = new JLabel("");
		panel.add(qFourWord);

		qFourAnswer = new JTextField();
		panel.add(qFourAnswer);
		qFourAnswer.setColumns(10);

		qFiveWord = new JLabel("");
		panel.add(qFiveWord);

		qFiveAnswer = new JTextField();
		panel.add(qFiveAnswer);
		qFiveAnswer.setColumns(10);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmEnglishTester.getContentPane().add(panel_1, BorderLayout.SOUTH);

		JLabel lblNewLabel_5 = new JLabel("Test:");
		panel_1.add(lblNewLabel_5);

		JLabel testTitle = new JLabel("");
		panel_1.add(testTitle);

		JLabel timeLabelnn = new JLabel("Time:");
		panel_1.add(timeLabelnn);

		JLabel timeLabel = new JLabel("");
		panel_1.add(timeLabel);

		JLabel lblNewLabel_2 = new JLabel("Pass:");
		panel_1.add(lblNewLabel_2);

		JLabel passLabel = new JLabel("");
		panel_1.add(passLabel);

		JLabel lblNewLabel_1 = new JLabel("Fail");
		panel_1.add(lblNewLabel_1);

		JLabel failLabel = new JLabel("");
		panel_1.add(failLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmEnglishTester.getContentPane().add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new GridLayout(2, 1, 20, 20));

		JButton completeButton = new JButton("Complete");
		completeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAnswer();
			}
		});
		panel_2.add(completeButton);

		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextRoundQuestion();
			}
		});
		panel_2.add(nextButton);

		JPanel panel_3 = new JPanel();
		frmEnglishTester.getContentPane().add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new GridLayout(0, 1, 5, 5));

		qOneStatus = new JLabel("Correct");
		panel_3.add(qOneStatus);

		qTwoStatus = new JLabel("Correct");
		panel_3.add(qTwoStatus);

		qThreeStatus = new JLabel("Correct");
		panel_3.add(qThreeStatus);

		qFourStatus = new JLabel("Wrong");
		qFourStatus.setForeground(Color.RED);
		panel_3.add(qFourStatus);

		JLabel qFiveStatus = new JLabel("Correct");
		qFiveStatus.setForeground(Color.GREEN);
		panel_3.add(qFiveStatus);
	}

	/**
	 * check answer
	 */
	private void checkAnswer() {

	}

	private void nextRoundQuestion() {

	}

}
