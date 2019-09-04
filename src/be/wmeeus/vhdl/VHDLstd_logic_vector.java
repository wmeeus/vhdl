package be.wmeeus.vhdl;

import java.util.Hashtable;

/**
 * Class VHDLstd_logic_vector represents the std_logic_vector type in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLstd_logic_vector extends VHDLtype {
	/**
	 * Range of the vector
	 */
	VHDLrange range = null;
	
	/**
	 * A static std_logic_vector without a range
	 */
	public static final VHDLstd_logic_vector STD_LOGIC_VECTOR = new VHDLstd_logic_vector();
	
	/**
	 * Construct a std_logic_vector without a range
	 */
	public VHDLstd_logic_vector() {
	}
	
	/**
	 * Construct a std_logic_vector
	 * @param s start of the range
	 * @param d direction of the range (to/downto)
	 * @param e end of the range
	 */
	public VHDLstd_logic_vector(VHDLnode s, String d, VHDLnode e) {
		range = new VHDLrange(s, d, e);
	}
	
	/**
	 * Construct a std_logic_vector
	 * @param s start of the range
	 * @param e end of the range
	 */
	public VHDLstd_logic_vector(int s, int e) {
		range = new VHDLrange(s, e);
	}
	
	/**
	 * Construct a std_logic_vector
	 * @param s number of bits in the vector. The range will be (s-1) downto 0
	 */
	public VHDLstd_logic_vector(int s) {
		range = new VHDLrange(s);
	}
	
	/**
	 * Construct a std_logic_vector. 
	 * @param s either the range of the vector (object of class VHDLrange) or the number of bits,
	 *   in which case the range is (s-1) downto 0.
	 */
	public VHDLstd_logic_vector(VHDLnode s) {
		if (s instanceof VHDLrange) {
			range = (VHDLrange)s;
		} else {
			range = new VHDLrange(s);
		}
	}
	
	/**
	 * Construct a std_logic_vector.
	 * @param s a String description of the range. See class VHDLrange for the supported format. 
	 * @throws VHDLexception
	 */
	public VHDLstd_logic_vector(String s) throws VHDLexception {
		// throw out brackets
		if (s.startsWith("(")) s = s.substring(1);
		if (s.endsWith(")")) s = s.substring(0,  s.length()-1);
		range = new VHDLrange(s);
	}
	
	/**
	 * Returns a String representation of this std_logic_vector
	 */
	public String toString() {
		if (range==null) return "std_logic_vector";
		return "std_logic_vector(" + range + ")"; 
	}
	
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		VHDLrange rrange = (VHDLrange)(range.replace(replacetable));
		if (rrange!=null) return new VHDLstd_logic_vector(rrange);
		return null;
	}

	/**
	 * A list of generated std_logic_vectors
	 */
	static Hashtable<Object, VHDLstd_logic_vector> defvectors = new Hashtable<Object, VHDLstd_logic_vector>();
	
	/**
	 * Get a std_logic_vector type with the given width. 
	 * @param i the width
	 * @return the requested std_logic_vector type.
	 */
	public static VHDLtype getVector(int i) {
		if (i == 1) return VHDLstd_logic.STD_LOGIC;
		if (defvectors.contains(Integer.valueOf(i))) 
			return defvectors.get(Integer.valueOf(i));
		VHDLstd_logic_vector vi =new VHDLstd_logic_vector(i);
		defvectors.put(Integer.valueOf(i), vi);
		return vi;
	}
	
	public VHDLnode mkValue(String s) throws VHDLexception {
		if (s.equals("open")) return VHDLvalue.OPEN;
		if (s.startsWith("all:")) {
			String ss = s.substring(4);
			if (ss.equals("0")) return VHDLvalue.OTHERS0;
			if (ss.equals("1")) return VHDLvalue.OTHERS1;
			if (ss.equals("-") || s.equals("dc")) return VHDLvalue.OTHERSDC;
		}
		throw new VHDLexception("std_logic_vector value: " + s + " unsupported");
	}
}
