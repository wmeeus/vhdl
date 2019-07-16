package be.wmeeus.vhdl;

/**
 * Class VHDLinteger describes the integer data type
 * @author Wim Meeus
 *
 */
public class VHDLinteger extends VHDLtype {
	/**
	 * Range of this integer
	 */
	VHDLrange range = null;
	
	/**
	 * Is this a natural data type?
	 */
	boolean isNatural = false;
	
	/**
	 * A static integer type without an indicated range
	 */
	public static final VHDLinteger INTEGER = new VHDLinteger();
	
	/**
	 * A static natural type without an indicated range
	 */
	public static final VHDLinteger NATURAL = new VHDLinteger(true);
	
	/**
	 * Construct an integer type
	 */
	public VHDLinteger() {}
	
	/**
	 * Construct an integer or natural type
	 * @param natural true for natural, false for (signed) integer
	 */
	public VHDLinteger(boolean natural) {
		isNatural = natural;
	}
	
	/**
	 * Construct an integer type with a particular range
	 * @param min the minimum value
	 * @param max the maximum value
	 */
	public VHDLinteger(int min, int max) {
		range = new VHDLrange(min, max);		
	}
	
	/**
	 * Construct an integer type with a particular range
	 * @param natural  true for natural, false for (signed) integer
	 * @param min the minimum value
	 * @param max the maximum value
	 */
	public VHDLinteger(boolean natural, int min, int max) {
		isNatural = natural;
		range = new VHDLrange(min, max);		
	}
	
	/**
	 * Returns a String representation of this integer type
	 */
	public String toString() {
		String r = isNatural?"natural":"integer";
		if (range!=null) r += " range " + range;
		return r;
	}
}
