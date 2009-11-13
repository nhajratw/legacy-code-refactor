/**
 * $Id:RecipeIngredient.java 1657 2008-02-01 21:03:12Z  $
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 
 *
 * @version $Revision:1657 $ $Date:2008-02-01 13:03:12 -0800 (Fri, 01 Feb 2008) $ by $ $
 */
public class RecipeIngredient implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Fields
	protected Integer recipeIngredientId;

	protected Ingredient m_ingredient;

	protected String type;
	protected String customerId;
	protected String amount;
	protected String typePlural;
	protected String quantityNo;
	protected Integer lineNumber;

	protected String description;

	protected Date lastUpdate = new Date();
	protected Date created = new Date();

	//helping non-persistence variable.
	protected String m_recipeIngredientIds;


	// Constructors

	/** default constructor */
	public RecipeIngredient() {
	}

	/** minimal constructor */
	public RecipeIngredient(Integer recipeIngredientId,
			String type, Date lastUpdate, Date created) {
		this.recipeIngredientId = recipeIngredientId;
		this.type = type;
		this.lastUpdate = lastUpdate;
		this.created = created;
	}

	/** full constructor */
	public RecipeIngredient(Integer recipeIngredientId,
			Ingredient pIngredient, String type, String customerId, String amount, String typePlural,
			String quantityNo, Integer lineNumber, String description,
			Date lastUpdate, Date created) {
		this.recipeIngredientId = recipeIngredientId;
		this.type = type;
		this.m_ingredient = pIngredient;
		this.customerId = customerId;
		this.amount = amount;
		this.typePlural = typePlural;
		this.quantityNo = quantityNo;
		this.lineNumber = lineNumber;
		this.description = description;
		this.lastUpdate = lastUpdate;
		this.created = created;
	}

	// Property accessors

	public Integer getRecipeIngredientId() {
		return this.recipeIngredientId;
	}

	public void setRecipeIngredientId(Integer recipeIngredientId) {
		this.recipeIngredientId = recipeIngredientId;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTypePlural() {
		return this.typePlural;
	}

	public void setTypePlural(String typePlural) {
		this.typePlural = typePlural;
	}

	public String getQuantityNo() {
		return this.quantityNo;
	}

	public void setQuantityNo(String quantityNo) {
		this.quantityNo = quantityNo;
	}

	public Integer getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlTransient
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@XmlTransient
	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Ingredient getIngredient() {
		return m_ingredient;
	}

	public void setIngredient(Ingredient pIngredient) {
		m_ingredient = pIngredient;
	}

	public String getRecipeIngredientIds() {
		return m_recipeIngredientIds;
	}

	public void setRecipeIngredientIds(String pRecipeIngredientIds) {
		m_recipeIngredientIds = pRecipeIngredientIds;
	}

}