package be.wmeeus.vhdl;
import be.wmeeus.util.*;

public class VHDLarray extends VHDLtype {
	VHDLtype basetype = null;
	VHDLrange range = null;
	
	public VHDLarray(String n, VHDLtype t, VHDLrange r) {
		name = n;
		basetype = t;
		range = r;
	}
	
	public VHDLarray(String n, VHDLtype t, int rs, int re) {
		name = n;
		basetype = t;
		range = new VHDLrange(rs, re);
	}
	
	public VHDLarray(String n, VHDLtype t, VHDLnode rs, String d, VHDLnode re) {
		name = n;
		basetype = t;
		range = new VHDLrange(rs, d, re);
	}
	
	public String toStringDef() {
		String r = PP.I + "type " + name + " is array (range " + range + ") of " + basetype + ";\n";
		return r;
	}
	
	public String toString() {
		return name + "( " + range + " )";
	}
}
