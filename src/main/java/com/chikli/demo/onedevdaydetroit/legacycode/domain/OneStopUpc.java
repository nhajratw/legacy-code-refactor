package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.io.Serializable;


/**
 *
 * @author 
 *
 * @version $Revision: 98 $ $Date: 2007-10-16 02:03:08 -0700 (Tue, 16 Oct 2007) $ by $Author:  $
 */
public class OneStopUpc implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String m_upc;
	protected OneStop m_oneStop;

	public OneStopUpc() {}

	public OneStopUpc(String pUpc, OneStop pOneStop) {
		m_upc = pUpc;
		m_oneStop = pOneStop;
	}

	public String getUpc() {
		return m_upc;
	}
	public void setUpc(String pUpc) {
		m_upc = pUpc;
	}
	public OneStop getOneStop() {
		return m_oneStop;
	}
	public void setOneStop(OneStop pOneStop) {
		m_oneStop = pOneStop;
	}

	//Override equals and hashCode
	@Override
	public boolean equals(Object pObject) {
		if (this == pObject) {
			return true;
		}

		if (m_upc == null || m_oneStop == null) {
			return false;
		}

		if ( !(pObject instanceof OneStopUpc) ) {
			return false;
		}

		final OneStopUpc lObject = (OneStopUpc) pObject;

		return this.m_upc.equals(lObject.getUpc()) && this.m_oneStop.equals(lObject.getOneStop());
	}
	@Override
	public int hashCode() {
		int lResult = 14;
		lResult = 29 * lResult + m_upc.hashCode();
		lResult = 29 * lResult + m_oneStop.hashCode();
		return lResult;
	}


}
