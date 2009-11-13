package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.type.IngredientItemSizeConversionType;

/**
 * Contains the details to perform unit conversion for an Ingredient Item.
 * 
 * 
 * @author 
 *
 */
public class IngredientItemSizeConversion implements Serializable {

	private static final long serialVersionUID = 1L;

	//	Fields
	protected Integer m_ingredientItemSizeConversionId;

	//descriptive fields
	protected String m_food;
	protected String m_weight;
	protected String m_approximateMeasure;

	//conversion values
	protected IngredientItemSizeConversionType m_type;
	protected String m_fromUnitStr;
	protected Double m_fromQuantity;
	protected String m_toUnitStr;
	protected Double m_toQuantity;

	//non-persisted fields holding the parsed Unit values for the
	//strings held in the db
	protected Unit m_toUnit;
	protected Unit m_fromUnit;


	//size to ingredient mappings
	protected Set<IngredientItem> m_ingredientItems = new HashSet<IngredientItem>();


	//audit fields
	protected Date m_created;
	protected Date m_lastUpdate;

	//default constructor
	public IngredientItemSizeConversion() {	}


	// -- Getters and Setters

	public Integer getIngredientItemSizeConversionId() {
		return m_ingredientItemSizeConversionId;
	}

	public void setIngredientItemSizeConversionId(
			Integer pIngredientItemSizeConversionId) {
		m_ingredientItemSizeConversionId = pIngredientItemSizeConversionId;
	}

	public String getFood() {
		return m_food;
	}

	public void setFood(String pFood) {
		m_food = pFood;
	}

	public String getWeight() {
		return m_weight;
	}

	public void setWeight(String pWeight) {
		m_weight = pWeight;
	}

	public String getApproximateMeasure() {
		return m_approximateMeasure;
	}

	public void setApproximateMeasure(String pApproximateMeasure) {
		m_approximateMeasure = pApproximateMeasure;
	}

	public IngredientItemSizeConversionType getType() {
		return m_type;
	}

	public void setType(IngredientItemSizeConversionType pType) {
		m_type = pType;
	}

	public String getFromUnitStr() {
		return m_fromUnitStr;
	}

	public void setFromUnitStr(String pFromUnitStr) {
		m_fromUnitStr = pFromUnitStr;
		if (null != pFromUnitStr) {
			m_fromUnit = Unit.fromString(pFromUnitStr);
		}
	}

	public Unit getFromUnit() {
		return m_fromUnit;
	}

	public void setFromUnit(Unit pFromUnit) {
		m_fromUnit = pFromUnit;
		if (null != pFromUnit) {
			m_fromUnitStr = pFromUnit.getDescription();
		}
	}

	public Double getFromQuantity() {
		return m_fromQuantity;
	}

	public void setFromQuantity(Double pFromQuantity) {
		m_fromQuantity = pFromQuantity;
	}

	public String getToUnitStr() {
		return m_toUnitStr;
	}

	public void setToUnitStr(String pToUnitStr) {
		m_toUnitStr = pToUnitStr;
		if (null != pToUnitStr) {
			m_toUnit = Unit.fromString(pToUnitStr);
		}
	}

	public Unit getToUnit() {
		return m_toUnit;
	}

	public void setToUnit(Unit pToUnit) {
		m_toUnit = pToUnit;
		if (null != pToUnit) {
			m_toUnitStr = pToUnit.getDescription();
		}
	}

	public Double getToQuantity() {
		return m_toQuantity;
	}

	public void setToQuantity(Double pToQuantity) {
		m_toQuantity = pToQuantity;
	}

	public Date getCreated() {
		return m_created;
	}

	public void setCreated(Date pCreated) {
		m_created = pCreated;
	}

	public Date getLastUpdate() {
		return m_lastUpdate;
	}

	public void setLastUpdate(Date pLastUpdate) {
		m_lastUpdate = pLastUpdate;
	}


	public Set<IngredientItem> getIngredientItems() {
		return m_ingredientItems;
	}


	public void setIngredientItems(Set<IngredientItem> pIngredientItems) {
		m_ingredientItems = pIngredientItems;
	}

}