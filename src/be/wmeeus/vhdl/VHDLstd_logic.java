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

	public VHDLnode mkValue(String s) throws VHDLexception {
		if (s.equals("open")) return VHDLvalue.OPEN;
		if (s.equals("0")) return VHDLvalue.L0;
		if (s.equals("1")) return VHDLvalue.L1;
		if (s.equals("-") || s.equals("dc")) return VHDLvalue.OTHERSDC;
		throw new VHDLexception("std_logic value: " + s + " unsupported");
	}
}
