/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.collections.set.SynchronizedSet;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.type.PromotionStatus;

/**
 * @author 
 *
 */
public abstract class Promotion implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected String m_code;

	protected String description;
	protected String title;
	protected String m_additional;
	protected String m_listingDeal;
	protected String m_originalPrice;
	protected String m_finalPrice;
	protected String m_finePrint;

	protected Date m_activationDate = new Date();
	protected Date m_expirationDate = new Date();
	protected String m_image;
	protected String m_imageUrl;
	protected String m_barcodeImage;
	protected String m_printImage;
	protected boolean m_special = false;
	protected PromotionStatus m_status;
	protected String m_type;
	protected Integer m_batchId;
	protected Set<IngredientItemPromotion> m_ingredientPromotions = SynchronizedSet.decorate(new HashSet<IngredientItemPromotion>());

	protected String m_createdBy;
	protected String m_lastUpdatedBy;
	protected long m_version;
	protected long m_itemCount;

	protected Chain m_chain;
	
	protected String m_externalId = "";

	protected Set<PromotionCategory> promotionCategory = SynchronizedSet.decorate(new HashSet<PromotionCategory>());

	private Date lastUpdate = new Date();
	private Date created = new Date();

	// non-persistence Obj
	protected UPCItems m_upcItem ;
	protected String m_storeAisle;
	protected String m_category;
	protected String m_imageBaseUrl;
	protected Set<Integer> m_ingredientIds = new HashSet<Integer>();

	@XmlList
	public Set<Integer> getIngredientIds() {
		return m_ingredientIds;
	}

	public void setIngredientIds(Set<Integer> pIds) {
		m_ingredientIds = pIds;
	}

	//helper method to add id
	public void addIngredientId(Integer pId) {
		m_ingredientIds.add(pId);
	}

	@XmlTransient
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date pCreated) {
		created = pCreated;
	}
	@XmlTransient
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date pLastUpdate) {
		lastUpdate = pLastUpdate;
	}
	public Date getActivationDate() {
		return m_activationDate;
	}
	public void setActivationDate(Date pActivationDate) {
		m_activationDate = pActivationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String pDescription) {
		description = pDescription;
	}
	public Date getExpirationDate() {
		return m_expirationDate;
	}
	public void setExpirationDate(Date pExpirationDate) {
		m_expirationDate = pExpirationDate;
	}
	public String getImage() {
		return m_image;
	}
	public void setImage(String pImage) {
		m_image = pImage;
	}
	public String getImageUrl() {
		return m_imageUrl;
	}

	public void setImageUrl(String pImageUrl) {
		m_imageUrl = pImageUrl;
	}

	public Integer getPromotionId() {
		return id;
	}
	public void setPromotionId(Integer pPromotionId) {
		id = pPromotionId;
	}
	public PromotionStatus getStatus() {
		return m_status;
	}
	public void setStatus(PromotionStatus pStatus) {
		m_status = pStatus;
	}
	public boolean isSpecial() {
		return m_special;
	}
	public void setSpecial(boolean pSpecial) {
		m_special = pSpecial;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String pTitle) {
		title = pTitle;
	}
	public UPCItems getUpcItem() {
		return m_upcItem;
	}
	public void setUpcItem(UPCItems pUpc) {
		m_upcItem = pUpc;
	}
	public String getCode() {
		return m_code;
	}
	public void setCode(String pCode) {
		m_code = pCode;
	}
	public String getType() {
		return m_type;
	}
	public void setType(String pType) {
		m_type = pType;
	}

	//Helper meth to handle the IngredientItemPromotions.
	public void addIngredientPromotion(IngredientItemPromotion pIngredientItemPromotion) {
		if (null == pIngredientItemPromotion) {
			return;
		}

		if (null == getIngredientPromotions()) {
			setIngredientPromotions(new HashSet<IngredientItemPromotion>());
		}

		getIngredientPromotions().add(pIngredientItemPromotion);
		pIngredientItemPromotion.setPromotion(this);
	}
	@XmlTransient
	public Set<IngredientItemPromotion> getIngredientPromotions() {
		return m_ingredientPromotions;
	}
	public void setIngredientPromotions(Set<IngredientItemPromotion> pItemPromotions) {
		m_ingredientPromotions = pItemPromotions;
	}
	public void addIngredientItems(Collection<IngredientItem> pItems) {
		if (null == pItems || pItems.isEmpty()) {
			return;
		}
		getIngredientPromotions().clear();
		//create a IngredientItemPromotion for each IngredientItem in the Collection
		for (IngredientItem lIngredientItem : pItems) {
			IngredientItemPromotion lIngredientItemPromotion = new IngredientItemPromotion();
			lIngredientItemPromotion.setIngredientItem(lIngredientItem);
			lIngredientItemPromotion.setPromotion(this);
			lIngredientItem.getIngredientItemPromotions().add(lIngredientItemPromotion);
			getIngredientPromotions().add(lIngredientItemPromotion);
		}

	}
	public String getStoreAisle() {
		return m_storeAisle;
	}
	public void setStoreAisle(String pStoreAisle) {
		m_storeAisle = pStoreAisle;
	}

	@XmlTransient
	public Set<PromotionCategory> getPromotionCategory() {
		return promotionCategory;
	}
	public void setPromotionCategory(Set<PromotionCategory> pPromotionCategory) {
		promotionCategory = pPromotionCategory;
	}
	@XmlTransient
	public Integer getBatchId() {
		return m_batchId;
	}
	public void setBatchId(Integer pBatchId) {
		m_batchId = pBatchId;
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

	public long getVersion() {
		return m_version;
	}

	public void setVersion(long pVersion) {
		m_version = pVersion;
	}

	public String getPrintImage() {
		return m_printImage;
	}

	public void setPrintImage(String pPrintImage) {
		m_printImage = pPrintImage;
	}

	public String getCategory() {
		return m_category;
	}

	public void setCategory(String pCategory) {
		m_category = pCategory;
	}
	public String getExternalId() {
		return m_externalId;
	}

	public void setExternalId(String pExternalId) {
		m_externalId = pExternalId;
	}

	public String getImageBaseUrl() {
		return m_imageBaseUrl;
	}

	public void setImageBaseUrl(String pImageBaseUrl) {
		m_imageBaseUrl = pImageBaseUrl;
	}

	public String getAdditional() {
		return m_additional;
	}

	public void setAdditional(String pAdditional) {
		m_additional = pAdditional;
	}

	public String getListingDeal() {
		return m_listingDeal;
	}

	public void setListingDeal(String pListingDeal) {
		m_listingDeal = pListingDeal;
	}

	public String getOriginalPrice() {
		return m_originalPrice;
	}

	public void setOriginalPrice(String pOriginalPrice) {
		m_originalPrice = pOriginalPrice;
	}

	public String getFinePrint() {
		return m_finePrint;
	}

	public void setFinePrint(String pFinePrint) {
		m_finePrint = pFinePrint;
	}

	public String getFinalPrice() {
		return m_finalPrice;
	}

	public void setFinalPrice(String pFinalPrice) {
		m_finalPrice = pFinalPrice;
	}

	public long getItemCount() {
		return m_itemCount;
	}

	public void setItemCount(long count) {
		m_itemCount = count;
	}

	public String getBarcodeImage() {
		return m_barcodeImage;
	}

	public void setBarcodeImage(String pBarcodeImage) {
		m_barcodeImage = pBarcodeImage;
	}
	public Chain getChain() {
		return m_chain;
	}
	public void setChain(Chain pChain) {
		m_chain = pChain;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Promotion == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Promotion rhs = (Promotion) obj;
		return new EqualsBuilder()
					.append(title, rhs.title)
					.append(description, rhs.description)
					.append(m_finalPrice, rhs.m_finalPrice)
					.append(m_externalId, rhs.m_externalId)
					.append(m_status, rhs.m_status)
					.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
					.append(title)
					.append(description)
					.append(m_finalPrice)
					.append(m_externalId)
					.append(m_status)
					.toHashCode();
	}


}
