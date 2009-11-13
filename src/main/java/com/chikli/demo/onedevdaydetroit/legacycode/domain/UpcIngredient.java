package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Join mapping between UpcItem and IngredientItem.
 * 
 * These are mostly created as part of a manual admin process in the UI.
 * 
 * 
 * @author 
 *
 * @version $Revision: 430 $ $Date: 2007-11-01 17:49:01 -0700 (Thu, 01 Nov 2007) $ by $Author:  $
 */
public class UpcIngredient {
	protected Integer m_upcIngredientId;
	protected UPCItems m_upc;
	protected IngredientItem m_ingredientItem;
	protected Date m_created = new Date();
	protected Date m_lastUpdate = new Date();

	//non persisted relation
	protected Set<Ingredient> m_ingredients = new HashSet<Ingredient>();

	// Constructors
	/** default constructor */
	public UpcIngredient() {}

	//	Property accessors
	public Integer getUpcIngredientId() {
		return m_upcIngredientId;
	}

	public void setUpcIngredientId(Integer pUpcIngredientId) {
		m_upcIngredientId = pUpcIngredientId;
	}

	public UPCItems getUpc() {
		return m_upc;
	}

	public void setUpc(UPCItems pUpc) {
		m_upc = pUpc;
	}

	public IngredientItem getIngredientItem() {
		return m_ingredientItem;
	}

	public void setIngredientItem(IngredientItem pIngredientItem) {
		m_ingredientItem = pIngredientItem;
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

	@XmlTransient
	public Set<Ingredient> getIngredients() {
		return m_ingredients;
	}

	public void setIngredients(Set<Ingredient> pIngredients) {
		m_ingredients = pIngredients;
	}
	//Helper meth to handle the Ingredients.
	public void addIngredient(Ingredient pIngredient) {
		if (null == pIngredient) {
			return;
		}
		if(getIngredients() == null) {
			setIngredients(new HashSet<Ingredient>());
		}
		getIngredients().add(pIngredient);
	}


}
