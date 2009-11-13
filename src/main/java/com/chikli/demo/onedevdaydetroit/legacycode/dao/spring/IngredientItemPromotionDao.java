/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IIngredientItemPromotionDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItemPromotion;

/**
 * 
 * 
 * @author 
 *
 * @version $Revision: 98 $ $Date: 2007-10-16 02:03:08 -0700 (Tue, 16 Oct 2007) $ by $Author:  $
 */
public class IngredientItemPromotionDao extends BaseSpringDao<IngredientItemPromotion> implements
IIngredientItemPromotionDao {

	private static final Logger LOG = Logger.getLogger(IngredientItemPromotionDao.class);

	public IngredientItemPromotionDao(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}

	public void deleteAll(final Collection<IngredientItemPromotion> pIngredientItemPromotions) {
		LOG.debug("deleting IngredientItemPromotion instances");
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					getHibernateTemplate().deleteAll(pIngredientItemPromotions);
					pSession.flush();
					return null;
				}
			});

			LOG.debug("deleting IngredientItemPromotion instances successful");
		} catch (RuntimeException lRe) {
			LOG.error("deleting IngredientItemPromotion instances failed", lRe);
			throw lRe;
		}
	}


	@Override
	public IngredientItemPromotion findById(Integer pId) {

		throw new RuntimeException("Operation not supported.");
	}

	public IngredientItemPromotion findById(Integer pPromotionId, Integer pIngredientItemId) {
		LOG.debug("Find IngredientItemPromotion instance by id");
		try {
			String lQueryString = "from IngredientItemPromotion as model where model."
				+ "promotion.promotionId = ? and model.ingredientItem.ingredientItemId = ?";
			List<IngredientItemPromotion> lIngredientItemPromotions = getHibernateTemplate().find(lQueryString, new Object[]{pPromotionId,pIngredientItemId});
			if(lIngredientItemPromotions == null || lIngredientItemPromotions.isEmpty()){
				LOG.debug("none found");
				return null;
			}
			LOG.debug("found a match");
			return lIngredientItemPromotions.get(0);
		} catch (RuntimeException lRe) {
			LOG.error("Find IngredientItemPromotion instance by id failed", lRe);
			throw lRe;
		}
	}

}
