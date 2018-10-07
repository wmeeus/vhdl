package be.wmeeus.vhdl;

import java.util.Hashtable;

public class VHDLrange extends VHDLnode {

	VHDLnode rangestart = null;
	String direction    = null;
	VHDLnode rangeend   = null;

	public VHDLrange(VHDLnode s, String d, VHDLnode e) {
		rangestart = s;
		direction = d;
		rangeend = e;
	}
	
	public VHDLrange(int s, int e) {
		rangestart = new VHDLvalue(s);
		if (s<e) {
			direction = "to";
		} else {
			direction = "downto";
		}
		rangeend = new VHDLvalue(e);
	}
	
	public VHDLrange(int r) {
		rangestart = new VHDLvalue(r-1);
		direction = "downto";
		rangeend = VHDLvalue.ZERO;
	}
	
	public VHDLrange(String s) throws VHDLexception {
		// TODO figure out what's in s ...
		String left = null, right = null;
		int dt = s.indexOf(" downto ");
		if (dt>0) {
			direction = "downto";
			left = s.substring(0, dt).trim();
			right = s.substring(dt+8).trim();
		} else {
			dt = s.indexOf(" to ");
			if (dt>0) {
				direction = "to";
				left = s.substring(0, dt).trim();
				right = s.substring(dt+4).trim();
			} else {
				dt = s.indexOf(":");
				if (dt>0) {
					direction = "downto"; // assumption
					// figure out direction later?
					left = s.substring(0, dt).trim();
					right = s.substring(dt+1).trim();
				}
			}
		}
		if (dt<0) {
			// if s starts with g_ , assume that it's a generic
			VHDLsymbol g = VHDLsymbol.lookup(s);
			if (g==null) 
				throw new VHDLexception("**ERROR** cannot determine a range from " + s);

			rangestart = new VHDLexpression(g, "-", VHDLvalue.ONE);
			direction = "downto";
			rangeend = VHDLvalue.ZERO;				
		} else {
			rangestart = VHDLvalue.getValue(left);
			rangeend   = VHDLvalue.getValue(right);
		}
	}
	
	public VHDLrange(VHDLnode r) {
		rangestart = new VHDLexpression(r, "-", VHDLvalue.ONE);
		direction = "downto";
		rangeend = VHDLvalue.ZERO;
	}
	
	public String toString() {
		return rangestart + " " + direction + " " + rangeend;
	}

	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		VHDLnode rs = rangestart.replace(replacetable);
		VHDLnode re = rangeend.replace(replacetable);
		if (rs!=null || re!=null) {
			return new VHDLrange((rs==null)?rangestart:rs, direction, (re==null)?rangeend:re);
		}
		return null;
	}

}
