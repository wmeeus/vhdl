package be.wmeeus.vhdl;

import be.wmeeus.util.PP;

public class VHDLport extends VHDLsymbol {
	String direction = null;

	public VHDLport(String n, String d, VHDLtype t) throws VHDLexception {
		name = n;
		direction = d;
		type = t;
		VHDLsymbol.add(this);
	}

	public String toStringDef() {
		return toStringDef(0);
	}

	public boolean isIn() {
		return direction.equals("in");
	}
	
	public boolean isOut() {
		return direction.equals("out");
	}
	
	public boolean isInOut() {
		return direction.equals("inout");
	}
	
	public String getDirection() {
		return direction;
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
