package be.wmeeus.vhdl;

import java.util.Hashtable;

/**
 * Class VHDLsubarray represents an array datatype that is derived from another array datatype by
 * setting explicit array bounds. 
 * @author Wim Meeus
 *
 */
public class VHDLsubarray extends VHDLtype {
	/**
	 * Base datatype
	 */
	VHDLtype base = null;
	
	/**
	 * Array range
	 */
	VHDLrange range = null;
	
	/**
	 * Constructs a sub-array datatype
	 * @param n the base array type
	 * @param s start of the range
	 * @param e end of the range
	 */
	public VHDLsubarray(VHDLtype n, int s, int e) {
		base = n;
		range = new VHDLrange(s, e);
	}
	
	/**
	 * Constructs a sub-array datatype
	 * @param n the base array type
	 * @param s the number of elements in the range. The range is (s-1) downto 0
	 */
	public VHDLsubarray(VHDLtype n, int s) {
		base = n;
		range = new VHDLrange(s);
	}
	
	/**
	 * Constructs a sub-array datatype
	 * @param t the base array type
	 * @param n the range
	 */
	private VHDLsubarray(VHDLtype t, VHDLrange n) {
		base = t;
		range = n;
	}
	
	/**
	 * Returns a String representation of this sub-array type
	 */
	public String toString() {
		return base.name + "(" + range + ")";
	}
	
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		boolean do_replace=false;
		VHDLtype rbase=(VHDLtype)(base.replace(replacetable));
		if (rbase!=null) {
			do_replace=true;
		} else {
			rbase=base;
		}
		VHDLrange rrange = null;
		if (range!=null) {
			rrange = (VHDLrange)(range.replace(replacetable));
			if (rrange!=null) {
				do_replace=true;
			} else {
				rrange = range;
			}
		}
		if (do_replace) return new VHDLsubarray(rbase, rrange);
		return null;
	}

}
