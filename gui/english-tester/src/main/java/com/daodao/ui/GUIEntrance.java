package com.daodao.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daodao.model.ExamDO;
import com.daodao.model.ExamWordDO;
import com.daodao.other.Constants;
import com.daodao.service.TesterService;

public class GUIEntrance implements ActionListener
{

    private List<ExamWordDO>     examWordDOs;
    private List<JLabel>         allQuestions;
    private List<JTextField>     allAnswers;
    private List<JLabel>         allStatus;
    private Date                 startDate;
    private ExamDO               examDO;
    private TesterService        testerService;
    private JFrame               frmEnglishTester;
    private JTextField           qOneAnswer;
    private JTextField           qTwoAnswer;
    private JTextField           qThreeAnswer;
    private JTextField           qFourAnswer;
    private JLabel               qFiveWord;
    private JLabel               qOneWord;
    private JLabel               qTwoWord;
    private JLabel               qThreeWord;
    private JLabel               qFourWord;
    private JLabel               qOneStatus;
    private JLabel               qTwoStatus;
    private JLabel               qThreeStatus;
    private JLabel               qFourStatus;
    private JLabel               qFiveStatus;
    private JTextField           qFiveAnswer;
    private JButton              nextButton;
    private JButton              checkButton;
    private NewRoundDialog       newRoundDialog;
    private ContinueTestDialog   continueTestDialog;
    private JLabel               testTitle;
    private JLabel               timeLabel;
    private JLabel               passLabel;
    private JLabel               failLabel;
    private JLabel               remainLabel;
    private JButton              skipButton;
    private JMenuItem            mntmNewMenuItem;
    private Map<String, Integer> sourceCount;
    private JMenuItem            mntmNewMenuItem_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    GUIEntrance window = new GUIEntrance();
                    window.frmEnglishTester.setVisible(true);
                    window.frmEnglishTester.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUIEntrance()
    {
        initializeData();
        initializeGUI();
        initializeDialogs();
    }

    private void initializeData()
    {
        examWordDOs = new ArrayList<ExamWordDO>();
        allQuestions = new ArrayList<JLabel>();
        allAnswers = new ArrayList<JTextField>();
        allStatus = new ArrayList<JLabel>();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        testerService = (TesterService) context.getBean("testerService");
        sourceCount = testerService.getSourceCountMap();

    }

    private void initializeDialogs()
    {
        newRoundDialog = new NewRoundDialog(this);
        newRoundDialog.setSourceCount(sourceCount);
        newRoundDialog.setModal(true);
        continueTestDialog = new ContinueTestDialog(this);
        continueTestDialog.setModal(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initializeGUI()
    {
        frmEnglishTester = new JFrame();
        BorderLayout borderLayout = (BorderLayout) frmEnglishTester.getContentPane().getLayout();
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
        continueButton.setActionCommand(Constants.ACTION_MAIN_CONTINUE);
        continueButton.addActionListener(this);
        mnNewMenu.add(continueButton);

        JMenuItem restartButton = new JMenuItem("Restart");
        restartButton.setActionCommand(Constants.ACTION_MAIN_RESTART);
        restartButton.addActionListener(this);
        mnNewMenu.add(restartButton);

        mntmNewMenuItem = new JMenuItem("Fresh");
        mntmNewMenuItem.setActionCommand(Constants.ACTION_MAIN_FRESH);
        mnNewMenu.add(mntmNewMenuItem);

        JSeparator separator = new JSeparator();
        mnNewMenu.add(separator);

        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.setActionCommand(Constants.ACTION_MAIN_EXIT);
        exitButton.addActionListener(this);
        mnNewMenu.add(exitButton);

        JMenu mnNewMenu_1 = new JMenu("Statistic");
        mnNewMenu_1.setEnabled(false);
        menuBar.add(mnNewMenu_1);

        JMenuItem historyButton = new JMenuItem("History");
        mnNewMenu_1.add(historyButton);

        JMenu mnNewMenu_2 = new JMenu("Vocabulary");
        menuBar.add(mnNewMenu_2);

        mntmNewMenuItem_1 = new JMenuItem("Import");
        mnNewMenu_2.add(mntmNewMenuItem_1);

        JMenuItem listButton = new JMenuItem("List");
        mnNewMenu_2.add(listButton);

        JPanel panel = new JPanel();
        frmEnglishTester.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(5, 2, 5, 5));

        qOneWord = new JLabel("");
        allQuestions.add(qOneWord);
        panel.add(qOneWord);

        qOneAnswer = new JTextField();
        allAnswers.add(qOneAnswer);
        panel.add(qOneAnswer);
        qOneAnswer.setColumns(10);

        qTwoWord = new JLabel("");
        allQuestions.add(qTwoWord);
        panel.add(qTwoWord);

        qTwoAnswer = new JTextField();
        allAnswers.add(qTwoAnswer);
        panel.add(qTwoAnswer);
        qTwoAnswer.setColumns(10);

        qThreeWord = new JLabel("");
        allQuestions.add(qThreeWord);
        panel.add(qThreeWord);

        qThreeAnswer = new JTextField();
        allAnswers.add(qThreeAnswer);
        panel.add(qThreeAnswer);
        qThreeAnswer.setColumns(10);

        qFourWord = new JLabel("");
        allQuestions.add(qFourWord);
        panel.add(qFourWord);

        qFourAnswer = new JTextField();
        allAnswers.add(qFourAnswer);
        panel.add(qFourAnswer);
        qFourAnswer.setColumns(10);

        qFiveWord = new JLabel("");
        allQuestions.add(qFiveWord);
        panel.add(qFiveWord);

        qFiveAnswer = new JTextField();
        allAnswers.add(qFiveAnswer);
        panel.add(qFiveAnswer);
        qFiveAnswer.setColumns(10);

        JPanel panel_1 = new JPanel();
        frmEnglishTester.getContentPane().add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new GridLayout(0, 10, 0, 0));

        JLabel lblNewLabel_5 = new JLabel("Test:");
        panel_1.add(lblNewLabel_5);

        testTitle = new JLabel("");
        panel_1.add(testTitle);

        JLabel timeLabelnn = new JLabel("Time:");
        panel_1.add(timeLabelnn);

        timeLabel = new JLabel("");
        panel_1.add(timeLabel);

        JLabel lblNewLabel = new JLabel("Remain:");
        panel_1.add(lblNewLabel);

        remainLabel = new JLabel("");
        panel_1.add(remainLabel);

        JLabel lblNewLabel_2 = new JLabel("Pass:");
        panel_1.add(lblNewLabel_2);

        passLabel = new JLabel("");
        panel_1.add(passLabel);

        JLabel lblNewLabel_1 = new JLabel("Fail:");
        panel_1.add(lblNewLabel_1);

        failLabel = new JLabel("");
        panel_1.add(failLabel);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        frmEnglishTester.getContentPane().add(panel_2, BorderLayout.EAST);
        panel_2.setLayout(new GridLayout(3, 1, 20, 20));

        checkButton = new JButton("Check");
        checkButton.setActionCommand(Constants.ACTION_MAIN_CHECK);
        checkButton.addActionListener(this);
        panel_2.add(checkButton);

        nextButton = new JButton("Next");
        nextButton.setActionCommand(Constants.ACTION_MAIN_NEXT);
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);
        panel_2.add(nextButton);

        skipButton = new JButton("Skip");
        skipButton.setEnabled(false);
        panel_2.add(skipButton);

        JPanel panel_3 = new JPanel();
        frmEnglishTester.getContentPane().add(panel_3, BorderLayout.WEST);
        panel_3.setLayout(new GridLayout(0, 1, 5, 5));

        qOneStatus = new JLabel("");
        allStatus.add(qOneStatus);
        panel_3.add(qOneStatus);

        qTwoStatus = new JLabel("");
        allStatus.add(qTwoStatus);
        panel_3.add(qTwoStatus);

        qThreeStatus = new JLabel("");
        allStatus.add(qThreeStatus);
        panel_3.add(qThreeStatus);

        qFourStatus = new JLabel("");
        allStatus.add(qFourStatus);
        panel_3.add(qFourStatus);

        qFiveStatus = new JLabel("");
        allStatus.add(qFiveStatus);
        panel_3.add(qFiveStatus);
    }

    /**
     * 检测单词填写的正确性
     * 
     * @throws Exception
     */
    private void checkAnswer() throws Exception
    {
        for (int index = 0; index < examWordDOs.size(); index++)
        {
            ExamWordDO word = examWordDOs.get(index);
            JTextField answer = allAnswers.get(index);
            JLabel label = allStatus.get(index);
            if (answer.getText() == null || answer.getText().trim().length() == 0)
            {
                throw new Exception("所有答案都必须填写");
            }
            if (answer.getText().trim().equals(word.getEn()))
            {
                label.setForeground(Color.GREEN);
                label.setText("正确");
                word.setStatus(Constants.WORD_STATUS_CORRECT);
                answer.setForeground(Color.GREEN);
                examDO.setCorrect(examDO.getCorrect().intValue() + 1);
            }
            else
            {
                label.setForeground(Color.RED);
                label.setText("错误");
                word.setStatus(Constants.WORD_STATUS_WRONG);
                answer.setForeground(Color.RED);
                answer.setText(word.getEn());
                examDO.setWrong(examDO.getWrong().intValue() + 1);
            }
            examDO.setRemain(examDO.getRemain().intValue() - 1);
        }
        nextButton.setEnabled(true);
        checkButton.setEnabled(false);
    }

    /**
     * 读取下一轮测试单词
     * 
     * @throws Exception
     */
    private void nextRoundQuestion() throws Exception
    {
        examWordDOs = testerService.nextRound(examDO.getId(), examWordDOs);
        showTestWords();
    }

    /**
     * 退出系统
     */
    private void exitSystem()
    {
        if (showQuestionDialog("退出系统"))
        {
            System.exit(-1);
        }
    }

    /**
     * 列出所有未完成的测试
     */
    private void listLastTests()
    {
        List<ExamDO> unCompleteExam = testerService.listUncompletedExams();
        continueTestDialog.setExams(unCompleteExam, "继续未完成的测试", Constants.ACTION_CONTINUETEST_OK);
        continueTestDialog.setVisible(true);
    }

    /**
     * 继续上一次的测试
     * 
     * @throws Exception
     */
    private void continueLastTests() throws Exception
    {
        Long examId = continueTestDialog.getSelectExamId();
        if (examId != null)
        {
            examWordDOs = testerService.resumeLastTest(examId);
            examDO = testerService.findExamById(examId);
            continueTestDialog.setVisible(false);
            startDate = new Date();
            showTestWords();
        }
        else
        {
            showAlertDialog("请至少选择一个未完成的测试");
        }
    }

    /**
     * 展示指定examId的测试统计数据
     */
    private void showDetailtStatistic(Long examId)
    {

    }

    /**
     * 显示新的单词
     * 
     * @throws Exception
     */
    private void showTestWords()
    {
        if (examWordDOs == null && examWordDOs.size() == 0 && examDO.getRemain().intValue() <= 0)
        {
            showDetailtStatistic(examDO.getId());
        }
        for (int index = 0; index < examWordDOs.size(); index++)
        {
            ExamWordDO word = examWordDOs.get(index);
            JLabel question = allQuestions.get(index);
            JTextField answer = allAnswers.get(index);
            JLabel label = allStatus.get(index);
            label.setText("");
            answer.setText("");
            answer.setForeground(question.getForeground());
            question.setText(word.getZh());
        }
        nextButton.setEnabled(false);
        checkButton.setEnabled(true);
        refreshExamStatus();
    }

    /**
     * 获取关于新测试的信息
     */
    private void restartNewTest()
    {
        newRoundDialog.setVisible(false);
        String description = newRoundDialog.getDescription();
        int wordLevel = newRoundDialog.getWordLevel();
        try
        {
            // showAlertDialog("初始化新的测试，请稍等，初始化完成后会弹出提示");
            examDO = testerService.startNewTest(wordLevel, description, newRoundDialog.getSource(),
                                                newRoundDialog.getWordCount());
            startDate = new Date();
            refreshExamStatus();
            examWordDOs = testerService.nextRound(examDO.getId(), null);
            showAlertDialog("初始化完成，请开始测试");
            showTestWords();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            showAlertDialog(e.getMessage());
        }
    }

    /**
     * 弹出警告窗口
     * 
     * @param msg
     */
    private void showAlertDialog(String msg)
    {
        JOptionPane.showMessageDialog(frmEnglishTester, msg, "提示信息", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * 弹出提问窗口
     * 
     * @param msg
     * @return
     */
    private boolean showQuestionDialog(String msg)
    {
        int result = JOptionPane.showConfirmDialog(frmEnglishTester, msg, "", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            switch (e.getActionCommand())
            {
                case Constants.ACTION_MAIN_CHECK:
                    checkAnswer();
                    break;
                case Constants.ACTION_MAIN_CONTINUE:
                    listLastTests();
                    break;
                case Constants.ACTION_MAIN_EXIT:
                    exitSystem();
                    break;
                case Constants.ACTION_MAIN_NEXT:
                    nextRoundQuestion();
                    break;
                case Constants.ACTION_MAIN_RESTART:
                    newRoundDialog.setVisible(true);
                    break;
                case Constants.ACTION_NEWROUND_CANCEL:
                    newRoundDialog.setVisible(false);
                    break;
                case Constants.ACTION_NEWROUND_OK:
                    restartNewTest();
                    break;
                case Constants.ACTION_CONTINUETEST_OK:
                    continueLastTests();
                    break;
                case Constants.ACTION_CONTINUETEST_CANCEL:
                    continueTestDialog.setVisible(false);
                    break;
                case Constants.ACTION_MAIN_FRESH:
                    showTestWords();
                    refreshExamStatus();
                    break;
                default:
                    break;
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            showAlertDialog(e1.getMessage());
        }
    }

    private void refreshExamStatus()
    {
        testTitle.setText(examDO.getDescription());
        remainLabel.setText(examDO.getRemain().toString());
        timeLabel.setText(((new Date().getTime()) - startDate.getTime()) / 1000 + "s");
        passLabel.setText(examDO.getCorrect().toString());
        failLabel.setText(examDO.getWrong().toString());
    }

}
