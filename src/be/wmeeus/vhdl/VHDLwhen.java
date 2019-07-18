package be.wmeeus.vhdl;

/**
 * Class VHDLwhen represents a "when" construct in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLwhen extends VHDLnode {
	/**
	 * The value in case the condition is met
	 */
	VHDLnode value;
	
	/**
	 * The condition
	 */
	VHDLnode condition;
	
	/**
	 * The value in case the condition is not met
	 */
	VHDLnode defaultval;
	
	/**
	 * Construct a VHDL "when" construct
	 * @param v the value in case the condition is met
	 * @param c the condition
	 * @param d the value in case the condition is not met
	 */
	public VHDLwhen(VHDLnode v, VHDLnode c, VHDLnode d) {
		value = v;
		condition = c;
		defaultval = d;
	}
	
	/**
	 * Returns a String representation of this when construct
	 */
	public String toString() {
		return value + " when " + condition + " else " + defaultval;
	}
}
