package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.PP;

/**
 * Class VHDLcase represents a case statement in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLcase extends VHDLnode {
	/**
	 * The case variable
	 */
	VHDLnode casevar = null;
	
	/**
	 * The table of cases. The table key is the case value, the table value is the VHDL code for the case 
	 */
	Hashtable<VHDLvalue, VHDLnode> cases = new Hashtable<VHDLvalue, VHDLnode>();
	
	/**
	 * The VHDL code for the default (when others) case
	 */
	VHDLnode defaultcase = null;
	
	/**
	 * Construct a VHDL case statement
	 * @param cv the case variable
	 */
	public VHDLcase(VHDLnode cv) {
		casevar = cv;
	}
	
	/**
	 * Add a case
	 * @param v the value of the case variable
	 * @param n the corresponding VHDL code
	 */
	public void add(VHDLvalue v, VHDLnode n) {
		cases.put(v,  n);
	}
	
	/**
	 * Add a case
	 * @param v the value of the case variable
	 * @param n the corresponding VHDL code
	 */
	public void add(int v, VHDLnode n) {
		cases.put(new VHDLvalue(v),  n);
	}

	/**
	 * Sets the VHDL code for the default case
	 * @param n the VHDL code for the default case
	 */
	public void setDefault(VHDLnode n) {
		defaultcase = n;
	}
	
	/**
	 * Returns a String representation of this case statement
	 */
	public String toString() {
		String r = PP.I + "case " + casevar + " is\n";
		ArrayList<VHDLvalue> keys = new ArrayList<VHDLvalue>(cases.keySet());
		Collections.sort(keys);
		for (VHDLvalue v: keys) {
			VHDLnode c = cases.get(v);
			r += PP.I + "when " + v + " => \n";
			PP.down();
			r += c;
			PP.up();
		}
		if (defaultcase != null) {
			r += PP.I + "when others => \n";
			PP.down();
			r += defaultcase;
			PP.up();
		}
		return r + PP.I + "end case;\n";
	}
}
