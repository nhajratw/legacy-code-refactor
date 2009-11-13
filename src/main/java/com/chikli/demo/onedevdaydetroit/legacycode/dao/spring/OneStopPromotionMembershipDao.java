/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import java.util.List;

import org.apache.log4j.Logger;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IOneStopPromotionMembershipDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStopPromotionMembership;

/**
 * @author 
 */
public class OneStopPromotionMembershipDao extends BaseSpringDao<OneStopPromotionMembership> implements
IOneStopPromotionMembershipDao {

	private static final Logger LOG = Logger.getLogger(OneStopPromotionMembershipDao.class);

	public OneStopPromotionMembershipDao(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}

	@Override
	public OneStopPromotionMembership findById(Integer pId) {
		throw new RuntimeException("Operation not supported.");
	}

	public OneStopPromotionMembership findById(Integer pPromotionId, Integer pOneStopId) {
		LOG.debug("Find OneStopPromotionMembership instance by id");
		try {
			String lQueryString = "from OneStopPromotionMembership as model where model."
				+ "promotion.promotionId = ? and model.oneStop.id = ?";
			List<OneStopPromotionMembership> lOneStopPromotionMemberships = getHibernateTemplate().find(lQueryString, new Object[]{pPromotionId,pOneStopId});
			if(lOneStopPromotionMemberships == null || lOneStopPromotionMemberships.isEmpty()){
				LOG.debug("none found");
				return null;
			}
			LOG.debug("found a match");
			return lOneStopPromotionMemberships.get(0);
		} catch (RuntimeException lRe) {
			LOG.error("Find OneStopPromotionMembership instance by id failed", lRe);
			throw lRe;
		}
	}

}
