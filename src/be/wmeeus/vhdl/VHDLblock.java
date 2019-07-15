package be.wmeeus.vhdl;
import java.util.*;

/**
 * Class VHDLblock contains a block or group of VHDL statements. This can be used to structure
 * code and for the body of loops and conditionals.
 * @author Wim Meeus
 *
 */
public class VHDLblock extends VHDLnode {
	/**
	 * The list of statements in this block of code
	 */
	ArrayList<VHDLnode> body = new ArrayList<VHDLnode>();
	
	/**
	 * Construct an empty code block
	 */
	public VHDLblock() {}
	
	/**
	 * Add a statement to this block
	 * @param n the statement
	 */
	public void add(VHDLnode n) {
		body.add(n);
	}
	
	/**
	 * Returns a String representation of this code block
	 */
	public String toString() {
		String r = "";
		for (VHDLnode n: body) {
			r += n;
		}
		return r;
	}
}
