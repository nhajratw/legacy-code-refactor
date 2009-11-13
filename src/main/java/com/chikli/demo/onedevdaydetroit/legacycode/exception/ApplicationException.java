/**
 * $Id:ApplicationException.java 170 2007-07-24 00:57:18Z  $
 */
package com.chikli.demo.onedevdaydetroit.legacycode.exception;

/**
 * @author 
 *
 * @version $Revision:170 $ $Date:2007-07-23 17:57:18 -0700 (Mon, 23 Jul 2007) $ by $ $
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String pMsg) {
		super(pMsg);
	}

	public ApplicationException(String pMsg, Exception pParentException) {
		super(pMsg, pParentException);
	}

}
