package com.chikli.demo.onedevdaydetroit.legacycode.domain;


/**
 * Represents the differing textual details that can be associated with a OneStop ad/UPC.
 * For example, the batch may contain the same UPC many times over because it can represent
 * different product classes.  We want to allow for reuse of a UPC in the batch file
 * to represent distinct promotions and at the same time allowing for equivalent ad copy
 * to be associated with the same OneStop.
 *
 * @author 
 *
 * @version $Revision: 98 $ $Date: 2007-10-16 02:03:08 -0700 (Tue, 16 Oct 2007) $ by $Author:  $
 */
public class OneStopDetail {

	protected Integer m_id;
	protected OneStop m_oneStop;

	protected String m_body;
	protected String m_header;
	protected String m_image;

	public Integer getId() {
		return m_id;
	}

	public void setId(Integer pId) {
		m_id = pId;
	}

	public OneStop getOneStop() {
		return m_oneStop;
	}

	public void setOneStop(OneStop pOneStop) {
		m_oneStop = pOneStop;
	}

	public String getBody() {
		return m_body;
	}

	public void setBody(String pBody) {
		m_body = pBody;
	}

	public String getHeader() {
		return m_header;
	}

	public void setHeader(String pHeader) {
		m_header = pHeader;
	}

	public String getImage() {
		return m_image;
	}

	public void setImage(String pImage) {
		m_image = pImage;
	}

}
