/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;


/**
 * @author 
 *
 */
public class OneStopPromotionMembership implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected Promotion m_promotion;
	protected OneStop m_oneStop;

	public Promotion getPromotion() {
		return m_promotion;
	}

	public void setPromotion(Promotion pPromotion) {
		m_promotion = pPromotion;
	}

	//Override equals and hashCode
	@Override
	public boolean equals(Object pObject) {
		if (this == pObject) {
			return true;
		}

		if (m_promotion == null || m_oneStop == null) {
			return false;
		}

		if ( !(pObject instanceof OneStopPromotionMembership) ) {
			return false;
		}

		final OneStopPromotionMembership lObject = (OneStopPromotionMembership) pObject;

		return this.m_oneStop.equals(lObject.getOneStop()) && this.m_promotion.equals(lObject.getPromotion());
	}
	
	@Override
	public int hashCode() {
		int lResult = 26;
		
		if (null == m_promotion && null == m_oneStop) {
			return super.hashCode();
		}
		
		
		if (null != m_promotion) {
			lResult = 39 * lResult + m_promotion.hashCode();
		}
			
		if (null != m_oneStop) {
			lResult = 39 * lResult + m_oneStop.hashCode();
		}

		return lResult;
	}

	public OneStop getOneStop() {
		return m_oneStop;
	}

	public void setOneStop(OneStop pOneStop) {
		m_oneStop = pOneStop;
	}
}
