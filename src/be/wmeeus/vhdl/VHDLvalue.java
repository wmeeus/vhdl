package be.wmeeus.vhdl;
import java.util.*;

public class VHDLvalue extends VHDLnode implements /*Comparator<VHDLvalue> ,*/ Comparable<VHDLvalue> {
	Object value = null;
	VHDLtype type = null;

	public static final VHDLvalue ZERO    = new VHDLvalue(0);
	public static final VHDLvalue ONE     = new VHDLvalue(1);
	public static final VHDLvalue TRUE    = new VHDLvalue(true);
	public static final VHDLvalue FALSE   = new VHDLvalue(false);
	public static final VHDLvalue L0      = new VHDLvalue("'0'", VHDLstd_logic.STD_LOGIC);
	public static final VHDLvalue L1      = new VHDLvalue("'1'", VHDLstd_logic.STD_LOGIC);
	public static final VHDLvalue OTHERS0 = new VHDLvalue("(others => '0')", VHDLstd_logic_vector.STD_LOGIC_VECTOR);
	public static final VHDLvalue OTHERSDC = new VHDLvalue("(others => '-')", VHDLstd_logic_vector.STD_LOGIC_VECTOR);

	public VHDLvalue(Object o) {
		// create a value of said type
		if (o instanceof VHDLtype) {
			type = (VHDLtype) o;

			return;
		}
		// argument is the value
		value = o;
	}

	public VHDLvalue(Object o, VHDLtype t) {
		value = o;
		type = t;
	}

	public VHDLvalue(int i) {
		value = Integer.valueOf(i);
		type = VHDLinteger.INTEGER;
	}

	public VHDLvalue(boolean b) {
		value = Boolean.valueOf(b);
		type = VHDLboolean.BOOLEAN;
	}
	
	public static VHDLnode getValue(String s) throws VHDLexception {
		VHDLnode result = null;
		// try to determine the nature of what's in the string, return appropriate node
		// TODO put this into a proper parser, in a separate class
		if (s.trim().startsWith("\"")) {
			// TODO remove quotes from string as well
			return new VHDLvalue(s.trim().replaceAll("\"", ""), VHDLstring.STRING);
		}
		try {
			int i = Integer.parseInt(s);
			result = new VHDLvalue(i);
		} catch (NumberFormatException ex) {
			throw new VHDLexception("**ERROR** " + s + " doesn not represent a number");
		}
		
		return result;
	}

	public Object get() {
		return value;
	}

	public VHDLtype getType() {
		return type;
	}

	public String toString() {
		if (type instanceof VHDLstd_logic_vector || type instanceof VHDLstring) {
			if (value instanceof String) {
				if (((String)value).startsWith("\"") || ((String)value).startsWith("("))
					return (String)value;
			}
			return "\"" + value + "\"";
		}
		if (value!=null) return value.toString();
		return "";
	}

	/**
	 * Make a one-hot std_logic_vector of length len with a one at position pos
	 * 
	 * @param pos
	 * @param len
	 * @return
	 */
	public static VHDLvalue onehot(int pos, int len) {
		String s = "";
		for (int i=0; i<len; i++) {
			if (i==pos) 
				s = "1" + s;
			else
				s = "0" + s;
		}
		return new VHDLvalue(s, new VHDLstd_logic_vector(len));
	}
	
	@Override
	public int compareTo(VHDLvalue arg0) {
		if ((arg0.value instanceof Integer) &&  (this.value instanceof Integer)) {
			return ((Integer)this.value).compareTo((Integer)arg0.value);
		}
		if ((arg0.value instanceof String) &&  (this.value instanceof String)) {
			return ((String)this.value).compareTo((String)arg0.value);
		}
	return 0;
	}

	public VHDLnode replace(Hashtable<VHDLnode, VHDLnode> replacetable) throws VHDLexception {
		if (replacetable.containsKey(this)) return replacetable.get(this);
		VHDLtype tp = (VHDLtype)(type.replace(replacetable));
		if (tp!=null) return new VHDLvalue(value, tp);
		return null;
	}

}
