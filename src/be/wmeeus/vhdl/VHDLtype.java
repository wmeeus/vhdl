package be.wmeeus.vhdl;

import java.util.*;

public class VHDLtype extends VHDLnode {

	public VHDLtype() {}
	
	static Hashtable<String, VHDLtype> types = new Hashtable<String, VHDLtype>();
	
	public VHDLtype(String n) {
		name = n;
		types.put(n, this);
	}
	
	public String toString() {
		return name;
	}
	
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
