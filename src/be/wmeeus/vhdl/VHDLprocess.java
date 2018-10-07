package be.wmeeus.vhdl;
import java.util.*;
import be.wmeeus.util.PP;

public class VHDLprocess extends VHDLnode  {
	ArrayList<VHDLsymbol> sensitivitylist = new ArrayList<VHDLsymbol>();
	ArrayList<VHDLsymbol> symbols = new ArrayList<VHDLsymbol>();
	ArrayList<VHDLnode> body = new ArrayList<VHDLnode>();
	
	public VHDLprocess() {}
	public VHDLprocess(String n) {
		name = n;
	}
	public VHDLprocess(String n, VHDLsymbol c) {
		name = n;
		sensitivitylist.add(c);
	}
	public void add(VHDLnode n) {
		if (n instanceof VHDLsymbol) {
			symbols.add((VHDLsymbol)n);
		} else {
			body.add(n);
		}
	}
	public void addSensitivity(VHDLsymbol s) {
		sensitivitylist.add(s);
	}
	
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
