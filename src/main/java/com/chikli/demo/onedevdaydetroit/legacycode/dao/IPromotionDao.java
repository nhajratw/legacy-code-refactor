/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import java.util.List;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.Promotion;

/**
 * @author 
 *
 */
public interface IPromotionDao extends IFoodlabDao<Promotion> {

	List<Promotion> findSpecialsByStoreId(Integer pStoreId);

	List<Promotion> findByIds(final List<Integer> pPromotionIds);


	List<Promotion> findPromotionByExternalId(String pExternalId);


	Integer deletePromotionByBatchId(final Integer pBatchId);

	Integer deleteSpecials();

	List<String> deleteExpiredPromotions();

	List<String> getPromotionImagesByBatchId(Integer pBatchId);

	Promotion findByImageName(String pPromotionImage);

	int findStorePromotionByIds(Integer pStoreId, Integer pPromotionId);

	List<Promotion> findByUpcId(Long pUpcItemId, Integer pStoreId);

	List<Promotion> findByBrandId(Integer pBrandId);

	void bulkUpdateByBrand(Integer pBrandId);

	List<Promotion> findCouponsIncCoupons();
}