package be.wmeeus.vhdl;

import java.util.Hashtable;

/**
 * Class VHDLsubrange represents an index or a range of an instance of an array datatype. 
 * @author Wim Meeus
 *
 */
public class VHDLsubrange extends VHDLnode {
	/**
	 * Base node (signal, variable, port...)
	 */
	VHDLnode base = null;
	
	/**
	 * The range (in case of a range). If range is used, index must be null.
	 */
	VHDLrange range = null;
	
	/**
	 * Index (in case of a single index). If index is used, range must be null
	 */
	VHDLnode index = null;
	
	/**
	 * Construct a subrange
	 * @param n the base node
	 * @param s
	 * @param e
	 */
	public VHDLsubrange(VHDLnode n, int s, int e) {
		base = n;
		range = new VHDLrange(s, e);
	}
	
	/**
	 * Construct a sub-index
	 * @param n the base node
	 * @param s the index
	 */
	public VHDLsubrange(VHDLnode n, int s) {
		base = n;
		index = new VHDLvalue(s);
	}
	
	/**
	 * Construct a subnode or a sub-index
	 * @param n the base node
	 * @param s the range or index. If s is of class VHDLrange, a range is assumed. Otherwise,
	 *   an index is assumed.
	 */
	public VHDLsubrange(VHDLnode n, VHDLnode s) {
		base = n;
		if (s instanceof VHDLrange) {
			range = (VHDLrange) s;
		} else {
			index = s;
		}
	}
	
	/**
	 * Private constructor, should only be used for copy operations. Either r or s must be null.
	 * 
	 * @param n the base node
	 * @param r the range
	 * @param s the index
	 */
	private VHDLsubrange(VHDLnode n, VHDLrange r, VHDLnode s) {
		base = n;
		range = r;
		index = s;
	}
	
	public String toString() {
		if (index!=null) return base.name + "(" + index + ")";
		return base.name + "(" + range + ")";
	}
	
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		boolean do_replace = false;
		VHDLnode rbase = base.replace(replacetable);
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
		VHDLnode rindex=null;
		if (index!=null) {
			rindex = index.replace(replacetable);
			if (rindex!=null) {
				do_replace=true;
			} else {
				rindex = index;
			}
		}
		if (do_replace) return new VHDLsubrange(rbase, rrange, rindex);
		return null;
	}

}
