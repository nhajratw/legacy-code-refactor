/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import java.util.Collection;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItemPromotion;

/**
 *
 */
public interface IIngredientItemPromotionDao extends IFoodlabDao<IngredientItemPromotion> {

	public IngredientItemPromotion findById(Integer pPromotionId, Integer pIngredientItemId);

	public void deleteAll(final Collection<IngredientItemPromotion> pIngredientItemPromotions);

}
