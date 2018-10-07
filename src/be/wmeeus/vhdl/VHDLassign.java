package be.wmeeus.vhdl;
import be.wmeeus.util.PP;

public class VHDLassign extends VHDLnode {
	VHDLnode lhs;
	VHDLnode rhs;
	
	public VHDLassign(VHDLnode l, VHDLnode r) {
		lhs = l;
		rhs = r;
	}
	
	public String toString() {
		String r = PP.I + lhs;
		if (comment!=null) r = "\n" + PP.C + comment + "\n" + r;
		if (lhs instanceof VHDLvariable) {
			r += " := ";
		} else {
			r += " <= ";
		}
		r += rhs + ";\n";
		return r;
	}
}
