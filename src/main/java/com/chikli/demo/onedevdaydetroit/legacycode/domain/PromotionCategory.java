
package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.collections.set.SynchronizedSet;


/**
 * A category of Promotions.  Used when grouping OneStop promotions
 * received from xxx.
 *
 * @author 
 *
 * @version $Revision: 98 $ $Date: 2007-10-16 02:03:08 -0700 (Tue, 16 Oct 2007) $ by $Author:  $
 */
public class PromotionCategory {

	private Integer m_promotionCategoryId;

	private String name;

	//Department name as provided by OneStop, used for lookup
	//or the SL category Id
	private String m_refName;

	private Integer m_sortIdx;
	private Boolean m_active;
	private String m_image;
	private Chain m_chain;

	private Set<Promotion> m_promotions = SynchronizedSet.decorate(new HashSet<Promotion>());

	private Date m_created;
	private Date m_lastUpdate;


	public Integer getPromotionCategoryId() {
		return m_promotionCategoryId;
	}
	public void setPromotionCategoryId(Integer pPromotionCategoryId) {
		m_promotionCategoryId = pPromotionCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String pName) {
		name = pName;
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
	public Set<Promotion> getPromotions() {
		return m_promotions;
	}
	public void setPromotions(Set<Promotion> pPromotions) {
		m_promotions = pPromotions;
	}
	public Integer getSortIdx() {
		return m_sortIdx;
	}
	public void setSortIdx(Integer pSortIdx) {
		m_sortIdx = pSortIdx;
	}
	public String getRefName() {
		return m_refName;
	}
	public void setRefName(String pRefName) {
		m_refName = pRefName;
	}
	public Boolean getActive() {
		return m_active;
	}
	public void setActive(Boolean pActive) {
		m_active = pActive;
	}
	public String getImage() {
		return m_image;
	}
	public void setImage(String pImage) {
		m_image = pImage;
	}
	public Chain getChain() {
		return m_chain;
	}
	public void setChain(Chain pChain) {
		m_chain = pChain;
	}

}
