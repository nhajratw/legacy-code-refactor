package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IPromotionDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItemPromotion;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.Promotion;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.PromotionCategory;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.Special;

/**
 * @author 
 *
 */
public class PromotionDao extends BaseSpringDao<Promotion> implements IPromotionDao {
	private static final Logger LOG = Logger.getLogger(PromotionDao.class);

	protected JdbcTemplate m_jdbcTemplate;

	public PromotionDao() {
		super(Promotion.class, "Promotion");
	}

	public PromotionDao(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}

	public List<Promotion> findByIds(final List<Integer> pPromotionIds) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("finding Promotions by Ids");
		}
		try {
			List<Promotion> lPromotion = (List<Promotion>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery(
					"select this from Promotion as this where this.promotionId in (:pIds)")
					.setParameterList("pIds", pPromotionIds)
					.list();
				}
			});

			if (null != lPromotion) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("finding Promotions by Ids");
				}
			}
			return lPromotion;

		} catch (RuntimeException lRe) {
			LOG.error("finding Promotions by Ids", lRe);
			throw lRe;
		}

	}

	public List<Promotion> findSpecialsByStoreId( final Integer pStoreId){
		List<Promotion> lList = new ArrayList<Promotion>();

		if(pStoreId == null){
			lList = (List<Promotion>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery("select this from Promotion this left join this.stores store" +
							" where store.storeId is NULL and this.status='ACTIVE' and this.special=true" +
							" and (current_date() between date(this.activationDate) and date(this.expirationDate))" +
					" order by this.promotionId")
					.list();
				}
			});
		}else{
			lList = (List<Promotion>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery("select this from Promotion this left join this.stores store" +
							" where (store.storeId = :pStoreId or this.store.storeId is NULL) and this.status='ACTIVE' and this.special=true" +
							" and (current_date() between date(this.activationDate) and date(this.expirationDate))" +
					" order by this.promotionId")
					.setInteger("pStoreId", pStoreId)
					.list();
				}
			});
		}
		return lList;
	}


	public List<Promotion> findPromotionByExternalId(final String pExternalId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("finding Promotions by ExternalId");
		}
		try {
			List<Promotion> lPromotion = (List<Promotion>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery(
					"select this from Promotion as this where this.externalId = :pExternalId")
					.setString("pExternalId", pExternalId)
					.list();
				}
			});

			if (null != lPromotion) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("finding Promotions by Ids");
				}
			}
			return lPromotion;

		} catch (RuntimeException lRe) {
			LOG.error("finding Promotions by Ids", lRe);
			throw lRe;
		}
	}

	

	public Integer deletePromotionByBatchId(final Integer pBatchId){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Deleting Promotion by BatchId");
		}
		try {

			String[] lDeleteQueries = new String[] {
					"delete cm from fl_promotion_category_membership cm " +
					"where cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.batch_id = " + pBatchId + ")",

					"delete cm from fl_onestop_promotion_membership cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.batch_id = " + pBatchId + ")",

					"delete cm from fl_upc_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.batch_id = " + pBatchId + ")",

					"delete cm from fl_store_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.batch_id = " + pBatchId + ")",

					"delete cm from fl_ingredient_item_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.batch_id = " + pBatchId + ")",

					"delete cm from fl_shopping_list_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.batch_id = " + pBatchId + ")",

					"delete p from fl_promotion p where p.batch_id = " + pBatchId};


			int[] lCounts = m_jdbcTemplate.batchUpdate(lDeleteQueries);

			//last result
			Integer lResult = lCounts[lCounts.length -1];

			//force cache clearing
			getHibernateTemplate().getSessionFactory().evict(PromotionCategory.class);
			getHibernateTemplate().getSessionFactory().evict(Promotion.class);
			getHibernateTemplate().getSessionFactory().evict(IngredientItemPromotion.class);
			getHibernateTemplate().getSessionFactory().evict(Special.class);

			if (LOG.isDebugEnabled()) {
				LOG.debug("deleted " + lResult + " promotions");
			}

			return lResult;
		}catch (RuntimeException e) {
			LOG.error("deletePromotionByBatchId failed",  e);
			throw  e;
		}
	}

	public Integer deleteSpecials(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Deleting specials");
		}
		try {

			String[] lDeleteQueries = new String[] {
					"delete cm from fl_promotion_category_membership cm " +
					"where cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.special = 1)",

					"delete cm from fl_onestop_promotion_membership cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.special = 1)",

					"delete cm from fl_upc_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.special = 1)",

					"delete cm from fl_store_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.special = 1)",

					"delete cm from fl_ingredient_item_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.special = 1)",

					"delete cm from fl_shopping_list_promotion cm " +
					"where " +
					"cm.promotion_id in " +
					"(select p.promotion_id from fl_promotion p where p.special = 1)",

			"delete p from fl_promotion p where p.special = 1"};


			int[] lCounts = m_jdbcTemplate.batchUpdate(lDeleteQueries);

			//last result
			Integer lResult = lCounts[lCounts.length -1];

			//force cache clearing
			getHibernateTemplate().getSessionFactory().evict(PromotionCategory.class);
			getHibernateTemplate().getSessionFactory().evict(Promotion.class);
			getHibernateTemplate().getSessionFactory().evict(IngredientItemPromotion.class);
			getHibernateTemplate().getSessionFactory().evict(Special.class);

			if (LOG.isDebugEnabled()) {
				LOG.debug("deleted " + lResult + " specials");
			}

			return lResult;
		}catch (RuntimeException e) {
			LOG.error("deleteSpecials failed",  e);
			throw  e;
		}
	}





	public List<String> deleteExpiredPromotions(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Deleting Promotion by BatchId");
		}
		try {

			Object lQueryResult = getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {

					List<String> lExternalIds = new ArrayList<String>();

					ScrollableResults lPromotions = pSession.createQuery("select this from Promotion this where now() > this.expirationDate")
					.scroll();

					if(lPromotions.first()) {
						do {
							Promotion lPromotion = (Promotion)lPromotions.get(0);
							lExternalIds.add(lPromotion.getImage());
							lPromotion.getIngredientPromotions().clear();
							pSession.delete(lPromotion);
						} while (lPromotions.next());
					}

					return lExternalIds;

				}
			});

			List<String> lResult = (List<String>)lQueryResult;

			if (LOG.isDebugEnabled()) {
				LOG.debug("Deleting promotions["+lResult+"] by BatchId");
			}
			return lResult;
		}catch (RuntimeException e) {
			LOG.error("deletePromotionByBatchId failed",  e);
			throw  e;
		}
	}

	public List<String> getPromotionImagesByBatchId(final Integer pBatchId){
		if (LOG.isDebugEnabled()) {
			LOG.debug("Deleting Promotion by BatchId");
		}
		try {

			List<String> lResult = (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {


					List<String> lImages = pSession.createQuery("select distinct this.image from Promotion this where this.batchId=:pBatchId")
					.setInteger("pBatchId", pBatchId)
					.list();
					return lImages;

				}
			});

			if (LOG.isDebugEnabled()) {
				LOG.debug("Found Promotion Images["+lResult+"] for BatchId:"+pBatchId);
			}
			return lResult;
		}catch (RuntimeException e) {
			LOG.error("getPromotionImagesByBatchId failed",  e);
			throw  e;
		}
	}


	/**
	 * Get the promotion based on imageName.
	 */
	public Promotion findByImageName(final String pPromotionImage) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("finding Promotion by ImageName ["+(pPromotionImage == null ? "NA" : pPromotionImage)+"]");
		}
		try {
			List<Special> lPromotions = (List<Special>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery(
							"select this from Special as this where this.image = :pImage" +
					" and (this.status='ACTIVE' or this.status='PENDING')")
					.setString("pImage", pPromotionImage)
					.list();
				}
			});

			if (null != lPromotions) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("finding Promotions by ImageName successful. Numbers of finding ["+lPromotions.size()+"]");
				}
			}
			if(lPromotions == null || lPromotions.isEmpty()) {
				return null;
			} else {
				return lPromotions.get(0);
			}

		} catch (RuntimeException lRe) {
			LOG.error("Error finding Promotions by ImageName", lRe);
			throw lRe;
		}

	}

	/**
	 * Find the number of promotion map with given store
	 * @return int
	 */
	public int findStorePromotionByIds(final Integer pStoreId, final Integer pPromotionId){

		if (LOG.isDebugEnabled()) {
			LOG.debug("finding StorePromotion by storeId and promotionId");
		}
		Integer count = new Integer(0);
		try {
			count =  (Integer) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {

					//forming queries
					String lQueryforCount = "select count(1) as count from fl_store_promotion this where" +
					" this.store_id = "+pStoreId+" and this.promotion_id = "+pPromotionId;

					//execute the query for the entity count
					Query lCountQuery = pSession.createSQLQuery(lQueryforCount).addScalar("count", Hibernate.INTEGER);

					Integer count =  (Integer)lCountQuery.uniqueResult();


					if (LOG.isDebugEnabled()) {
						LOG.debug("Successfully found StorePromotion by Ids. Count ["+(count == null ? 0 : count.intValue())+"]");
					}
					if(count == null) {
						return 0;
					} else {
						return count.intValue();
					}
				}
			});

			return count.intValue();
		} catch (RuntimeException e) {
			LOG.error("findStorePromotionByIds failed",  e);
			throw  e;
		}
	}

	public List<Promotion> findByUpcId(final Long pUpcItemId, final Integer pStoreId) {
		List<Promotion> lPromotions = null;
		try {
			lPromotions = (List<Promotion>) getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session pSession) throws SQLException, HibernateException {
					//current date
					Date date = new Date();

					if(null == pStoreId) {
						String lQuery = "select promo from IngredientItemPromotion ingItemPromo left join ingItemPromo.promotion promo left join promo.stores as store , UpcIngredient upcIng " +
						" where upcIng.ingredientItem = ingItemPromo.ingredientItem" +
						" and upcIng.upc.code = :code" +
						" and promo.activationDate <= :actDate" +
						" and promo.expirationDate >= :expDate" +
						" and promo.status = 'ACTIVE'" +
						" and store.storeId IS NULL" +
						" and promo.special is not true";
						List<Promotion> lPromos = pSession.createQuery(lQuery).setLong("code", pUpcItemId)
						.setDate("actDate", date)
						.setDate("expDate", date)
						.list();
						return lPromos;
					} else {

						String lQuery = "select promo from IngredientItemPromotion ingItemPromo left join ingItemPromo.promotion promo left join promo.stores as store , UpcIngredient upcIng " +
						" where upcIng.ingredientItem = ingItemPromo.ingredientItem" +
						" and upcIng.upc.code = :code" +
						" and promo.activationDate <= :actDate" +
						" and promo.expirationDate >= :expDate" +
						" and promo.status = 'ACTIVE'" +
						" and ((promo.special is true and store.unitId = :pStoreId)	or "+
						" (promo.special is not true and (store.unitId = :pStoreId OR store.storeId IS NULL))) ";

						List<Promotion> lPromos = pSession.createQuery(lQuery).setLong("code", pUpcItemId)
						.setDate("actDate", date)
						.setDate("expDate", date)
						.setInteger("pStoreId", pStoreId)
						.list();
						return lPromos;
					}


				}
			});
			return lPromotions;
		}catch(RuntimeException e) {
			LOG.error("findByUpcId failed",  e);
			throw  e;
		}
	}

	public List<Promotion> findByBrandId(final Integer pBrandId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("finding promotions with brand id");
		}
		List<Promotion> lPromotions = null;

		try {
			lPromotions = (List<Promotion>) getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session pSession) throws SQLException, HibernateException {
					List<Promotion> lPromotions = pSession.createQuery("from Coupon coupon where coupon.brand.id = :pBrandId ").setInteger("pBrandId",pBrandId).list();

					if(null == lPromotions || lPromotions.isEmpty()){
						return null;
					}
					return lPromotions;
				}
			});
		} catch(RuntimeException lRe){
			LOG.error("finding promotions with brand id failed.", lRe);
			throw lRe;
		}

		return lPromotions;
	}

	public void bulkUpdateByBrand(final Integer pBrandId){

		if (LOG.isDebugEnabled()) {
			LOG.debug("Update promotions with brand id");
		}

		Integer rowCount=null;
		try{
			rowCount =(Integer)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession) throws HibernateException, SQLException {

					String hql = "update Promotion promotion set promotion.brand = :brand where promotion.brand.id = :brandId";
					Query lQuery = pSession.createQuery(hql);
					lQuery.setString("brand",null);
					lQuery.setInteger("brandId",pBrandId);
					return lQuery.executeUpdate();
				}
			});
		}catch(RuntimeException e) {
			LOG.error("fail to update the promotions :",  e);
			throw  e;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updated Promotions :"+rowCount);
		}
	}
	
	public List<Promotion> findCouponsIncCoupons() {
		return findByNamedQuery("promotion.coupons_inc_coupons", null);
	}

	public void setJdbcTemplate(JdbcTemplate pJdbcTemplate) {
		m_jdbcTemplate = pJdbcTemplate;
	}
}
