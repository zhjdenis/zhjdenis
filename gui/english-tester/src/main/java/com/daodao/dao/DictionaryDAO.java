package com.daodao.dao;

import com.daodao.model.DictionaryDO;

public class DictionaryDAO extends AbstractDAO<Long, DictionaryDO> {

	@Override
	protected String getTableName() {
		return this.getClass().getName();
	}

}
