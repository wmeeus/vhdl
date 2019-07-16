package be.wmeeus.vhdl;
import be.wmeeus.util.PP;

/**
 * Class VHDLif contains an if/then/else structure in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLif extends VHDLnode {
	/**
	 * The condition
	 */
	VHDLnode cond;
	
	/**
	 * The "then" body
	 */
	VHDLnode bthen = null;
	
	/**
	 * The "else" body
	 */
	VHDLnode belse = null;
	
	/**
	 * Construct an if structure
	 * @param c the condition
	 * @param t the "then" body
	 */
	public VHDLif(VHDLnode c, VHDLnode t) {
		cond = c;
		bthen = t;
	}
	
	/**
	 * Construct an if structure
	 * @param c the condition
	 * @param t the "then" body
	 * @param e the "else" body
	 */
	public VHDLif(VHDLnode c, VHDLnode t, VHDLnode e) {
		cond = c;
		bthen = t;
		belse = e;
	}
	
	/**
	 * Returns a String representation of this if structure
	 */
	public String toString() {
		String r = PP.I + "if " + cond + " ";
		if (bthen != null) {
			r += "then\n";
			PP.down();
			r += bthen;
			PP.up();
		}
		if (belse != null) {
			r += "else\n";
			PP.down();
			r += belse;
			PP.up();
		}
		return r + PP.I + "end if;\n";
	}
}
