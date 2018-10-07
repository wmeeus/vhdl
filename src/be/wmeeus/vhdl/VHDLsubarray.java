package be.wmeeus.vhdl;

import java.util.Hashtable;

public class VHDLsubarray extends VHDLtype {
	VHDLtype base = null;
	VHDLrange range = null;
	
	public VHDLsubarray(VHDLtype n, int s, int e) {
		base = n;
		range = new VHDLrange(s, e);
	}
	
	public VHDLsubarray(VHDLtype n, int s) {
		base = n;
		range = new VHDLrange(s);
	}
	
	private VHDLsubarray(VHDLtype t, VHDLrange n) {
		base = t;
		range = n;
	}
	
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
