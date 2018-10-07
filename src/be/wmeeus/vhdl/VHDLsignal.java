package be.wmeeus.vhdl;

import be.wmeeus.util.PP;

public class VHDLsignal extends VHDLsymbol {

	public VHDLsignal(String n, VHDLtype t) throws VHDLexception {
		name = n;
		type = t;
		VHDLsymbol.add(this);
	}

	public VHDLsignal(String n, VHDLtype t, VHDLarchitecture a) throws VHDLexception {
		name = n;
		type = t;
		a.add(this);
		VHDLsymbol.add(this);
	}
	
	public String toStringDef() {
		return "signal " + name + " : " + type;
	}
	
	public String toStringDef(int l) {
		return "signal " + PP.W(name, l) + " : " + type;
	}
}
