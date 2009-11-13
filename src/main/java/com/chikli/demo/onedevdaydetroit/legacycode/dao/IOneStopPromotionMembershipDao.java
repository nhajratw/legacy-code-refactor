/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStopPromotionMembership;


/**
 * @author 
 *
 */
public interface IOneStopPromotionMembershipDao extends IFoodlabDao<OneStopPromotionMembership> {

	public OneStopPromotionMembership findById(Integer pPromotionId, Integer pOneStopId);

}
