package com.daodao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daodao.dao.DictionaryDAO;
import com.daodao.model.DictionaryDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../../applicationContext.xml" })
public class DictionaryDAOTest
{

    @Autowired
    private DictionaryDAO dictionaryDAO;

    @Test
    public void testSave()
    {
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
}
