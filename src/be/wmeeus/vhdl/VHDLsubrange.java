package be.wmeeus.vhdl;

import java.util.Hashtable;

public class VHDLsubrange extends VHDLnode {
	VHDLnode base = null;
	VHDLrange range = null;
	VHDLnode index = null;
	
	public VHDLsubrange(VHDLnode n, int s, int e) {
		base = n;
		range = new VHDLrange(s, e);
	}
	
	public VHDLsubrange(VHDLnode n, int s) {
		base = n;
		index = new VHDLvalue(s);
	}
	
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
	 * @param n
	 * @param r
	 * @param s
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
