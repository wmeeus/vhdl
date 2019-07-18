package be.wmeeus.vhdl;

import be.wmeeus.util.PP;

/**
 * Class VHDLsignal represents a signal in VHDL
 * @author wmeeus
 *
 */
public class VHDLsignal extends VHDLsymbol {
	/**
	 * Constructs a signal
	 * @param n signal name
	 * @param t signal type
	 * @throws VHDLexception
	 */
	public VHDLsignal(String n, VHDLtype t) throws VHDLexception {
		name = n;
		type = t;
		VHDLsymbol.add(this);
	}

	/**
	 * Constructs a signal and adds it to the symbol table of an architecture
	 * @param n signal name
	 * @param t signal type
	 * @param a the architecture
	 * @throws VHDLexception
	 */
	public VHDLsignal(String n, VHDLtype t, VHDLarchitecture a) throws VHDLexception {
		name = n;
		type = t;
		a.add(this);
		VHDLsymbol.add(this);
	}
	
	/**
	 * Returns a String with the definition of this signal
	 */
	public String toStringDef() {
		return "signal " + name + " : " + type;
	}
	
	/**
	 * Returns a String with the definition of this signal
	 * @param l the column width for the signal name (pretty-printer)
	 */
	public String toStringDef(int l) {
		return "signal " + PP.W(name, l) + " : " + type;
	}
}
