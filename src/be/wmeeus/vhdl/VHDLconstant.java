package be.wmeeus.vhdl;

/**
 * Class VHDLconstant represents a constant in VHDL.
 * @author Wim Meeus
 *
 */
public class VHDLconstant extends VHDLsymbol {
	// TODO store the value as a VHDL value
	String value = null;
	
	public VHDLconstant(String n, VHDLtype t, String v) {
		name = n;
		type = t;
		value = v;
	}
	
	public VHDLconstant(String n) {
		// in case we don't need the definition...
		name = n;
	}
	
	public String toStringDef() {
		return "constant " + name + ": " + type + " := " + value;
	}
}
