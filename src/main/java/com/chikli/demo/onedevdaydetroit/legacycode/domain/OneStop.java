package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.type.OneStopStatus;

/**
 * Represents ads and UPCs we receive in a OneStop batch file.  The OneStop will end
 * up being associated with a Set of Promotions (that are created based on the batch information)
 * and a Set of UpcIngredients and one or more OneStopUpcs.  The Promotions will be created automatically as part of the batch
 * process but the UpcIngredients will be associated as part of a manual mapping process.  When the
 * mapping process is complete we will have a relationship to UPCs coming in the batch to a set
 * of UpcIngredients in the system that will be reusable in future batches.  We use a set of OneStopUpcs
 * because this allows reuse of the grouping that the business has already done with UPCs when they put
 * multiple products in one ad (block w/ multiple upcs sharing a single image).
 *
 * @author 
 *
 * @version $Revision: 98 $ $Date: 2007-10-16 02:03:08 -0700 (Tue, 16 Oct 2007) $ by $Author:  $
 */
public class OneStop {

	protected Integer m_id;
	protected String m_latestImage;
	protected OneStopStatus m_status;
	protected boolean m_ignored = false;
	protected Date m_created = new Date();
	protected Date m_lastUpdate;

	protected Set<OneStopUpc> m_upcCodes = new HashSet<OneStopUpc>();
	protected Set<Promotion> m_promotions = new HashSet<Promotion>();
	protected Set<IngredientItem> m_ingredientItems = new HashSet<IngredientItem>();

	protected Set<OneStopDetail> m_details = new HashSet<OneStopDetail>();

	protected Integer m_activePromotions;
	protected Integer m_inActivePromotions;

	protected Integer m_ingredientItemCounts;

	public Integer getId() {
		return m_id;
	}
	public void setId(Integer pId) {
		m_id = pId;
	}
	public Set<OneStopUpc> getUpcCodes() {
		return m_upcCodes;
	}
	public void setUpcCodes(Set<OneStopUpc> pUpcCodes) {
		m_upcCodes = pUpcCodes;
	}
	public Set<Promotion> getPromotions() {
		return m_promotions;
	}
	public void setPromotions(Set<Promotion> pPromotions) {
		m_promotions = pPromotions;
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
	public String getLatestImage() {
		return m_latestImage;
	}
	public void setLatestImage(String pLatestImage) {
		m_latestImage = pLatestImage;
	}
	public Integer getActivePromotions() {
		return m_activePromotions;
	}
	public void setActivePromotions(Integer pActivePromotions) {
		m_activePromotions = pActivePromotions;
	}
	public Integer getInActivePromotions() {
		return m_inActivePromotions;
	}
	public void setInActivePromotions(Integer pInActivePromotions) {
		m_inActivePromotions = pInActivePromotions;
	}
	public OneStopStatus getStatus() {
		return m_status;
	}
	public void setStatus(OneStopStatus pStatus) {
		m_status = pStatus;
	}

	@XmlTransient
	public Set<OneStopDetail> getDetails() {
		return m_details;
	}
	public void setDetails(Set<OneStopDetail> pDetails) {
		m_details = pDetails;
	}
	public Integer getIngredientItemCounts() {
		return m_ingredientItemCounts;
	}
	public void setIngredientItemCounts(Integer pIngredientItemCounts) {
		m_ingredientItemCounts = pIngredientItemCounts;
	}
	public boolean isIgnored() {
		return m_ignored;
	}
	public void setIgnored(boolean pIgnore) {
		m_ignored = pIgnore;
	}

}
