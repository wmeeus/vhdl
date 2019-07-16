package be.wmeeus.vhdl;

import be.wmeeus.util.PP;

/**
 * Class VHDLport represents a port in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLport extends VHDLsymbol {
	/**
	 * Port direction
	 */
	String direction = null;

	/**
	 * Construct a port
	 * @param n port name
	 * @param d port direction (in/out/inout)
	 * @param t port type
	 * @throws VHDLexception
	 */
	public VHDLport(String n, String d, VHDLtype t) throws VHDLexception {
		name = n;
		direction = d;
		type = t;
		VHDLsymbol.add(this);
	}

	/**
	 * Indicates whether this port is an input
	 * @return true if this port is an input
	 */
	public boolean isIn() {
		return direction.equals("in");
	}
	
	/**
	 * Indicates whether this port is an output
	 * @return true if this port is an output
	 */
	public boolean isOut() {
		return direction.equals("out");
	}
	
	/**
	 * Indicates whether this port is an inout
	 * @return true if this port is an inout
	 */
	public boolean isInOut() {
		return direction.equals("inout");
	}
	
	/**
	 * Returns the port direction
	 * @return the port direction
	 */
	public String getDirection() {
		return direction;
	}
	
	public String toStringDef() {
		return toStringDef(0);
	}

	public String toStringDef(int l) {
		String r = null;
		if (comment!=null) {
			r = "-- " + comment + "\n    " ;
		} else {
			r = "";
		}
		r += PP.W(name, l) + " : " + direction;
		if (direction.length()<3) r += " ";
		r += " " + type;
		return r;
	}
}
