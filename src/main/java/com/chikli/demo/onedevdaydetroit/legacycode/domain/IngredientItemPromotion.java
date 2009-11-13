/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;


/**
 * @author 
 *
 */
public class IngredientItemPromotion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected Promotion m_promotion;
	protected IngredientItem m_ingredientItem;

	public IngredientItem getIngredientItem() {
		return m_ingredientItem;
	}

	public void setIngredientItem(IngredientItem pIngredientItem) {
		m_ingredientItem = pIngredientItem;
	}

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

		if (m_promotion == null || m_ingredientItem == null) {
			return false;
		}

		if ( !(pObject instanceof IngredientItemPromotion) ) {
			return false;
		}

		final IngredientItemPromotion lObject = (IngredientItemPromotion) pObject;

		return this.m_promotion.equals(lObject.getPromotion()) && this.m_ingredientItem.equals(lObject.getIngredientItem());
	}
	@Override
	public int hashCode() {
		int lResult = 24;
		lResult = 39 * lResult + getPromotion().hashCode();
		lResult = 39 * lResult + getIngredientItem().hashCode();
		return lResult;
	}
}
