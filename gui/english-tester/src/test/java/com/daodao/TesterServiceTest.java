package com.daodao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daodao.model.ExamDO;
import com.daodao.service.TesterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../../applicationContext.xml" })
public class TesterServiceTest {

	@Autowired
	private TesterService testerService;

	@Test
	public void testStartNewTest() {
		try {
			ExamDO examDO = testerService.startNewTest(-10, "test", "IELTS",
					10000);
			Assert.assertNotNull(examDO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
