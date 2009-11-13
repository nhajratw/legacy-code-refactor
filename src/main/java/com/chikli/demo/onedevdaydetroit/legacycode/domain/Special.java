/**
 * 
 */
package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;



/**
 * @author 
 *
 */
public class Special extends Promotion {

	private static final long serialVersionUID = 1L;
	protected Set<OneStop> m_oneStops = new HashSet<OneStop>();

	@XmlTransient
	public Set<OneStop> getOneStops() {
		return m_oneStops;
	}
	public void setOneStops(Set<OneStop> pOneStops) {
		m_oneStops = pOneStops;
	}

}
