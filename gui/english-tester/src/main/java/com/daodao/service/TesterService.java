/**
 * 上午10:50:56
 */
package com.daodao.service;

import java.util.List;

import com.daodao.dao.DictionaryDAO;
import com.daodao.dao.ExamDAO;
import com.daodao.dao.ExamWordDAO;
import com.daodao.dao.HistoryExamWordDAO;
import com.daodao.model.ExamDO;
import com.daodao.model.ExamWordDO;

/**
 * @author zhjdenis
 * 
 */
public class TesterService {

	private ExamDAO examDAO;

	private ExamWordDAO examWordDAO;

	private HistoryExamWordDAO historyExamWordDAO;

	private DictionaryDAO dictionaryDAO;

	public ExamDO startNewTest() {
		return null;
	}

	public ExamDO resumeLastTest() {
		return null;
	}

	public List<ExamWordDO> nextRound(List<ExamWordDO> currentWord) {
		return null;
	}

	public ExamDAO getExamDAO() {
		return examDAO;
	}

	public void setExamDAO(ExamDAO examDAO) {
		this.examDAO = examDAO;
	}

	public ExamWordDAO getExamWordDAO() {
		return examWordDAO;
	}

	public void setExamWordDAO(ExamWordDAO examWordDAO) {
		this.examWordDAO = examWordDAO;
	}

	public HistoryExamWordDAO getHistoryExamWordDAO() {
		return historyExamWordDAO;
	}

	public void setHistoryExamWordDAO(HistoryExamWordDAO historyExamWordDAO) {
		this.historyExamWordDAO = historyExamWordDAO;
	}

	public DictionaryDAO getDictionaryDAO() {
		return dictionaryDAO;
	}

	public void setDictionaryDAO(DictionaryDAO dictionaryDAO) {
		this.dictionaryDAO = dictionaryDAO;
	}

}
