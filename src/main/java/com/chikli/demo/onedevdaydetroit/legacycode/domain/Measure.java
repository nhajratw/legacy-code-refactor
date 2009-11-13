package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import org.apache.commons.lang.math.Fraction;

import com.chikli.demo.onedevdaydetroit.legacycode.exception.ApplicationException;


/**
 * Encapsulate quantity and unit data for
 * an ingredient as well provide conversion
 * methods.
 * 
 * @author 
 *
 */
public class Measure {

	//initial text values
	protected String m_baseUnit;
	protected String m_baseQty;

	//parsed values
	protected Unit m_unit;
	protected Fraction m_quantity;


	//converted values
	protected Unit m_normalizedUnit;
	protected double m_normalizedQuantity;


	/*
	 * Build the quantity and unit values.  Stores the original
	 * values but also converts to the base unit for the given type.
	 * 
	 * i.e if given lbs will convert to grams and update the quantity.
	 * 
	 * 
	 */
	public Measure(String pQty, String pUnit,
			Fraction pParsedQuantity, Unit pParsedUnit,
			double pNormalizedQuantity, Unit pNormalizedUnit) throws ApplicationException {
		m_baseQty = pQty;
		m_baseUnit = pUnit;
		m_quantity = pParsedQuantity;
		m_unit = pParsedUnit;
		m_normalizedUnit = pNormalizedUnit;
		m_normalizedQuantity = pNormalizedQuantity;
	}

	@Override
	public String toString() {

		if (null != m_quantity && null != m_unit) {
			return m_quantity.toProperString() + " " + m_unit.toString();
		} else {
			return m_baseQty + " " + m_baseUnit;
		}
	}

	// -- Getters and Setters


	public String getBaseUnit() {
		return m_baseUnit;
	}

	public void setBaseUnit(String pBaseUnit) {
		m_baseUnit = pBaseUnit;
	}

	public String getBaseQty() {
		return m_baseQty;
	}

	public void setBaseQty(String pBaseQty) {
		m_baseQty = pBaseQty;
	}

	public Unit getUnit() {
		return m_unit;
	}

	public void setUnit(Unit pUnit) {
		m_unit = pUnit;
	}

	public Fraction getQuantity() {
		return m_quantity;
	}

	public void setQuantity(Fraction pQuantity) {
		m_quantity = pQuantity;
	}

	public Unit getNormalizedUnit() {
		return m_normalizedUnit;
	}

	public void setNormalizedUnit(Unit pNormalizedUnit) {
		m_normalizedUnit = pNormalizedUnit;
	}

	public double getNormalizedQuantity() {
		return m_normalizedQuantity;
	}

	public void setNormalizedQuantity(double pNormalizedQuantity) {
		m_normalizedQuantity = pNormalizedQuantity;
	}

}
