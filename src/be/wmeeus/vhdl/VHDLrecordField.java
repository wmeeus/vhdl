package be.wmeeus.vhdl;

public class VHDLrecordField extends VHDLsymbol {
	public VHDLrecordField(String n, VHDLtype t) {
		name = n;
		type = t;
	}
	
	public String toStringDef() {
		return name + " : " + type + ";\n";
	}
}
