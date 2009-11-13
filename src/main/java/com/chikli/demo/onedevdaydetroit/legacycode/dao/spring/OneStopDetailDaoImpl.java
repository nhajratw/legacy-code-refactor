package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IOneStopDetailDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStopDetail;

public class OneStopDetailDaoImpl extends BaseSpringDao<OneStopDetail> implements IOneStopDetailDao {

	public OneStopDetailDaoImpl() {
		super(OneStopDetail.class, "OneStopDetail");
	}
	public OneStopDetailDaoImpl(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}

}
