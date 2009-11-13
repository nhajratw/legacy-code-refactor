package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IOneStopDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStop;

public class OneStopDaoImpl extends BaseSpringDao<OneStop> implements IOneStopDao {
    private static final Logger LOG = Logger.getLogger(OneStopDaoImpl.class);
	
	public OneStopDaoImpl() {
		super(OneStop.class, "OneStop");
	}
	public OneStopDaoImpl(Class pClazz, String pEntityName) {
		super(pClazz, pEntityName);
	}
	
	public OneStop findByUpc(final String pUpc) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findByUpc");
		}
		try {
			OneStop lResult = (OneStop) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					
					List<OneStop> lResult = (List<OneStop>)pSession.createQuery(
					"select os from OneStop os join os.upcCodes upc where upc.upc = :upc group by os order by os.created asc limit 1")
					.setString("upc", pUpc)
					.setMaxResults(1)
					.list();
					
					OneStop lUpcs = null;
					if (null != lResult && !lResult.isEmpty()) {
						lUpcs = lResult.get(0);
					}

					return lUpcs;
					
				}
			});
			
			if (null != lResult) {
	    		if (LOG.isDebugEnabled()) {
	    			LOG.debug("findByUpc successful");
	    		}
			}

			return lResult;

		} catch (RuntimeException lRe) {
			LOG.error("findByUpc failed", lRe);
			throw lRe;
		}
	}
	

	/*
	 * if multiple OneStop have the same code, then narrow the search by looking in the details
	 */
	public OneStop findByDetails(final Collection<Integer> pOneStopUpcs, final String pBody, final String pHeader) {
		try {
			OneStop lResult = (OneStop) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					
					List<OneStop> lUpcs = (List<OneStop>)pSession.createQuery(
							"select detail.oneStop from OneStopDetail detail where" +
							" detail.oneStop.id in (:ids) and" +
							" detail.header = :header and" +
							" detail.body = :body")
							.setString("header", pHeader)
							.setString("body", pBody)
							.setParameterList("ids", pOneStopUpcs)
							.list();
					
					
					Set<OneStop> lSet = new HashSet<OneStop>();
					lSet.addAll(lUpcs);
					
					if (1 < lSet.size()) {
						if (LOG.isDebugEnabled()) {
							LOG.debug("non unique result found");
						}
						OneStop lUpc = lSet.iterator().next();
						return lUpc;
						
					} else if (lSet.isEmpty()) {
						return null;
					} else {
						OneStop lUpc = lSet.iterator().next();
						return lUpc;
					}
					
				}
			});
			
			if (null != lResult) {
	    		if (LOG.isDebugEnabled()) {
	    			LOG.debug("findByDetails successful");
	    		}
			}

			return lResult;

		} catch (RuntimeException lRe) {
			LOG.error("findByDetails failed", lRe);
			throw lRe;
		}
	}

	/*
	 * Find a OneStop by looking at its detaisl
	 */
	public OneStop findByDetails(final String pBody) {
		try {
			OneStop lResult = (OneStop) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					
					List<OneStop> lUpcs = (List<OneStop>)pSession.createQuery(
							"select detail.oneStop from OneStopDetail detail where" +
							" detail.body = :body")
							.setString("body", pBody)
							.list();
					
					
					Set<OneStop> lSet = new HashSet<OneStop>();
					lSet.addAll(lUpcs);
					
					if (1 < lSet.size()) {
						if (LOG.isDebugEnabled()) {
							LOG.debug("non unique result found");
						}
						OneStop lUpc = lSet.iterator().next();
						return lUpc;
						
					} else if (lSet.isEmpty()) {
						return null;
					} else {
						OneStop lUpc = lSet.iterator().next();
						return lUpc;
					}
					
				}
			});
			
			if (null != lResult) {
	    		if (LOG.isDebugEnabled()) {
	    			LOG.debug("findByDetails successful");
	    		}
			}

			return lResult;

		} catch (RuntimeException lRe) {
			LOG.error("findByDetails failed", lRe);
			throw lRe;
		}
	}
	
	
	
	public String getSortingClause(StringBuffer pJoinClause, String pColumn, String pSortType){
		String lOrderByClause = "";
		
		if((null != pColumn && !"".equals(pColumn)) && (null != pSortType && !"".equals(pSortType))) {
			String lOrderCriteria = "";
			
			if("upc".equals(pColumn)) {
				
				lOrderCriteria += " u.upc "+pSortType;
				
			} else if ("Header".equals(pColumn) || "Body".equals(pColumn)) {
				
				if("Header".equals(pColumn)) {
					lOrderCriteria += " d.header "+pSortType;
				} else {
					lOrderCriteria += " d.body "+pSortType;
				}
				
			} else if("Id".equals(pColumn)) {
				
				lOrderCriteria += " o.onestop_id "+pSortType;
				
			} else if("itemCount".equals(pColumn)) {
				
				lOrderCriteria += " count(distinct(oim.ingredient_item_id)) "+pSortType;
			}
			if(!"".equals(lOrderCriteria)) {
				lOrderByClause = " order by "+lOrderCriteria;
			}
		}
		return lOrderByClause;
	}
	
	public List<OneStop> findByUpcHasMapping(final String pOneStopUpc) {
		List<OneStop> lUpcs = new ArrayList<OneStop> ();
		try {
			lUpcs = (List<OneStop>)getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession) {
					List<OneStop> lList = pSession.createQuery(
							"from OneStop upc inner join upc.upcCodes code where " +
							"code.upc = :pUpc and upc.ingredientItems.size >= 1")
							.setString("pUpc", pOneStopUpc)	
							.list();
					return lList;
				}
			});
		} catch(RuntimeException lRe) {
			LOG.error("findByIdHasMapping failed", lRe);
			throw lRe;
		}
		return lUpcs;
	}
	
	public OneStop findByImage(final String pImage) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findByImage");
		}
		try {
			OneStop lResult = (OneStop) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					
					OneStop lUpcs = (OneStop)pSession.createQuery(
							"from OneStop where image = :image")
							.setString("image", pImage)
							.uniqueResult();

					return lUpcs;
					
				}
			});
			
			if (null != lResult) {
	    		if (LOG.isDebugEnabled()) {
	    			LOG.debug("findByImage successful");
	    		}
			}

			return lResult;

		} catch (RuntimeException lRe) {
			LOG.error("findByImage failed", lRe);
			throw lRe;
		}
	}
	
   /**
    * Get the OneStop based on imageName.
    */
   public OneStop findOneStopByImageName(final String pOneStopImage) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("finding OneStop by ImageName ["+(pOneStopImage == null ? "NA" : pOneStopImage)+"]");
		}
		try {
			List<OneStop> lOneStops = (List<OneStop>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session pSession)
				throws HibernateException, SQLException {
					return pSession.createQuery(
							"select this from OneStop as this where this.latestImage = :pImage" +
								" and this.status='ACTIVE' ")
					.setString("pImage", pOneStopImage)
					.list();
				}
			});

			if (null != lOneStops) {
	    		if (LOG.isDebugEnabled()) {
	    			LOG.debug("finding OneStop by ImageName successful. Numbers of finding ["+lOneStops.size()+"]");
	    		}
			}
			if(lOneStops == null || lOneStops.isEmpty())
				return null;
			else
				return lOneStops.get(0);

		} catch (RuntimeException lRe) {
			LOG.error("Error finding OneStop by ImageName", lRe);
			throw lRe;
		}

    }

   /**
    * Get the count of number of entries in fl_onestop_promotion_membership
    * table based on OneStopId and PromotionId.
    * @return int
    */
   public int findOneStopPromotionByIds(final Integer pOneStopId, final Integer pPromotionId) {
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("finding OneStopPromotion by OneStopId and PromotionId");
		}
		Integer count = new Integer(0);
		try {
			count =  (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session pSession)
			throws HibernateException, SQLException {
				
				//forming queries
				String lQueryforCount = "select count(1) as count from fl_onestop_promotion_membership this where" +
											" this.onestop_id = "+pOneStopId+" and this.promotion_id = "+pPromotionId;			
				
				//execute the query for the entity count
				Query lCountQuery = pSession.createSQLQuery(lQueryforCount).addScalar("count", Hibernate.INTEGER);
				
				Integer count =  (Integer)lCountQuery.uniqueResult();
				
				
				if (LOG.isDebugEnabled()) {
	    			LOG.debug("Successfully found OneStopPromotion by Ids. Count ["+(count == null ? 0 : count.intValue())+"]");
	    		}
				if(count == null)
					return 0;
				else
					return count.intValue();
				}
			});
   		
			return count.intValue();
		} catch (RuntimeException e) {
			LOG.error("finding OneStopPromotion failed",  e);
   		throw  e;
		}

   }
	
   /**
    * Get the count of number of entries in fl_onestop_upc
    * table based on OneStopId and upccode
    * @return int
    */
   public int findOneStopUPCByIds(final Integer pOneStopId, final String pUPCCode) {
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("finding OneStopUPC by OneStopId and UPCCode");
		}
		Integer count = new Integer(0);
		try {
			count =  (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session pSession)
			throws HibernateException, SQLException {
				
				//forming queries
				String lQueryforCount = "select count(1) as count from fl_onestop_upc this where" +
											" this.onestop_id = "+pOneStopId+" and this.upc = "+pUPCCode;			
				
				//execute the query for the entity count
				Query lCountQuery = pSession.createSQLQuery(lQueryforCount).addScalar("count", Hibernate.INTEGER);
				
				Integer count =  (Integer)lCountQuery.uniqueResult();
				
				
				if (LOG.isDebugEnabled()) {
	    			LOG.debug("Successfully found OneStopUPC by Ids. Count ["+(count == null ? 0 : count.intValue())+"]");
	    		}
				if(count == null)
					return 0;
				else
					return count.intValue();
				}
			});
   		
			return count.intValue();
		} catch (RuntimeException e) {
			LOG.error("finding OneStopUPC failed",  e);
   		throw  e;
		}

   }

}
