package be.wmeeus.vhdl;

import java.util.*;

/**
 * Class VHDLtype represents a type in VHDL. Specific types can be subclasses of this class. 
 * @author wmeeus
 *
 */
public class VHDLtype extends VHDLnode {
	/**
	 * Default constructor: construct an empty type
	 */
	public VHDLtype() {}
	
	/**
	 * A table of all types
	 */
	static Hashtable<String, VHDLtype> types = new Hashtable<String, VHDLtype>();
	
	/**
	 * Construct a named type
	 * @param n the type name
	 */
	public VHDLtype(String n) {
		name = n;
		types.put(n, this);
	}
	
	/**
	 * Returns a String representation of this type
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Get a particular type. Attempt to reuse an existing object that represents the requested
	 * type, construct a new type if necessary 
	 * @param s the type name
	 * @return the requested type
	 * @throws VHDLexception
	 */
	public static VHDLtype getType(String s) throws VHDLexception {
		if (types.containsKey(s)) return types.get(s);
		VHDLtype t = null;

		// TODO try to figure out the actual data type (std_logic etc)
		if (s.equals("std_logic")) return VHDLstd_logic.STD_LOGIC;
		if (s.startsWith("std_logic_vector")) {
			String v = s.substring(16).trim();
			if (v.startsWith("(")) v = v.substring(1);
			if (v.endsWith(")"))   v = v.substring(0,  v.length()-1);
			v = v.trim();
			VHDLgeneric lg = (VHDLgeneric)(VHDLsymbol.get(v));
			if (lg!=null) return new VHDLstd_logic_vector(lg);
			return new VHDLstd_logic_vector(v);
		}
		if (s.startsWith("natural")) return VHDLinteger.NATURAL; // TODO add range
		if (s.startsWith("integer")) return VHDLinteger.INTEGER; // TODO add range
		
		t = new VHDLtype(s);
		return t;
	}
}
