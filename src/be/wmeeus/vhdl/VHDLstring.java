package be.wmeeus.vhdl;

/**
 * Class VHDLstring represents the String datatype in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLstring extends VHDLtype {
	/**
	 * A static String data type
	 */
	public static final VHDLstring STRING = new VHDLstring();
		
	/**
	 * Returns a String representation of the String data type.
	 */
	public String toString() {
		return "string";
	}
	
}
