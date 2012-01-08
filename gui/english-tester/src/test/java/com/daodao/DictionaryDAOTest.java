package com.daodao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daodao.dao.DictionaryDAO;
import com.daodao.model.DictionaryDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../../applicationContext.xml" })
public class DictionaryDAOTest {

	@Autowired
	private DictionaryDAO dictionaryDAO;

	@Test
	public void testSave() {
		DictionaryDO a = new DictionaryDO();
		a.setEn("test1");
		Long id = dictionaryDAO.saveEntity(a);
		assertThat(id, greaterThan(0l));
		System.out.println(id);
		DictionaryDO b = dictionaryDAO.findById(id);
		System.out.println(b.getEn());
	}

	@Test
	public void testFindById() {
		DictionaryDO b = dictionaryDAO.findById(5L);
		System.out.println(b.getEn());
	}

	@Test
	public void testFindNextRound() {
		List<DictionaryDO> data = dictionaryDAO.findByFields(null, 0, 5);
		for (DictionaryDO d : data) {
			System.out.println(d.getId() + "\t" + d.getEn() + "\t" + d.getZh());
		}
		data = dictionaryDAO.findByFields(null, 5, 5);
		for (DictionaryDO d : data) {
			System.out.println(d.getId() + "\t" + d.getEn() + "\t" + d.getZh());
		}
	}

	@Test
	public void testDeleteById() {
		long total = dictionaryDAO.countAll();
		DictionaryDO a = new DictionaryDO();
		a.setEn("test1");
		Long id = dictionaryDAO.saveEntity(a);
		assertThat(id, greaterThan(0l));
		Assert.assertEquals(total + 1, dictionaryDAO.countAll());
		dictionaryDAO.deleteById(id);
		Assert.assertEquals(total, dictionaryDAO.countAll());
	}

	@Test
	public void testGetCountByFields() {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("accurate", 0);
		fields.put("source", "IELTS");
		Assert.assertEquals(4536, dictionaryDAO.getCountByFields(fields)
				.intValue());
	}

}
