package com.chikli.demo.onedevdaydetroit.legacycode.domain;

import java.util.HashMap;
import java.util.Map;


/**
 * Describe a unit of measure for an item.
 * 
 * These probably shouldn't be setup as static contstant values but
 * rather created dynamically via a property file or db.
 * 
 * Conversion values are courtesy of Google.
 * 
 * 
 * @author 
 * @version $Revision: 13686 $ $Date: 05-Dec-08 11:31:16 AM $ by $Author:  $
 */
public class Unit {
	public static final String WEIGHT = "WEIGHT";
	public static final String VOLUME = "VOLUME";
	public static final String PACKAGE = "PACKAGE";

	public static final Unit BasePackageUnit = new Unit(PACKAGE, "EACH", 1d);
	public static final Unit BOX = new Unit(PACKAGE, "BOX", 1d);
	public static final Unit PKG = new Unit(PACKAGE, "PKG", 1d);
	public static final Unit CAN = new Unit(PACKAGE, "CAN", 1d);
	public static final Unit JAR = new Unit(PACKAGE, "JAR", 1d);
	public static final Unit SQUARE = new Unit(PACKAGE, "SQUARE", 1d);
	public static final Unit SLICE = new Unit(PACKAGE, "SLICE", 1d);
	public static final Unit CONTAINER = new Unit(PACKAGE, "CONTAINER", 1d);
	public static final Unit BUNCH = new Unit(PACKAGE, "BUNCH", 1d);

	public static final Unit BaseWeightUnit = new Unit(WEIGHT, "G", 1d);

	public static final Unit OZ = new Unit(WEIGHT, "OZ", 28.3495231d);
	public static final Unit LB = new Unit(WEIGHT, "LB", 453.59237d);
	public static final Unit KG = new Unit(WEIGHT, "KG", 1000d);

	public static final Unit BaseVolumeUnit = new Unit(VOLUME, "ML", 1d);

	public static final Unit FL_OZ = new Unit(VOLUME, "FL_OZ", 29.5735296d);
	public static final Unit CUP = new Unit(VOLUME, "CUP", 236.588237d);
	public static final Unit TBSP = new Unit(VOLUME, "TBSP", 14.7867648d);
	public static final Unit TSP = new Unit(VOLUME, "TSP", 4.92892159d);
	public static final Unit LITER = new Unit(VOLUME, "LITER", 1000d);
	public static final Unit GALLON = new Unit(VOLUME, "GALLON", 3785.41178d);
	public static final Unit QUART = new Unit(VOLUME, "QUART", 946.352946d);
	public static final Unit PINT = new Unit(VOLUME, "PINT", 473.176473d);

	//aliases
	public static final Unit EACH = BasePackageUnit;
	public static final Unit G = BaseWeightUnit;
	public static final Unit ML = BaseVolumeUnit;

	private static Map<String, Unit> s_unitMap = new HashMap<String, Unit>();

	static {

		s_unitMap.put("BOX", BOX);
		s_unitMap.put("PKG", PKG);
		s_unitMap.put("CAN", CAN);
		s_unitMap.put("JAR", JAR);
		s_unitMap.put("SQUARE", SQUARE);
		s_unitMap.put("SLICE", SLICE);
		s_unitMap.put("BUNCH", BUNCH);
		s_unitMap.put("CONTAINER", CONTAINER);
		s_unitMap.put("OZ", OZ);
		s_unitMap.put("LB", LB);
		s_unitMap.put("KG", KG);
		s_unitMap.put("FL_OZ", FL_OZ);
		s_unitMap.put("CUP", CUP);
		s_unitMap.put("TBSP", TBSP);
		s_unitMap.put("TSP", TSP);
		s_unitMap.put("LITER", LITER);
		s_unitMap.put("GALLON", GALLON);
		s_unitMap.put("QUART", QUART);
		s_unitMap.put("PINT", PINT);
		s_unitMap.put("EACH", EACH);
		s_unitMap.put("G", G);
		s_unitMap.put("ML", ML);
	}



	protected String m_type;
	protected String m_description;
	protected Double m_coeffient;

	protected Unit(String pType, String pDescription, Double pCoefficient) {
		m_type = pType;
		m_description = pDescription;
		m_coeffient = pCoefficient;
	}

	public static Unit fromString(String pName) {
		Unit lUnit = s_unitMap.get(pName);
		return lUnit;
	}

	// -- Getters - immutable object so no setters

	public String getType() {
		return m_type;
	}
	public String getDescription() {
		return m_description;
	}
	public Double getCoeffient() {
		return m_coeffient;
	}

	@Override
	public String toString() {
		return m_description;
	}



}
