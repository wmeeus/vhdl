package be.wmeeus.vhdl;

import java.util.Hashtable;

public class VHDLstd_logic_vector extends VHDLtype {
	VHDLrange range = null;
	
	public static final VHDLstd_logic_vector STD_LOGIC_VECTOR = new VHDLstd_logic_vector();
	
	public VHDLstd_logic_vector() {
	}
	
	public VHDLstd_logic_vector(VHDLnode s, String d, VHDLnode e) {
		range = new VHDLrange(s, d, e);
	}
	
	public VHDLstd_logic_vector(int s, int e) {
		range = new VHDLrange(s, e);
	}
	
	public VHDLstd_logic_vector(int s) {
		range = new VHDLrange(s);
	}
	
	public VHDLstd_logic_vector(VHDLnode s) {
		if (s instanceof VHDLrange) {
			range = (VHDLrange)s;
		} else {
			range = new VHDLrange(s);
		}
	}
	
	public VHDLstd_logic_vector(String s) throws VHDLexception {
		// throw out brackets
		if (s.startsWith("(")) s = s.substring(1);
		if (s.endsWith(")")) s = s.substring(0,  s.length()-1);
		range = new VHDLrange(s);
	}
	
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

}
