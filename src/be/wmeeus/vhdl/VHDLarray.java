package be.wmeeus.vhdl;
import be.wmeeus.util.*;

/**
 * Class VHDLarray describes an array type in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLarray extends VHDLtype {
	/**
	 * Base type of the array
	 */
	VHDLtype basetype = null;
	
	/**
	 * Array range
	 */
	VHDLrange range = null;
	
	/**
	 * Constructs a VHDL array
	 * @param n type name
	 * @param t base type
	 * @param r array range
	 */
	public VHDLarray(String n, VHDLtype t, VHDLrange r) {
		name = n;
		basetype = t;
		range = r;
	}
	
	/**
	 * Constructs a VHDL array
	 * @param n type name
	 * @param t base type
	 * @param rs start of the range
	 * @param re end of the range
	 */
	public VHDLarray(String n, VHDLtype t, int rs, int re) {
		name = n;
		basetype = t;
		range = new VHDLrange(rs, re);
	}
	
	/**
	 * Constructs a VHDL array
	 * @param n type name
	 * @param t base type
	 * @param rs start of the range
	 * @param d to / downto
	 * @param re end of the range
	 */
	public VHDLarray(String n, VHDLtype t, VHDLnode rs, String d, VHDLnode re) {
		name = n;
		basetype = t;
		range = new VHDLrange(rs, d, re);
	}

	/**
	 * Returns the type definition in VHDL (type n is array range ...)
	 * @return the type definition in VHDL
	 */
	public String toStringDef() {
		String r = PP.I + "type " + name + " is array (range " + range + ") of " + basetype + ";\n";
		return r;
	}
	
	/**
	 * Returns a String representation of this array
	 */
	public String toString() {
		return name + "( " + range + " )";
	}
}
