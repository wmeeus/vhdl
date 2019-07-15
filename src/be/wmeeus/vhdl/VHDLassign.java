package be.wmeeus.vhdl;
import be.wmeeus.util.PP;

/**
 * Class VHDLassign describes an assignment in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLassign extends VHDLnode {
	/**
	 * Left hand side of the assignment
	 */
	VHDLnode lhs;
	
	/**
	 * Right hand side of the assignment
	 */
	VHDLnode rhs;
	
	/**
	 * Constructs a VHDL assignment
	 * @param l the LHS
	 * @param r the RHS
	 */
	public VHDLassign(VHDLnode l, VHDLnode r) {
		lhs = l;
		rhs = r;
	}
	
	/**
	 * Returns a String representation of this assignment
	 */
	public String toString() {
		String r = PP.I + lhs;
		if (comment!=null) r = "\n" + VHDLcomment.pp(comment) + "\n" + r;
		if (lhs instanceof VHDLvariable) {
			r += " := ";
		} else {
			r += " <= ";
		}
		r += rhs + ";\n";
		return r;
	}
}
