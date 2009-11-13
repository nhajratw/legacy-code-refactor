/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.type.IngredientItemSizeConversionType;

/**
 * @author 
 *
 */
//@Indexed(index = "fl_indexes/IngredientItem")
public class IngredientItem implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer m_ingredientItemId;
	protected String m_name;
	protected Set<Ingredient> m_ingredients = new HashSet<Ingredient>();
	protected Set<UpcIngredient> m_upcIngredients = new HashSet<UpcIngredient>();
	protected Set<IngredientItemPromotion> m_ingredientItemPromotions = new HashSet<IngredientItemPromotion>();

	protected Map<IngredientItemSizeConversionType, IngredientItemSizeConversion> m_sizeConversionMap = new HashMap<IngredientItemSizeConversionType, IngredientItemSizeConversion>();

	//audit fields
	protected Date m_created;
	protected Date m_lastUpdate;


	// Constructors
	/** default constructor */
	public IngredientItem(){

	}

	/** minimal constructor */
	public IngredientItem(String pName) {
		m_name = pName;
	}

	/** full constructor */
	public IngredientItem(String pName, Set<Ingredient> pIngredients, Set<UpcIngredient> pUpcIngredients, Set<IngredientItemPromotion> pIngredientItemPromotions) {
		m_name = pName;
		m_ingredients = pIngredients;
		m_upcIngredients = pUpcIngredients;
		m_ingredientItemPromotions = pIngredientItemPromotions;
	}

	//	Property accessors
	public Integer getIngredientItemId() {
		return m_ingredientItemId;
	}
	public void setIngredientItemId(Integer pIngredientItemId) {
		m_ingredientItemId = pIngredientItemId;
	}
	@XmlTransient
	public Set<Ingredient> getIngredients() {
		return m_ingredients;
	}

	public void setIngredients(Set<Ingredient> pIngredients) {
		m_ingredients = pIngredients;
	}
	public String getName() {
		return m_name;
	}
	public void setName(String pName) {
		m_name = pName;
	}
	@XmlTransient
	public Set<UpcIngredient> getUpcIngredients() {
		return m_upcIngredients;
	}
	public void setUpcIngredients(Set<UpcIngredient> pUpcIngredients) {
		m_upcIngredients = pUpcIngredients;
	}
	@XmlTransient
	public Set<IngredientItemPromotion> getIngredientItemPromotions() {
		return m_ingredientItemPromotions;
	}

	public void setIngredientItemPromotions(
			Set<IngredientItemPromotion> pIngredientItemPromotion) {
		m_ingredientItemPromotions = pIngredientItemPromotion;
	}

	public void addPromotions(Collection<Promotion> pPromotions){
		if (null == pPromotions || pPromotions.isEmpty()) {
			return;
		}
		getIngredientItemPromotions().clear();

		//create a IngredientItemPromotion for each Promotion in the set
		for (Promotion lPromotion : pPromotions) {
			IngredientItemPromotion lIngredientItemPromotion = new IngredientItemPromotion();
			lIngredientItemPromotion.setIngredientItem(this);
			lIngredientItemPromotion.setPromotion(lPromotion);
			lPromotion.getIngredientPromotions().add(lIngredientItemPromotion);
			getIngredientItemPromotions().add(lIngredientItemPromotion);
		}
	}

	@XmlTransient
	public Map<IngredientItemSizeConversionType, IngredientItemSizeConversion> getSizeConversionMap() {
		return m_sizeConversionMap;
	}

	public void setSizeConversionMap(
			Map<IngredientItemSizeConversionType, IngredientItemSizeConversion> pSizeConversionMap) {
		m_sizeConversionMap = pSizeConversionMap;
	}

	@XmlTransient
	public Date getCreated() {
		return m_created;
	}
	public void setCreated(Date pCreated) {
		m_created = pCreated;
	}

	@XmlTransient
	public Date getLastUpdate() {
		return m_lastUpdate;
	}

	public void setLastUpdate(Date pLastUpdate) {
		m_lastUpdate = pLastUpdate;
	}

}
