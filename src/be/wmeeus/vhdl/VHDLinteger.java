package be.wmeeus.vhdl;

public class VHDLinteger extends VHDLtype {

	VHDLrange range = null;
	boolean isNatural = false;
	
	public static final VHDLinteger INTEGER = new VHDLinteger();
	public static final VHDLinteger NATURAL = new VHDLinteger(true);
	
	public VHDLinteger() {}
	
	public VHDLinteger(boolean natural) {
		isNatural = natural;
	}
	
	public VHDLinteger(int min, int max) {
		range = new VHDLrange(min, max);		
	}
	
	public VHDLinteger(boolean natural, int min, int max) {
		isNatural = natural;
		range = new VHDLrange(min, max);		
	}
	
	public String toString() {
		String r = isNatural?"natural":"integer";
		if (range!=null) r += " range " + range;
		return r;
	}
}
