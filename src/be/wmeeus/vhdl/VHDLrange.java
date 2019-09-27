package be.wmeeus.vhdl;

import java.util.Hashtable;

/**
 * Class VHDLrange represents a range in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLrange extends VHDLnode {
	/**
	 * Start of the range
	 */
	VHDLnode rangestart = null;
	
	/**
	 * Direction of the range (to/downto)
	 */
	String direction    = null;
	
	/**
	 * End of the range
	 */
	VHDLnode rangeend   = null;

	/**
	 * Construct a range
	 * @param s start of the range
	 * @param d direction (to/downto)
	 * @param e end of the range
	 */
	public VHDLrange(VHDLnode s, String d, VHDLnode e) {
		rangestart = s;
		direction = d;
		rangeend = e;
	}
	
	/**
	 * Construct a range
	 * @param s start of the range
	 * @param e endof the range
	 */
	public VHDLrange(int s, int e) {
		rangestart = new VHDLvalue(s);
		if (s<e) {
			direction = "to";
		} else {
			direction = "downto";
		}
		rangeend = new VHDLvalue(e);
	}
	
	/**
	 * Construct a range (n-1 downto 0)
	 * @param r the size of the range
	 */
	public VHDLrange(int r) {
		rangestart = new VHDLvalue(r-1);
		direction = "downto";
		rangeend = VHDLvalue.ZERO;
	}
	
	/**
	 * Construct a range from text input
	 * @param s the text defining a range. Supported input: a to b / a downto b / a:b / <a_name>. 
	 *   In case a name is given, the name is looked up in the symbol table of the design; if found,
	 *   a range (<a_name> -  1) downto 0 is returned.
	 * @throws VHDLexception
	 */
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
	
	/**
	 * Construct a range (n-1 downto 0)
	 * @param r the size of the range
	 */
	public VHDLrange(VHDLnode r) {
		rangestart = new VHDLexpression(r, "-", VHDLvalue.ONE);
		direction = "downto";
		rangeend = VHDLvalue.ZERO;
	}

	/**
	 * Returns a String representation of this range
	 */
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

	private static VHDLnode _size = null; 
	
	public VHDLnode size() {
		boolean downto = (direction.startsWith("d"));
		
		if (_size == null) {
			if (downto) {
				_size = new VHDLexpression(rangestart, "-", rangeend);
			} else {
				_size = new VHDLexpression(rangeend, "-", rangestart);
			}
			_size = new VHDLexpression(_size, "+", VHDLvalue.ONE);
		}
		return _size;
	}

}
