/**
 * 上午10:50:56
 */
package com.daodao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daodao.dao.DictionaryDAO;
import com.daodao.dao.ExamDAO;
import com.daodao.dao.ExamWordDAO;
import com.daodao.dao.HistoryExamWordDAO;
import com.daodao.model.DictionaryDO;
import com.daodao.model.ExamDO;
import com.daodao.model.ExamWordDO;
import com.daodao.model.HistoryExamWordDO;
import com.daodao.other.Constants;

/**
 * @author zhjdenis
 * 
 */
public class TesterService {

	private static final Logger log = LoggerFactory
			.getLogger(TesterService.class);

	private ExamDAO examDAO;

	private ExamWordDAO examWordDAO;

	private HistoryExamWordDAO historyExamWordDAO;

	private DictionaryDAO dictionaryDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public ExamDO startNewTest(String description) throws Exception {
		long start = System.currentTimeMillis();
		ExamDO result = new ExamDO();
		result.setCorrect(0);
		result.setDate(new Date());
		result.setDescription(description);
		result.setRemain(0);
		result.setWrong(0);
		result.setId(examDAO.saveEntity(result));
		int pageSize = 1000;
		int startPos = 0;
		int totalSize = 0;
		List<DictionaryDO> words = null;
		while ((words = dictionaryDAO.copyWords(startPos, pageSize)) != null
				&& words.size() > 0) {
			totalSize += words.size();
			List<ExamWordDO> examWords = new ArrayList<ExamWordDO>();
			for (DictionaryDO word : words) {
				ExamWordDO examWord = new ExamWordDO();
				examWord.setEn(word.getEn());
				examWord.setExamId(result.getId());
				examWord.setSource(word.getSource());
				examWord.setType(word.getType());
				examWord.setZh(word.getZh());
				examWords.add(examWord);
			}
			List<Long> rids = examWordDAO.batchSaveEntities(examWords);
			if (rids.size() != words.size()) {
				throw new Exception(
						"Error in copying words from dictionary to exam_word");
			}
			startPos += pageSize;
		}
		result.setRemain(totalSize);
		examDAO.updateEntity(result);
		log.info("load " + totalSize + " words to exam:" + result.getId());
		System.out.println("load " + totalSize + " words to exam:"
				+ result.getId());
		System.out.println((System.currentTimeMillis() - start) / 1000);
		return result;
	}

	public List<ExamWordDO> resumeLastTest(Long examId) {
		return examWordDAO.findByFields(null, 0, 5);
	}

	public List<ExamDO> listUncompletedExams() {
		return examDAO.listRemainExams();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ExamWordDO> nextRound(Long examId, List<ExamWordDO> currentWord)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		if (currentWord == null) {
			return examWordDAO.findByFields(params, 0, 5);
		} else {
			ExamDO exam = examDAO.findById(examId);
			List<HistoryExamWordDO> saveEntities = new ArrayList<HistoryExamWordDO>();
			List<Long> deleteIds = new ArrayList<Long>();
			List<DictionaryDO> updateWords = new ArrayList<DictionaryDO>();
			int totalSize = 0;
			int correctSize = 0;
			int wrongSize = 0;
			for (ExamWordDO examWordDO : currentWord) {
				DictionaryDO dictionaryDO = dictionaryDAO.findById(examWordDO
						.getId());
				if (dictionaryDO == null) {
					throw new Exception("Unmatched dictionary id:"
							+ examWordDO.getId());
				}
				updateWords.add(dictionaryDO);
				HistoryExamWordDO historyExamWordDO = new HistoryExamWordDO();
				historyExamWordDO.setEn(examWordDO.getEn());
				historyExamWordDO.setExamId(exam.getId());
				historyExamWordDO.setSource(examWordDO.getSource());
				historyExamWordDO.setStatus(examWordDO.getStatus());
				historyExamWordDO.setType(examWordDO.getType());
				historyExamWordDO.setZh(examWordDO.getZh());
				saveEntities.add(historyExamWordDO);
				deleteIds.add(examWordDO.getId());
				if (examWordDO.getStatus()
						.equals(Constants.WORD_STATUS_CORRECT)) {
					correctSize++;
					dictionaryDO.setAccurate(dictionaryDO.getAccurate()
							.intValue() + 1);

				} else if (examWordDO.getStatus().equals(
						Constants.WORD_STATUS_WRONG)) {
					wrongSize++;
					dictionaryDO.setAccurate(dictionaryDO.getAccurate()
							.intValue() - 1);
				}
				totalSize++;
			}
			historyExamWordDAO.batchSaveEntities(saveEntities);
			examWordDAO.deleteByIds(deleteIds);
			exam.setRemain(exam.getRemain() - totalSize);
			exam.setCorrect(exam.getCorrect() + correctSize);
			exam.setWrong(exam.getWrong() + wrongSize);
			examDAO.updateEntity(exam);
			return examWordDAO.findByFields(params, 0, 5);
		}
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
