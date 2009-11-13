/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import java.util.Collection;
import java.util.List;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.UpcIngredient;

/**
 * @author 
 *
 */
public interface IUpcIngredientDao extends IFoodlabDao<UpcIngredient> {

	public void saveOrUpdateAll(Collection<UpcIngredient> pUpcIngredients);

	public void deleteAll(Collection<UpcIngredient> pUpcIngredients);

	public List<UpcIngredient> findUPCIngredientsByPromotionId(final Integer pPromotionId);

}
