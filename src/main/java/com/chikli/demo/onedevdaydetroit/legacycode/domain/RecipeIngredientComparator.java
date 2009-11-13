package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.Comparator;


/**
 *
 * @author 
 *
 * @version $Revision:1657 $ $Date:2008-02-01 13:03:12 -0800 (Fri, 01 Feb 2008) $ by $ $
 */public class RecipeIngredientComparator implements Comparator<RecipeIngredient> {
	 public int compare(RecipeIngredient pArg0, RecipeIngredient pArg1) {
		 if (null == pArg0 && null == pArg1 ) {
			 return 0;
		 }if(pArg0.getDescription() == null){
			 return 0;
		 }if(pArg1.getDescription() == null){
			 return 0;
		 }
		 //else if (null == pArg1) {
		 //			return 1;
		 //		} else if (null == pArg0) {
		 //			return -1;
		 //		} else if (null != pArg0.getLineNumber() &&  null != pArg1.getLineNumber()) {
		 //			return pArg0.getLineNumber().compareTo(pArg1.getLineNumber());
		 //		} else {
		 return pArg0.getDescription().compareTo(pArg1.getDescription());
		 //		}
	 }
 }