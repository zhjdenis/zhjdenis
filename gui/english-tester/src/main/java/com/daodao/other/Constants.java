package com.daodao.other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

	public static final short WORD_STATUS_CORRECT = 1;
	public static final short WORD_STATUS_WRONG = 2;
	public static final short WORD_STATUS_UNANSWER = 3;
	public static final int SIZE_OF_ROUND = 5;

	public static final String ACTION_NEWROUND_OK = "new_round_dialog_ok";
	public static final String ACTION_NEWROUND_CANCEL = "new_round_dialog_cancel";
	public static final String ACTION_SELECT_CONTINUETEST = "select_continue_test";
	public static final String ACTION_SELECT_STATISTIC = "select_statistic_test";
	public static final String ACTION_CONTINUETEST_CANCEL = "select_test_dialog_cancel";
	public static final String ACTION_STATISTIC_CLOSE = "statistic_close";

	public static final String ACTION_MAIN_CONTINUE = "main_continue";
	public static final String ACTION_MAIN_RESTART = "main_restart";
	public static final String ACTION_MAIN_FRESH = "main_fresh";
	public static final String ACTION_MAIN_EXIT = "main_exit";
	public static final String ACTION_MAIN_CHECK = "main_check";
	public static final String ACTION_MAIN_NEXT = "main_next";
	public static final String ACTION_MAIN_HISTORY = "main_history";
	public static final String ACTION_MAIN_CURRENT = "main_current";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static String dateFormat(Date date) {
		return dateFormat.format(date);
	}
}
