package com.daodao.example.db;

import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.daodao.framework.BaseTest;
import com.daodao.framework.annotation.DaoDaoDBDataSet;

@PrepareForTest(value = {})
public class DBTest extends BaseTest {

	@Test
	@DaoDaoDBDataSet(connection = "", locations = {})
	public void testInsertDB() {

	}
}