package com.chikli.demo.onedevdaydetroit.legacycode.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IIngredientItemDao;
import com.chikli.demo.onedevdaydetroit.legacycode.dao.IIngredientItemPromotionDao;
import com.chikli.demo.onedevdaydetroit.legacycode.dao.IOneStopDao;
import com.chikli.demo.onedevdaydetroit.legacycode.dao.IOneStopDetailDao;
import com.chikli.demo.onedevdaydetroit.legacycode.dao.IOneStopPromotionMembershipDao;
import com.chikli.demo.onedevdaydetroit.legacycode.dao.IPromotionDao;
import com.chikli.demo.onedevdaydetroit.legacycode.dao.IUpcIngredientDao;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItem;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItemPromotion;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStop;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStopDetail;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStopPromotionMembership;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStopUpc;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.Promotion;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.Special;
import com.chikli.demo.onedevdaydetroit.legacycode.domain.type.OneStopStatus;


/**
 * Utility class to take a newly created promotion and try to map it to ingredients
 * and to a Onestop
 */
public class PromotionToOneStopMapper {
	//log definition
	public static final Logger LOG = Logger.getLogger(PromotionToOneStopMapper.class);

	// dao injections
	protected IPromotionDao m_promotionDao;
	protected IOneStopDetailDao m_oneStopDetailDao;
	protected IOneStopDao m_oneStopDao;
	protected IIngredientItemDao m_ingredientItemDao;
	protected IIngredientItemPromotionDao m_ingredientItemPromotionDao;
	protected IUpcIngredientDao m_upcIngredientDao;
	protected IOneStopPromotionMembershipDao m_oneStopPromotionMembershipDao;

	protected enum m_counts {updated, newonestops, mapped};
	/**
	 * Map the list of specials into the OneStops
	 * 
	 * @param pSpecials
	 * @param pResult
	 * @return
	 */
	public OneStopMappingResult mapPromotionsToOneStops(List<Special> pSpecials) {

		Map<m_counts, Integer> lCounts = new HashMap<m_counts, Integer>();
		lCounts.put(m_counts.updated, 0);
		lCounts.put(m_counts.newonestops, 0);
		lCounts.put(m_counts.mapped, 0);

		Set<OneStop> lOneStops = new HashSet<OneStop>();
		Set<String> lUpcs = new HashSet<String>();

		for (Special lSpecial : pSpecials) {
			try {
				if (null == lSpecial.getPromotionId()) {
					LOG.info("special has no id, not saved properly " + lSpecial.getCode() + ":" + lSpecial.getDescription());
					continue;
				}
				mapSpecial(lSpecial, lOneStops, lUpcs, lCounts);
			}catch(Exception ex) {
				LOG.warn("error occurred while mapPromotionsToOneStops. promotionId: "+lSpecial.getPromotionId());
				if(LOG.isDebugEnabled()){
					LOG.debug("error occurred", ex);
				}
			}
		}

		int lUpdatedCount = lCounts.get(m_counts.updated);
		int lNewCount = lCounts.get(m_counts.newonestops);
		int lMappedCount = lCounts.get(m_counts.mapped);

		OneStopMappingResult lResult = new OneStopMappingResult(lNewCount, lUpdatedCount, lMappedCount, lOneStops, lUpcs);
		LOG.debug("Successfully created: " + lNewCount  + " and updated: " + lUpdatedCount + " OneStops");

		return lResult;
	}


	protected void mapSpecial(Special pSpecial, Set<OneStop> pOneStops, Set<String> pUpcs, Map<m_counts, Integer> pCounts){

		// search for OneStop by Upc
		OneStop lOneStop = m_oneStopDao.findByUpc(pSpecial.getCode());
		pUpcs.add(pSpecial.getCode());

		//1. if found, add the details from the current promotion data
		if (null != lOneStop) {
			List<Integer> lIds = new ArrayList<Integer>();
			lIds.add(lOneStop.getId());

			OneStop lExisting = m_oneStopDao.findByDetails(lIds, pSpecial.getDescription(), pSpecial.getTitle());

			if (null == lExisting) {
				OneStopDetail lDetail = new OneStopDetail();
				lDetail.setBody(pSpecial.getDescription());
				lDetail.setHeader(pSpecial.getTitle());
				lDetail.setImage(pSpecial.getImage());
				lDetail.setOneStop(lOneStop);
				m_oneStopDetailDao.save(lDetail);
			}

			if(updatePromotionWithOneStop(pSpecial, lOneStop)){
				int lUpdateCount = pCounts.get(m_counts.updated);
				lUpdateCount++;
				pCounts.put(m_counts.updated, lUpdateCount);
			}
		} else {
			// search for UPCItems by id
			String lUPCCode = pSpecial.getCode();
			//			Long lUpc = null;
			//			try {
			//				lUpc = Long.parseLong(lUPCCode);
			//			} catch (Exception e) {
			//				//ignore
			//			}

			lOneStop = buildOneStop(pSpecial,lUPCCode);
			//new OneStop so find the ingredients for the upc
			//mapIngredientItems(lOneStop, pPromotion);
			getOneStopDao().save(lOneStop);
			int lNewCount = pCounts.get(m_counts.newonestops);
			lNewCount++;
			pCounts.put(m_counts.newonestops, lNewCount);

		}

		//get upcs for onestop. looks for ingredient items for upcs
		// combine ingredient from onestop and from upc, and map them for new promotion.
		if (mapIngredientItems(lOneStop, pSpecial)){
			int lMappedCount = pCounts.get(m_counts.mapped);
			lMappedCount++;
			pCounts.put(m_counts.mapped, lMappedCount);
		}
		pOneStops.add(lOneStop);

		if(LOG.isDebugEnabled()) {
			LOG.debug("Successfully updated the onestop id ["+lOneStop.getId()+"]");
		}

		
	}

	protected boolean mapIngredientItems(OneStop pOneStop, Promotion pPromotion) {
		boolean lResult = false;

		//find the IngredientItems for the Upcs (if any) and associate with the
		//OneStop and the Promotions
		if (null == pOneStop.getUpcCodes() || pOneStop.getUpcCodes().isEmpty()) {
			return false;
		}

		List<String> lUpcs = new ArrayList<String>(pOneStop.getUpcCodes().size());
		for(OneStopUpc lUpc: pOneStop.getUpcCodes()) {
			lUpcs.add(lUpc.getUpc());
		}

		List<IngredientItem> lIngredientItems = lookupIngredientItems(lUpcs);

		//Add all the ingredientItem to promotion which are mapped to onestop as well.
		//that helps carrying over all ingredientItems which we mapped earlier to onestop.
		if(null != lIngredientItems) {
			for (IngredientItem lIngredientItem : pOneStop.getIngredientItems()) {
				if(!lIngredientItems.contains(lIngredientItem)) {
					lIngredientItems.add(lIngredientItem);
				}
			}
		}

		if (null == lIngredientItems || lIngredientItems.isEmpty()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("no UpcIngredient found for upcs");
			}
		} else {
			//associate each ingredient item with each promotion
			for(IngredientItem lIngItem : lIngredientItems) {
				IngredientItemPromotion lExistingItemProm = m_ingredientItemPromotionDao.findById(pPromotion.getPromotionId(), lIngItem.getIngredientItemId());
				if(lExistingItemProm == null) {
					//create an IngredientItemPromotion and associate with the Promotion and IngredientItem
					IngredientItemPromotion lIngItemPromotion = new IngredientItemPromotion();
					lIngItemPromotion.setIngredientItem(lIngItem);
					lIngItemPromotion.setPromotion(pPromotion);

					pPromotion.getIngredientPromotions().add(lIngItemPromotion);
					lIngItem.getIngredientItemPromotions().add(lIngItemPromotion);

					m_ingredientItemDao.update(lIngItem);
					//update the promotion
					m_promotionDao.update(pPromotion);

					lResult = true;
				}
			}
		}
		return lResult;
	}

	/**
	 * looking ingredientItems on the basis of upcCode
	 * @param pPromotionData
	 * @return
	 */
	protected List<IngredientItem> lookupIngredientItems(List<String> pIds){
		List<Long> lLongIds = new ArrayList<Long>();
		for (String lString : pIds) {
			try {
				// check if the incoming string ids are parsable to Long, (but not a alphanumeric)
				Long lId = Long.valueOf(lString);
				lLongIds.add(lId);
			} catch (NumberFormatException ex) {
				// skip the alphanumeric ids (unable to parse)
				LOG.debug("Error parsing string to long for UPC id : "+lString);
			}
		}
		
		String[] lParams = {"pCode"};
		Object[] lValues = {lLongIds};
		List<IngredientItem> lIngredientItems = m_upcIngredientDao.findByNamedQueryAndNamedParams("findIngredientItemByUpcCode", lParams, lValues);
		return lIngredientItems;
	}

	public OneStop buildOneStop(Promotion pPromotion, String pUPC) {
		OneStop lNewOneStop = new OneStop();
		lNewOneStop.setLatestImage(pPromotion.getImage());
		lNewOneStop.setStatus(OneStopStatus.ACTIVE);
		lNewOneStop.setIgnored(false);

		// add the incoming promotion
		Set<Promotion> lPromotions = new HashSet<Promotion>();
		lPromotions.add(pPromotion);
		lNewOneStop.setPromotions(lPromotions);

		// add the onestop detail
		OneStopDetail lDetail = new OneStopDetail();
		lDetail.setHeader(pPromotion.getTitle());
		lDetail.setBody(pPromotion.getDescription());
		lDetail.setImage(pPromotion.getImage());
		lDetail.setOneStop(lNewOneStop);
		Set<OneStopDetail> lDetails = new HashSet<OneStopDetail>();
		lDetails.add(lDetail);
		lNewOneStop.setDetails(lDetails);

		//even though we don't have a UPC, we can still
		//create the OneStop to allow mapping of ingredients
		//(even if its non-ideal in that new onestops can't re-use the mapping).
		if (null != pUPC) {
			OneStopUpc lOneStopUpc = new OneStopUpc();
			lOneStopUpc.setOneStop(lNewOneStop);
			lOneStopUpc.setUpc(pUPC);
			Set<OneStopUpc> lOneStopUpcSet = new HashSet<OneStopUpc>();
			lOneStopUpcSet.add(lOneStopUpc);
			lNewOneStop.setUpcCodes(lOneStopUpcSet);
		}

		lNewOneStop.setLastUpdate(new Date());
		lNewOneStop.setCreated(new Date());

		return lNewOneStop;
	}

	protected boolean updatePromotionWithOneStop(Promotion pPromotion, OneStop pOneStop){
		if(null==pPromotion || null==pOneStop){
			return false;
		}
		//see if its there
		OneStopPromotionMembership lOneStopMembership = m_oneStopPromotionMembershipDao.findById(pPromotion.getPromotionId(), pOneStop.getId());
		if(null==lOneStopMembership){
			//then save it
			lOneStopMembership = new OneStopPromotionMembership();
			lOneStopMembership.setOneStop(pOneStop);
			lOneStopMembership.setPromotion(pPromotion);
			m_oneStopPromotionMembershipDao.save(lOneStopMembership);

			//update set entities
			pOneStop.getPromotions().add(pPromotion);
		}

		return true;
	}

	// -- Getters and Setters

	public IPromotionDao getPromotionDao() {
		return m_promotionDao;
	}

	public void setPromotionDao(IPromotionDao pPromotionDao) {
		m_promotionDao = pPromotionDao;
	}

	public IOneStopDetailDao getOneStopDetailDao() {
		return m_oneStopDetailDao;
	}

	public void setOneStopDetailDao(IOneStopDetailDao pOneStopDetailDao) {
		m_oneStopDetailDao = pOneStopDetailDao;
	}

	public IOneStopDao getOneStopDao() {
		return m_oneStopDao;
	}

	public void setOneStopDao(IOneStopDao pOneStopDao) {
		m_oneStopDao = pOneStopDao;
	}

	public IIngredientItemDao getIngredientItemDao() {
		return m_ingredientItemDao;
	}

	public void setIngredientItemDao(IIngredientItemDao pIngredientItemDao) {
		m_ingredientItemDao = pIngredientItemDao;
	}

	public IIngredientItemPromotionDao getIngredientItemPromotionDao() {
		return m_ingredientItemPromotionDao;
	}

	public void setIngredientItemPromotionDao(
			IIngredientItemPromotionDao pIngredientItemPromotionDao) {
		m_ingredientItemPromotionDao = pIngredientItemPromotionDao;
	}

	public IUpcIngredientDao getUpcIngredientDao() {
		return m_upcIngredientDao;
	}

	public void setUpcIngredientDao(IUpcIngredientDao pUpcIngredientDao) {
		m_upcIngredientDao = pUpcIngredientDao;
	}

	public IOneStopPromotionMembershipDao getOneStopPromotionMembershipDao() {
		return m_oneStopPromotionMembershipDao;
	}

	public void setOneStopPromotionMembershipDao(
			IOneStopPromotionMembershipDao pOneStoPromotionMembershipDao) {
		m_oneStopPromotionMembershipDao = pOneStoPromotionMembershipDao;
	}



}
