/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Date;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.type.ChainStatus;

/**
 * @author 
 *
 */
public class Chain implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	protected Integer id;
	protected String m_name;
	protected String m_image;

	// feed fetcher data
	protected String m_baseUrl;
	protected String m_campaignId;
	protected ChainStatus m_status;

	protected Date m_created = new Date();
	protected Date m_lastUpdate = new Date();

	public Integer getId() {
		return id;
	}
	public void setId(Integer pId) {
		id = pId;
	}
	public String getName() {
		return m_name;
	}
	public void setName(String pName) {
		m_name = pName;
	}
	public String getImage() {
		return m_image;
	}
	public void setImage(String pImage) {
		m_image = pImage;
	}
	public String getBaseUrl() {
		return m_baseUrl;
	}
	public void setBaseUrl(String pBaseUrl) {
		m_baseUrl = pBaseUrl;
	}
	public String getCampaignId() {
		return m_campaignId;
	}
	public void setCampaignId(String pCampaignId) {
		m_campaignId = pCampaignId;
	}
	public ChainStatus getStatus() {
		return m_status;
	}
	public void setStatus(ChainStatus pStatus) {
		m_status = pStatus;
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

}
