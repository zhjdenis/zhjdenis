package com.daodao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daodao.dao.ExamDAO;
import com.daodao.model.ExamDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../../applicationContext.xml" })
public class ExamDAOTest {

	@Autowired
	private ExamDAO examDAO;

	@Test
	public void testUpdate() {
		ExamDO entity = new ExamDO();
		entity.setCorrect(0);
		Long id = examDAO.saveEntity(entity);
		entity.setId(id);
		entity.setCorrect(2);
		examDAO.updateEntity(entity);
		ExamDO newEntity = examDAO.findById(id);
		Assert.assertEquals(2, newEntity.getCorrect().intValue());
	}

	@Test
	public void testListRemainExams() {
		examDAO.listRemainExams();
	}
}
