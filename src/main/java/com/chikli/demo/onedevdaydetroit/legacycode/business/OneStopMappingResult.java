package com.chikli.demo.onedevdaydetroit.legacycode.business;

import java.util.Set;

import com.chikli.demo.onedevdaydetroit.legacycode.domain.OneStop;


/**
 * DTO class to hold the results of a PromotionToOneStop
 * mapping process
 */
public class OneStopMappingResult {

	protected Integer m_oneStopsCreated;
	protected Integer m_oneStopsUpdated;
	protected Integer m_oneStopsMappedAutomatically;
	protected Set<OneStop> m_oneStops;
	protected Set<String> m_upcs;

	public OneStopMappingResult(Integer pOneStopsCreated,
			Integer pOneStopsUpdated,
			Integer pOneStopsMappedAutomatically,
			Set<OneStop> pOneStops,
			Set<String> pUpcs) {

		m_oneStopsCreated = pOneStopsCreated;
		m_oneStopsUpdated = pOneStopsUpdated;
		m_oneStopsMappedAutomatically = pOneStopsMappedAutomatically;
		m_oneStops = pOneStops;
		m_upcs = pUpcs;

	}


	public Integer getOneStopsCreated() {
		return m_oneStopsCreated;
	}
	public void setOneStopsCreated(Integer pOneStopsCreated) {
		m_oneStopsCreated = pOneStopsCreated;
	}
	public Integer getOneStopsUpdated() {
		return m_oneStopsUpdated;
	}
	public void setOneStopsUpdated(Integer pOneStopsUpdated) {
		m_oneStopsUpdated = pOneStopsUpdated;
	}
	public Integer getOneStopsMappedAutomatically() {
		return m_oneStopsMappedAutomatically;
	}
	public void setOneStopsMappedAutomatically(Integer pOneStopsMappedAutomatically) {
		m_oneStopsMappedAutomatically = pOneStopsMappedAutomatically;
	}
	public Set<OneStop> getOneStops() {
		return m_oneStops;
	}
	public void setOneStops(Set<OneStop> pOneStops) {
		m_oneStops = pOneStops;
	}


	public Set<String> getUpcs() {
		return m_upcs;
	}


	public void setUpcs(Set<String> pUpcs) {
		m_upcs = pUpcs;
	}

}
