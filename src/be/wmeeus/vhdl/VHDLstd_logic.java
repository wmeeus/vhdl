package be.wmeeus.vhdl;

/**
 * Class VHDLstd_logic represents the std_logic data type in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLstd_logic extends VHDLtype {
	/**
	 * A static std_logic type
	 */
	public static final VHDLstd_logic STD_LOGIC = new VHDLstd_logic(); 
	
	/**
	 * Returns a String representation of the std_logic data type.
	 */
	public String toString() {
		return "std_logic";
	}
}
