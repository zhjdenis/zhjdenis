/**
 * 上午10:50:56
 */
package com.daodao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.daodao.ui.VocabularyDialog.VocabularySearchOption;

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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public ExamDO startNewTest(int wordLevel, String description,
			String source, int expectWordSize) throws Exception {
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
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accurate", wordLevel);
		param.put("source", source);
		int potentialWordCount = dictionaryDAO.getCountByFields(param)
				.intValue();
		while ((words = dictionaryDAO.copyWords(param, startPos, pageSize)) != null
				&& words.size() > 0 && totalSize <= expectWordSize) {

			List<ExamWordDO> examWords = new ArrayList<ExamWordDO>();
			if (potentialWordCount > expectWordSize) {// 为了随机获取单词
				int resultSize = (int) (words.size() * (1.0 * expectWordSize / potentialWordCount)) + 1;
				if (words.size() < pageSize) { // 数据库最后一个分页，需要调整所需单词的数量
					resultSize = expectWordSize - totalSize;
				}
				List<Integer> randomArray = generateRandomList(words.size(),
						resultSize);
				for (int index = 0; index < words.size(); index++) {
					if (!randomArray.contains(index)) {
						continue;
					}
					DictionaryDO word = words.get(index);
					ExamWordDO examWord = new ExamWordDO();
					examWord.setEn(word.getEn());
					examWord.setExamId(result.getId());
					examWord.setSource(word.getSource());
					examWord.setType(word.getType());
					examWord.setZh(word.getZh());
					examWord.setWordId(word.getId());
					examWords.add(examWord);
				}
			} else {// 候选单词不足，所以不需要随机
				for (DictionaryDO word : words) {
					ExamWordDO examWord = new ExamWordDO();
					examWord.setEn(word.getEn());
					examWord.setExamId(result.getId());
					examWord.setSource(word.getSource());
					examWord.setType(word.getType());
					examWord.setZh(word.getZh());
					examWord.setWordId(word.getId());
					examWords.add(examWord);
				}
			}
			List<Long> rids = examWordDAO.batchSaveEntities(examWords);
			totalSize += rids.size();
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

	public List<DictionaryDO> filterWords(VocabularySearchOption option) {
		return dictionaryDAO.findSimilarWords(option);
	}

	public List<ExamWordDO> resumeLastTest(Long examId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("examId", examId);
		return examWordDAO.findByFields(param, 0, 5);
	}

	public List<ExamDO> listUncompletedExams() {
		return examDAO.listRemainExams();
	}

	public List<ExamDO> listHistoryExams() {
		Map<String, String> order = new HashMap<>();
		order.put("id", "desc");
		return examDAO.findAll(order);
	}

	public ExamDO findExamById(Long examId) {
		return examDAO.findById(examId);
	}

	public List<HistoryExamWordDO> findWrongWords(Long examId) {
		Map<String, Object> fields = new HashMap<>();
		fields.put("status", Constants.WORD_STATUS_WRONG);
		fields.put("examId", examId);
		return historyExamWordDAO.findByFields(fields);
	}

	public List<HistoryExamWordDO> findCorrectWords(Long examId) {
		Map<String, Object> fields = new HashMap<>();
		fields.put("status", Constants.WORD_STATUS_CORRECT);
		fields.put("examId", examId);
		return historyExamWordDAO.findByFields(fields);
	}

	/**
	 * 读取下一轮的五个词语
	 * 
	 * @param examId
	 * @param currentWord
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<ExamWordDO> nextRound(Long examId, List<ExamWordDO> currentWord)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		if (currentWord == null) {
			return examWordDAO.findByFields(params, 0, Constants.SIZE_OF_ROUND);
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
						.getWordId());
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
				historyExamWordDO.setWordId(examWordDO.getWordId());
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

	private static List<Integer> generateRandomList(Integer range,
			Integer resultSize) {
		Random random = new Random(System.currentTimeMillis());
		List<Integer> result = new ArrayList<Integer>();
		List<Integer> allSeeds = new ArrayList<Integer>();
		for (int index = 1; index <= range; index++) {
			allSeeds.add(index);
		}
		while (result.size() < resultSize) {
			int seedIndex = Math.abs(random.nextInt() % allSeeds.size());
			result.add(allSeeds.get(seedIndex));
			allSeeds.remove(seedIndex);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public List<Long> batchSaveDictionaryWords(List<DictionaryDO> dictionaryDOs) {
		return dictionaryDAO.batchSaveEntities(dictionaryDOs);
	}

	/**
	 * 返回指定词库和词库单词数字的关系
	 * 
	 * @return
	 */
	public Map<String, Integer> getSourceCountMap() {
		return dictionaryDAO.getSourceCountMap();
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

	public static void main(String[] args) throws InterruptedException {
		for (int times = 0; times < 10; times++) {
			List<Integer> list = generateRandomList(100, 10);
			for (Integer i : list) {
				System.out.print(i + "\t");
			}
			System.out.println();
			Thread.sleep(100);
		}
	}

}
