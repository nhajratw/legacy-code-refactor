package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlTransient;

public class Ingredient {

	protected Integer ingredientId;

	protected String m_description;
	protected String m_category;
	protected String m_readableDescription;
	protected String m_basicItem;

	protected IngredientItem m_ingredientItem;
	protected String m_createdBy;
	protected String m_lastUpdatedBy;
	protected Date m_created = new Date();
	protected Date m_lastUpdate = new Date();

	//@IndexedEmbedded
	protected Set<RecipeIngredient> m_recipeIngredients = new TreeSet<RecipeIngredient>((new RecipeIngredientComparator()));

	//Non persisted value.. Hibernate is not happy in handling many-to-many relation with property-ref.
	//Use "saveOrUpdateAll()" for save, Named query "getUpcIngredients" for fetch. And a bulkupdate to delete.
	protected Set<UpcIngredient> m_upcIngredients = new HashSet<UpcIngredient>();
	protected Set<Promotion> m_coupans = new HashSet<Promotion>();

	// Constructors
	/** default constructor */
	public Ingredient() {}

	/** full constructor */
	public Ingredient(Integer pId, String pDescription, IngredientItem pIngredientItem, String pCategory, String pReadableDescription,
			Set<UpcIngredient> pUpcIngredients, Date pCreated, Date pLastUpdate) {

		ingredientId = pId;
		m_description = pDescription;
		m_category = pCategory;
		m_readableDescription = pReadableDescription;
		m_ingredientItem = pIngredientItem;
		m_created = pCreated;
		m_lastUpdate = pLastUpdate;
		m_upcIngredients = pUpcIngredients;

	}

	//	Property accessors
	public Integer getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Integer pIngredientId) {
		ingredientId = pIngredientId;
	}
	public String getDescription() {
		return m_description;
	}
	public void setDescription(String pDescription) {
		m_description = pDescription;
	}

	@XmlTransient
	public Set<UpcIngredient> getUpcIngredients() {
		return m_upcIngredients;
	}

	public void setUpcIngredients(Set<UpcIngredient> pUpcIngredients) {
		m_upcIngredients = pUpcIngredients;
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
	public Set<Promotion> getCoupans() {
		return m_coupans;
	}

	public void setCoupans(Set<Promotion> pPromotions) {
		m_coupans = pPromotions;
	}
	@XmlTransient
	public Set<RecipeIngredient> getRecipeIngredients() {
		return m_recipeIngredients;
	}

	public void setRecipeIngredients(Set<RecipeIngredient> pRecipeIngredient) {
		m_recipeIngredients = pRecipeIngredient;
	}

	public String getCategory() {
		return m_category;
	}

	public void setCategory(String pCategory) {
		m_category = pCategory;
	}

	public String getReadableDescription() {
		return m_readableDescription;
	}

	public void setReadableDescription(String pReadableDescription) {
		m_readableDescription = pReadableDescription;
	}

	public IngredientItem getIngredientItem() {
		return m_ingredientItem;
	}

	public void setIngredientItem(IngredientItem pIngredientItem) {
		m_ingredientItem = pIngredientItem;
	}
	//Helper meth to handle the UpcIngredients.
	public void addUpcIngredients(Collection<UPCItems> pUpcIngredients) {
		if (null == pUpcIngredients) {
			return;
		}
		getUpcIngredients().clear();
		for (UPCItems lUPCItem : pUpcIngredients) {
			UpcIngredient lUpcIngredient = new UpcIngredient();
			lUpcIngredient.setIngredientItem(getIngredientItem());
			lUpcIngredient.setUpc(lUPCItem);
			lUpcIngredient.addIngredient(this);
			getUpcIngredients().add(lUpcIngredient);
		}
	}

	public String getBasicItem() {
		return m_basicItem;
	}

	public void setBasicItem(String pBasicItem) {
		m_basicItem = pBasicItem;
	}

	public String getCreatedBy() {
		return m_createdBy;
	}

	public void setCreatedBy(String pCreatedBy) {
		m_createdBy = pCreatedBy;
	}

	public String getLastUpdatedBy() {
		return m_lastUpdatedBy;
	}

	public void setLastUpdatedBy(String pLastUpdatedBy) {
		m_lastUpdatedBy = pLastUpdatedBy;
	}
}
