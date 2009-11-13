/**
 * $Id: IIngredientDao.java 1111 2007-12-13 13:18:22Z $
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import java.util.List;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.IngredientItem;

/**
 * @version $Revision: 1111 $ $Date: 2007-12-13 18:48:22 +0530 (Thu, 13 Dec 2007) $ by $Author: $
 */
public interface IIngredientItemDao extends IFoodlabDao<IngredientItem>{
	public IngredientItem getNextUnmappedItem(Integer pId);

	public String getItem(Integer pItem);

	public List<IngredientItem> findByIds(List<Integer> pIngredientItemIds);

	public List<IngredientItem> getIngredientItemByName(String pName);
}
