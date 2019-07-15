package be.wmeeus.vhdl;

/**
 * Class VHDLboolean represents a boolean type in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLboolean extends VHDLtype {
	/**
	 * Static boolean type
	 */
	public static final VHDLboolean BOOLEAN = new VHDLboolean();
	
	/**
	 * Returns a String representation of the boolean type
	 */
	public String toString() {
		return "boolean";
	}

}
