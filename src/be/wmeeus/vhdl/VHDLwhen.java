package be.wmeeus.vhdl;

public class VHDLwhen extends VHDLnode {
	VHDLnode value;
	VHDLnode condition;
	VHDLnode defaultval;
	
	public VHDLwhen(VHDLnode v, VHDLnode c, VHDLnode d) {
		value = v;
		condition = c;
		defaultval = d;
	}
	
	public String toString() {
		return value + " when " + condition + " else " + defaultval;
	}
}
