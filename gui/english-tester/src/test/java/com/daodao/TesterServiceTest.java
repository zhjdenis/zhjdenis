package com.daodao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daodao.model.DictionaryDO;
import com.daodao.model.ExamDO;
import com.daodao.service.TesterService;
import com.daodao.ui.VocabularyDialog.VocabularySearchOption;
import com.daodao.ui.VocabularyDialog.VocabularySearchOption.Sort;

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

	@Test
	public void testFilterWords() {
		VocabularySearchOption option = new VocabularySearchOption("", "IELTS",
				Sort.ACCURATE);
		option.startPos = 0;
		option.pageSize = 5;
		List<DictionaryDO> words = testerService.filterWords(option);
		Assert.assertNotNull(words);
		Assert.assertEquals(5, words.size());
	}

}
