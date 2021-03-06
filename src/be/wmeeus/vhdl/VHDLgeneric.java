package be.wmeeus.vhdl;

import java.util.Hashtable;

import be.wmeeus.util.PP;

/**
 * Class VHDLgeneric contains a generic in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLgeneric extends VHDLsymbol {
	/**
	 * Default value of this generic
	 */
	VHDLnode defaultvalue = null;
	
	/**
	 * Construct a generic
	 * @param n generic name
	 * @param t generic type
	 * @throws VHDLexception
	 */
	public VHDLgeneric(String n, VHDLtype t) throws VHDLexception {
		name = n;
		type = t;
		VHDLsymbol.add(this);
	}
	
	/**
	 * Construct a generic
	 * @param n generic name
	 * @param t generic type
	 * @param d default value
	 * @throws VHDLexception
	 */
	public VHDLgeneric(String n, VHDLtype t, VHDLnode d) throws VHDLexception {
		name = n;
		type = t;
		defaultvalue = d;
		VHDLsymbol.add(this);
	}
	
	/**
	 * Construct a generic
	 * @param n generic name
	 * @param t generic type
	 * @param d default value
	 * @throws VHDLexception
	 */
	public VHDLgeneric(String n, VHDLtype t, int d) throws VHDLexception {
		name = n;
		type = t;
		defaultvalue = new VHDLvalue(d);
		VHDLsymbol.add(this);
	}

	/**
	 * Parse a generic from a String
	 * @param s the input String, format <name> <type> [:= <default_value>]
	 * @throws VHDLexception
	 */
	public VHDLgeneric(String s) throws VHDLexception {
		// parse generic from string
		s = s.trim();
		// find name
		int ws = s.indexOf(" ");
		if (ws<1) throw new VHDLexception("VHDLgeneric: cannot parse " + s);
		name = s.substring(0,ws);
		s = s.substring(ws+1).trim();
		// strip final ; if present
		if (s.endsWith(";")) s = s.substring(0, s.length()-1).trim();
		// check for default value, split if present
		ws = s.indexOf(":=");
		if (ws>=0) {
			defaultvalue = VHDLvalue.getValue(s.substring(ws+2).trim());
			s = s.substring(0, ws);
		}
		type = VHDLtype.getType(s);
		VHDLsymbol.add(this);
	}
	
	/**
	 * Construct a generic
	 * @param n generic name
	 * @param t generic type
	 * @param d default value
	 * @throws VHDLexception
	 */
	public VHDLgeneric(String n, String t, String d) throws VHDLexception {
		name = n.trim();
		type = VHDLtype.getType(t);
		if (d!=null) {
			d = d.trim();
			if (d.length()>0)
				defaultvalue = VHDLvalue.getValue(d);
		}
		VHDLsymbol.add(this);
	}

	/**
	 * Returns the default value
	 * @return the default value
	 */
	public VHDLnode getDefault() {
		return defaultvalue;
	}
	
	public String toStringDef() {
		return toStringDef(0);
	}
	
	public String toStringDef(int l) {
		String r = "" + PP.W(name, l) + " : " + type;
		if (defaultvalue != null) {
			r += " := " + defaultvalue;
		}
		return r;
	}
	
	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		VHDLtype tp = (VHDLtype)(type.replace(replacetable));
		VHDLnode df = defaultvalue.replace(replacetable);
		if (tp!=null || df!= null) {
			return new VHDLgeneric(name, tp==null?type:tp, df==null?defaultvalue:df);
		}
		return null;
	}

}
