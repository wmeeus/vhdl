package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.PP;

/**
 * Class VHDLprocess representsa process in VHDL
 * @author Wim Meeus
 *
 */
public class VHDLprocess extends VHDLnode  {
	/**
	 * Sensitivity list of this process
	 */
	ArrayList<VHDLsymbol> sensitivitylist = new ArrayList<VHDLsymbol>();
	
	/**
	 * Symbols in this process (variables)
	 */
	ArrayList<VHDLsymbol> symbols = new ArrayList<VHDLsymbol>();
	
	/**
	 * Process body
	 */
	ArrayList<VHDLnode> body = new ArrayList<VHDLnode>();
	
	/**
	 * Constructs an empty process
	 */
	public VHDLprocess() {}
	
	/**
	 * Constructs an empty process
	 * @param n process name (label)
	 */
	public VHDLprocess(String n) {
		name = n;
	}
	
	/**
	 * Constructs an empty process
	 * @param n process name (label)
	 * @param c a signal to add to the sensitivity list
	 */
	public VHDLprocess(String n, VHDLsymbol c) {
		name = n;
		sensitivitylist.add(c);
	}
	
	/**
	 * Add a variable or a statement to this process
	 * @param n the variable or statement. The selection is based on the object type.
	 */
	public void add(VHDLnode n) {
		if (n instanceof VHDLsymbol) {
			symbols.add((VHDLsymbol)n);
		} else {
			body.add(n);
		}
	}
	
	/**
	 * Add a signal to the sensitivity list
	 * @param s the signal to add
	 */
	public void addSensitivity(VHDLsymbol s) {
		sensitivitylist.add(s);
	}
	
	/**
	 * Returns a String representation of this process
	 */
	public String toString() {
		String r = "\n" + PP.I;
			if (name!=null && !name.isEmpty()) {
				r += name + ": ";
			}
			r += "process";
			if (!sensitivitylist.isEmpty()) {
				r += "(";
				boolean prev = false;
				for (VHDLsymbol s: sensitivitylist) {
					if (prev) r += ", ";
					prev = true;
					r += s;
				}
				r += ")";
			}
			r += "\n";
			// TODO symbol table
			
			// body
			r += PP.I + "begin\n";
			PP.down();
				for (VHDLnode n: body) {
					r += n;
				}
			PP.up();
			r += PP.I + "end process;\n\n";
		return r;
	}
	
}
