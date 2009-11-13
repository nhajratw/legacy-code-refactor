package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Contains the xxx UPC product details.  These fields
 * (with the possible exception of code) are specific to
 * xxx's system and derived from their data dumps,
 * so this class may need subclassing when new vendors
 * are brought online.
 * 
 * @author 
 *
 */

//lucene-indexed to allow for easier mapping to Ingredient Items in admin tool
public class UPCItems implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long code;

	private String m_description;
	private String m_primaryCategory;
	private String m_mprsCategory;
	private String m_productCategory;
	private Integer m_categoryId;
	private String m_storeAisle;


	//Non persisted data - from web service
	private String m_package;
	private Double m_price;  //dependent on store
	private Double m_salePrice; //dependent on store

	//decorated values
	private Measure m_measure;  //parse package size


	// -- Constructors
	public UPCItems() {	}

	public UPCItems(Long pCode) {
		code = pCode;
	}

	public UPCItems(Long pCode, String pDescription,
			String pPackage, String pPrimaryCategory, String pMprsCategory,
			String pProductCategory, Integer pCategoryId, String pStoreAisle) {
		code = pCode;
		m_description = pDescription;
		m_primaryCategory = pPrimaryCategory;
		m_mprsCategory = pMprsCategory;
		m_productCategory = pProductCategory;
		m_package = pPackage;
		m_categoryId = pCategoryId;
		m_storeAisle = pStoreAisle;
	}

	// -- Getters and Setters

	public Long getCode() {
		return code;
	}

	public void setCode(Long pCode) {
		code = pCode;
	}

	public String getDescription() {
		return m_description;
	}

	public void setDescription(String pDescription) {
		m_description = pDescription;
	}

	public String getPackage() {
		return m_package;
	}

	public void setPackage(String pPackage) {
		m_package = pPackage;
	}

	public Integer getCategoryId() {
		return m_categoryId;
	}

	public void setCategoryId(Integer pCategoryId) {
		m_categoryId = pCategoryId;
	}

	public String getStoreAisle() {
		return m_storeAisle;
	}

	public void setStoreAisle(String pStoreAisle) {
		m_storeAisle = pStoreAisle;
	}

	public Double getPrice() {
		return m_price;
	}

	public void setPrice(Double pPrice) {
		m_price = pPrice;
	}

	public Double getSalePrice() {
		return m_salePrice;
	}

	public void setSalePrice(Double pSalePrice) {
		m_salePrice = pSalePrice;
	}

	public String getPrimaryCategory() {
		return m_primaryCategory;
	}

	public void setPrimaryCategory(String pPrimaryCategory) {
		m_primaryCategory = pPrimaryCategory;
	}

	public String getMprsCategory() {
		return m_mprsCategory;
	}

	public void setMprsCategory(String pMprsCategory) {
		m_mprsCategory = pMprsCategory;
	}

	public String getProductCategory() {
		return m_productCategory;
	}

	public void setProductCategory(String pProductCategory) {
		m_productCategory = pProductCategory;
	}

	@XmlTransient
	public Measure getMeasure() {
		return m_measure;
	}

	public void setMeasure(Measure pMeasure) {
		m_measure = pMeasure;
	}



}

