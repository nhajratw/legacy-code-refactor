/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IUpcIngredientDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.UpcIngredient;

/**
 * @author
 *
 */
public class UpcIngredientDao extends BaseSpringDao<UpcIngredient> implements IUpcIngredientDao {

	private static final Logger LOG = Logger.getLogger(UpcIngredient.class);

	public UpcIngredientDao(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}

	public void saveOrUpdateAll(Collection<UpcIngredient> pUpcIngredients) {
		LOG.debug("saving UpcIngredient instances");
		try {
			getHibernateTemplate().saveOrUpdateAll(pUpcIngredients);
			LOG.debug("saving UpcIngredient instances successful");
		} catch (RuntimeException lRe) {
			LOG.error("saving UpcIngredient instances failed", lRe);
			throw lRe;
		}
	}

	public void deleteAll(final Collection<UpcIngredient> pUpcIngredients) {
		LOG.debug("deleting UpcIngredient instances");
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					getHibernateTemplate().deleteAll(pUpcIngredients);
					pSession.flush();
					return null;
				}
			});

			LOG.debug("deleting UpcIngredient instances successful");
		} catch (RuntimeException lRe) {
			LOG.error("deleting UpcIngredient instances failed", lRe);
			throw lRe;
		}
	}

	public List<UpcIngredient> findUPCIngredientsByPromotionId(final Integer pPromotionId){
		List<UpcIngredient> lUpcIngs = new ArrayList<UpcIngredient>();
		if(null==pPromotionId){
			return lUpcIngs;
		}
		try {
			lUpcIngs = (List<UpcIngredient>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery(
							"select distinct this from UpcIngredient this join this.ingredientItem.ingredientItemPromotions as ingredientItemPromotion" +
					" where ingredientItemPromotion.promotion.promotionId = :pPromotionId")
					.setInteger("pPromotionId", pPromotionId)
					.list();
				}
			});

			if (null != lUpcIngs) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("find UPCIngredients By PromotionId, done");
				}
			}
			return lUpcIngs;

		} catch (RuntimeException lRe) {
			LOG.error("find UPCIngredients By PromotionId failed", lRe);
			throw lRe;
		}
	}

}
