package com.daodao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daodao.dao.ExamDAO;
import com.daodao.dao.ExamWordDAO;
import com.daodao.model.ExamDO;
import com.daodao.model.ExamWordDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../../applicationContext.xml" })
public class ExamWordDAOTest {

	@Autowired
	private ExamWordDAO examWordDAO;
	@Autowired
	private ExamDAO examDAO;

	@Test
	public void testFindByField() {
		ExamDO examDO = new ExamDO();
		Long examId = examDAO.saveEntity(examDO);
		examDO = examDAO.findById(examId);
		ExamWordDO examWordDO = new ExamWordDO();
		examWordDO.setExamId(examDO.getId());
		Long wordId = examWordDAO.saveEntity(examWordDO);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("examId", examId);
		List<ExamWordDO> result = examWordDAO.findByFields(param, 0, 5);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(wordId, result.get(0).getId());
	}
}
