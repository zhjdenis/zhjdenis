package com.daodao.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractDAO<K extends Serializable, V extends Serializable>
		extends HibernateDaoSupport {

	public abstract K saveEntity(V entity);

	public abstract List<K> batchSaveEntities(List<V> entities);
}
