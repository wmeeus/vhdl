package be.wmeeus.vhdl;

public class VHDLcast extends VHDLnode {
	VHDLtype type = null;
	VHDLnode node = null;
	
	public VHDLcast(VHDLtype t, VHDLnode n) {
		type = t;
		node = n;
	}
	
	public String toString() {
		return "" + type + "(" + node + ")";
	}
}
