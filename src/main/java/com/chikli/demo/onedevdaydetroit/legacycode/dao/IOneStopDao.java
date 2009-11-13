package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import java.util.Collection;
import java.util.List;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStop;

/**
 * 
 * @version $Revision: 430 $ $Date: 2007-11-01 17:49:01 -0700 (Thu, 01 Nov 2007) $ by $Author: $
 */
public interface IOneStopDao extends IFoodlabDao<OneStop> {

	/**
	 * Find by the string version of the UPC
	 * @param pUpc
	 * @return
	 */
	public OneStop findByUpc(String pUpc);

	
	/**
	 * Narrow a seach for OneStops within the the given Ids and details
	 * @param pOneStopUpcs
	 * @param pBody
	 * @param pHeader
	 * @return
	 */
	public OneStop findByDetails(Collection<Integer> pOneStopUpcs, String pBody, String pHeader);

	/**
	 * Find the first onestop that has the given product description details
	 * @param pBody
	 * @param pHeader
	 * @return
	 */
	public OneStop findByDetails(String pBody);
	
	/**
	 * Find by the xxx image path
	 * @param pImage
	 * @return
	 */
	public OneStop findByImage(String pImage);

	public List<OneStop> findByUpcHasMapping(String pOneStopUpcId);

	public OneStop findOneStopByImageName(String pOneStopImage);

	public int findOneStopPromotionByIds(Integer pOneStopId, Integer pPromotionId);

	public int findOneStopUPCByIds(Integer pOneStopId, String pUPCCode);

	public void update(OneStop pOneStop);

	public void flush();

}
