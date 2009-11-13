/**
 * $Id: StoreDao.java 426 2007-09-13 22:38:31Z  $
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IIngredientItemDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItem;

/**
 * @author 
 *
 * @version $Revision: 426 $ $Date: 2007-09-14 04:08:31 +0530 (Fri, 14 Sep 2007) $ by $Author:  $
 */
public class IngredientItemDao extends BaseSpringDao<IngredientItem> implements IIngredientItemDao {

	private static final Logger LOG = Logger.getLogger(IngredientItemDao.class);

	public IngredientItemDao(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}

	public IngredientItem getNextUnmappedItem(Integer pId){
		if(LOG.isDebugEnabled()){
			LOG.debug("getting Next IngredientItem instance with id: "+pId);
		}
		try{
			String lQueryString = "from IngredientItem as model where model.ingredientItemId > ? order by model limit 1";
			List<IngredientItem> lIngredientItems = getHibernateTemplate().find(lQueryString, pId);
			if(lIngredientItems == null || lIngredientItems.isEmpty()){
				return null;
			}
			return lIngredientItems.get(0);
		}catch (RuntimeException lRe) {
			LOG.error("getting Next IngredientItem instance with id: "+pId+" failed", lRe);
			throw lRe;
		}
	}

	protected String getCreteria(List<String> pTokens){
		StringBuffer lBuffer = new StringBuffer("");
		for(String lToken: pTokens){
			lBuffer.append(" model.name like '%"+lToken+"%' ");
		}
		return null;
	}

	public String getItem(Integer pItemId){
		String lQuery = "select this.name from IngredientItem as this join this.ingredientItemPromotions as ingredientItemPro where" +
		" ingredientItemPro.promotion.promotionId = ?";
		List<String> lIngredientItems = getHibernateTemplate().find(lQuery, pItemId);
		if(null!=lIngredientItems && !lIngredientItems.isEmpty()){
			String lItem = lIngredientItems.get(0);
			return lItem;
		}
		return null;
	}

	public List<IngredientItem> findByIds(final List<Integer> pIngredientItemIds) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("finding IngredientItem by Ids");
		}
		try {
			List<IngredientItem> lIngredientItems = (List<IngredientItem>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery(
					"select this from IngredientItem as this where this.ingredientItemId in (:pIds)")
					.setParameterList("pIds", pIngredientItemIds)
					.list();
				}
			});

			if (null != lIngredientItems) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("finding IngredientItem by Ids");
				}
			}
			return lIngredientItems;
		} catch (RuntimeException lRe) {
			LOG.error("finding IngredientItem by Ids failed", lRe);
			throw lRe;
		}
	}

	public List<IngredientItem> getIngredientItemByName(String pName){

		if(LOG.isDebugEnabled()){
			LOG.debug("getting IngredientItem instances with name: "+pName);
		}
		try{
			String lQueryString = "from IngredientItem as model where model.name = ? ";
			List<IngredientItem> lIngredientItems = getHibernateTemplate().find(lQueryString, pName);
			return lIngredientItems;
		}catch (RuntimeException lRe) {
			LOG.error("getting IngredientItem instances with name: "+pName +" failed", lRe);
			throw lRe;
		}
	}


}
